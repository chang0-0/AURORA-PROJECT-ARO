package com.nassafy.aro.ui.view.stamp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.nassafy.aro.R

class StampCountryPlaceCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs) {
    private lateinit var countryPlaceAdapter : CountryPlaceViewPagerAdapter

    private var countryPlaceNameTextView: TextView? = null
    private var countryNameTextView: TextView? = null
    private var countryPlaceInformTextView: TextView? = null

    init {
        val inflater: LayoutInflater =
            getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.stamp_country_customview_layout, this, false)
        addView(view)

        // 값이 들어갈 부분
        countryPlaceNameTextView = view.findViewById(R.id.stamp_country_place_name_textview)
        countryNameTextView = view.findViewById(R.id.stamp_country_name_textview)
        countryPlaceInformTextView = view.findViewById(R.id.stamp_country_place_inform_textview)


    }

    fun setView(typeNum : Int) {

    } // End of setView



} // End of StampCountryPlaceCustomView class
