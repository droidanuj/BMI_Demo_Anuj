package com.anuj.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.anuj.R
import com.anuj.util.Const
import com.anuj.util.Const.AD_UNIT_ID
import com.anuj.util.Utils
import com.anuj.viewModel.MainViewModel
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.fragment_add_bmi.*

class AddBMIDetails : Fragment() {

    private var mInterstitialAd: InterstitialAd? = null
    private lateinit var mainViewModel: MainViewModel
    private var heightValue = Const.DEFAULT_HEIGHT
    private var weightValue = Const.DEFAULT_WEIGHT

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_bmi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setUpPickers()
        loadAds()
    }

    private fun loadAds() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            requireContext(),
            AD_UNIT_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showAds() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    // shows an error
                }

                override fun onAdShowedFullScreenContent() {
                    mInterstitialAd = null
                }
            }
            mInterstitialAd?.show(requireActivity())
        }
    }

    private fun validate() {
        val name = etName.text.toString()
        val calculateResults =
            mainViewModel.calculateResults(name, heightValue, weightValue)
        if (calculateResults) showAds()
        else Utils(requireActivity()).showToast(
            requireActivity(),
            getString(R.string.msg_pls_enter_name)
        )
    }

    private fun setUpPickers() {
        weightPicker()
        heightPicker()
        genderPicker()
        tvCalculate.setOnClickListener {
            validate()
        }
    }

    private fun weightPicker() {
        val values = Const.WEIGHT_VALUES
        npWeight.minValue = 0
        npWeight.maxValue = values.size - 1
        npWeight.displayedValues = values
        npWeight.wrapSelectorWheel = true
        npWeight.setOnValueChangedListener { _, _, newVal ->
            weightValue = values[newVal].toInt()
        }
    }

    private fun heightPicker() {
        val values = Const.HEIGHT_VALUES
        npHeight.minValue = 0
        npHeight.maxValue = values.size - 1
        npHeight.displayedValues = values
        npHeight.wrapSelectorWheel = true
        npHeight.setOnValueChangedListener { _, _, newVal ->
            heightValue = values[newVal].toInt()
        }
    }

    private fun genderPicker() {
        val values = Const.GENDER
        npGender.minValue = 0
        npGender.maxValue = values.size - 1
        npGender.displayedValues = values
        npGender.wrapSelectorWheel = true
        npGender.setOnValueChangedListener { _, _, _ -> }
    }
}