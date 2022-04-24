package com.anuj.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anuj.util.BmiHelper
import com.anuj.util.Const

class MainViewModel : ViewModel() {

    val bmiResults = MutableLiveData<Float>()
    val status = MutableLiveData<String>()

    fun calculateResults(name: String, height: Int, weight: Int): Boolean {
        if (name.isEmpty()) return false

        val bmi = BmiHelper.instance?.calculateBMI(height, weight)
        bmiResults.value = BmiHelper.instance?.calculateBMI(height, weight)
        if (bmi != null) {
            var message = ""
            if (bmi < 18.5) {
                message = Const.GREETINGS + name.uppercase() + Const.MSG_UNDER_WEIGHT
            } else if (bmi >= 18.5 && bmi < 24.9) {
                message = Const.GREETINGS + name.uppercase() + Const.MSG_HEALTHY
            } else if (bmi >= 24.9 && bmi < 30) {
                message = Const.GREETINGS + name.uppercase() + Const.MSG_OVER_WEIGHT
            } else if (bmi >= 30) {
                message = Const.GREETINGS + name.uppercase() + Const.MSG_OBESITY
            }
            status.postValue(message)
        }
        return true
    }
}