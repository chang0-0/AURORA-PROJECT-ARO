package com.nassafy.aro.ui.view.main.mypage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentAroServiceSelectBinding
import com.nassafy.aro.ui.view.BaseFragment
import com.nassafy.aro.ui.view.main.mypage.viewmodel.MyPageServiceRegisterFragmentViewModel
import com.nassafy.aro.util.NetworkResult
import com.nassafy.aro.util.showSnackBarMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageServiceRegisterFragment :
    BaseFragment<FragmentAroServiceSelectBinding>(FragmentAroServiceSelectBinding::inflate) {

    private var isNextButtonClicked = false
    private val myPageServiceRegisterFragementViewModel: MyPageServiceRegisterFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: MyPageServiceRegisterFragmentArgs by navArgs()
        myPageServiceRegisterFragementViewModel.auroraService = args.auroraService
        myPageServiceRegisterFragementViewModel.meteorService = args.meteorService

        initObserve()
        initView()
    }

    override fun onResume() {
        super.onResume()
        isNextButtonClicked = false
    }

    private fun initObserve() {

        myPageServiceRegisterFragementViewModel.setSelectServiceNetworkResultLiveData.observe(this.viewLifecycleOwner) {
            when (isNextButtonClicked) {
                true -> {
                    when (it) {
                        is NetworkResult.Success -> {
                            Log.d("ssafy", "success")
                            requireView().showSnackBarMessage(getString(R.string.service_modify_success_text))
                            findNavController().navigate(R.id.action_myPageServiceRegisterFragment_to_myPageFragment)
                        }
                        is NetworkResult.Error -> {
                            Log.d("ssafy", "error")

                        }
                        is NetworkResult.Loading -> {
                            Log.d("ssafy", "loading")
                        }
                    }
                }
                false -> {

                }
            }
        }
    }

    private fun initView() {
        binding.serviceSelectLaterGroup.isGone = true
        binding.auroraServiceCardview.setIsSelected(myPageServiceRegisterFragementViewModel.auroraService)
        binding.meteorServiceCardview.setIsSelected(myPageServiceRegisterFragementViewModel.meteorService)

        //커스텀 뷰는 setOnClickListener가 안먹힘;;; //TODO 나아아중에 개선
//        binding.auroraServiceCardview.setOnClickListener {
//            Log.d("ssafy1", "why didn't be clicked?")
//        }

        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.nextButton.setOnClickListener {
            isNextButtonClicked = true
            myPageServiceRegisterFragementViewModel.auroraService =
                binding.auroraServiceCardview.getIsSelected()
            myPageServiceRegisterFragementViewModel.meteorService =
                binding.meteorServiceCardview.getIsSelected()

            val action = MyPageServiceRegisterFragmentDirections.actionMyPageServiceRegisterFragmentToMyPageFavoriteRegisterFragment(
                myPageServiceRegisterFragementViewModel.auroraService,
                myPageServiceRegisterFragementViewModel.meteorService,
            )
            when (myPageServiceRegisterFragementViewModel.auroraService) {
                true -> {
                    findNavController().navigate(action)
                }
                false -> {
                    when (myPageServiceRegisterFragementViewModel.meteorService) {
                        true -> {
//                            TODO
                            isNextButtonClicked = false
                            requireView().showSnackBarMessage("메테오 관심 위치 선택은 추후 업데이트 됩니다!")
                            findNavController().navigate(R.id.action_myPageServiceRegisterFragment_to_myPageFragment)
                        }
                        false -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                myPageServiceRegisterFragementViewModel.selectService(
                                    myPageServiceRegisterFragementViewModel.auroraService,
                                    myPageServiceRegisterFragementViewModel.meteorService
                                ) // End of selectService
                            } // End of CoroutineScope
                        } // End of when
                    } // End of when
                } // End of false
            } // End of when
        }
    }


}