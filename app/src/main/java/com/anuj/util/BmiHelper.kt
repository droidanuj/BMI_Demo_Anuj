package com.anuj.util

import com.anuj.util.Const.FORMAT_DECIMAL

class BmiHelper {

    fun calculateBMI(height: Int, weight: Int): Float {
        val heightMeter = height.toFloat() / 100
        val bmi = weight.toFloat() / (heightMeter * heightMeter)
        return String.format(FORMAT_DECIMAL, bmi).toFloat()
    }

    companion object {
        private var INSTANCE: BmiHelper? = null
        val instance: BmiHelper?
            get() {
                if (INSTANCE == null) {
                    INSTANCE = BmiHelper()
                }
                return INSTANCE
            }
    }
}
