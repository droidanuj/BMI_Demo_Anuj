package com.anuj.util

object Const {

    const val DEFAULT_HEIGHT = 170
    const val DEFAULT_WEIGHT = 70
    const val AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"
    const val FILE_SCREENSHOT = "BMI_Anuj"
    const val FORMAT_DECIMAL = "%.2f"
    val GENDER = arrayOf(
        "Male",
        "Female",
        "Other"
    )
    val WEIGHT_VALUES = Array(201) { i -> "" + ((i + 1) * 1) }
    val HEIGHT_VALUES = Array(200) { i -> "" + ((i + 1) * 1) }

    const val GREETINGS = "HELLO, "
    const val MSG_UNDER_WEIGHT = " YOU ARE UNDER WEIGHT"
    const val MSG_HEALTHY = " YOU ARE HEALTHY"
    const val MSG_OVER_WEIGHT = " YOU ARE OVERWEIGHT"
    const val MSG_OBESITY = " YOU ARE SUFFERING FOM OBESITY"
}