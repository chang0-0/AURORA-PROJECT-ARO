package com.nassafy.aro.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getDateList(): (ArrayList<String>) {
    var dateList = arrayListOf<String>()
    val now = LocalDateTime.now()
    val startTime = LocalDateTime.of(now.year, now.month, now.dayOfMonth, now.hour, 0)
    val endTime = startTime.plusHours(71L)

    var i = 0L
    // 첫 날부터 1일 씩 늘려가며 dateList에 추가
    while (!startTime.plusDays(i).isEqual(endTime.plusHours(1L))) {
        val tempLocalDateTime = startTime.plusDays(i)
        val date = LocalDate.of(
            tempLocalDateTime.year,
            tempLocalDateTime.month,
            tempLocalDateTime.dayOfMonth
        ).format(DateTimeFormatter.ofPattern("yy/MM/dd"))
        dateList.add(date)
        i += 1L
    }
    // 마지막 날 추가
    dateList.add(
        LocalDate.of(endTime.year, endTime.month, endTime.dayOfMonth).format(
            DateTimeFormatter.ofPattern("yy/MM/dd")
        )
    )
    return dateList
} // End of getDateList

fun getHourList(dateList: ArrayList<String>): (ArrayList<ArrayList<String>>) {
    var hourList = arrayListOf<ArrayList<String>>()
    val now = LocalDateTime.now()
    val fullHours = arrayListOf<String>(
        "00:00", "01:00", "02:00", "03:00", "04:00", "05:00",
        "06:00", "07:00", "08:00", "09:00", "10:00", "11:00",
        "12:00", "13:00", "14:00", "15:00", "16:00", "17:00",
        "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"
    )
    val startTime = LocalDateTime.of(now.year, now.month, now.dayOfMonth, now.hour, 0)

    // dateList의 각 요소마다 선택 가능한 시간을 추가
    for (idx in 0 until dateList.size) {
        var tempTimeList = arrayListOf<String>()
        when (idx) {
            0 -> for (h in startTime.hour until 24) {
                when (h) {
                    in 0 until 10 -> {
                        tempTimeList.add("0$h:00")
                    }
                    else -> {
                        tempTimeList.add("$h:00")
                    }
                }
            }
            dateList.size - 1 -> for (h in 0 until startTime.hour) {
                when (h) {
                    in 0 until 10 -> {
                        tempTimeList.add("0$h:00")
                    }
                    else -> {
                        tempTimeList.add("$h:00")
                    }
                }
            }
            else -> tempTimeList = fullHours
        }
        hourList.add(tempTimeList)
    }
    return hourList
} // End of getHourList