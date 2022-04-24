package com.anuj.ui.main.fragments

import android.app.Dialog
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.anuj.R
import com.anuj.util.Const.FILE_SCREENSHOT
import com.anuj.util.Utils
import com.anuj.viewModel.MainViewModel
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.fragment_bmi_details.*

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bmi_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setUpObservers(model)
        listeners()
        showBanner()
    }

    override fun onResume() {
        super.onResume()
        Utils(requireActivity()).verifyStoragePermissions()
        ivScreenShot.visibility = View.GONE
    }

    private fun showBanner() {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun setUpObservers(model: MainViewModel) {
        model.status.observe(viewLifecycleOwner, {
            tvBmiMessage.text = it
        })
        model.bmiResults.observe(viewLifecycleOwner, {
            tvBmi.text = it.toString()
        })
    }

    private fun listeners() {
        ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        linearRate.setOnClickListener { askForRatings() }

        linearShare.setOnClickListener {
            val views: View = requireActivity().window.decorView.findViewById(android.R.id.content)
            val bitmap: Bitmap? = Utils(requireActivity()).takeScreenshot(views, FILE_SCREENSHOT)
            ivScreenShot.visibility = View.VISIBLE
            ivScreenShot.setImageBitmap(bitmap)
            rootLayout.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.screenshot_bg))
            Utils(requireActivity()).shareImage(bitmap)
        }
    }

    private fun askForRatings() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_rating)
        val tvCancel = dialog.findViewById(R.id.tvCancel) as TextView
        val tvSubmit = dialog.findViewById(R.id.tvSubmit) as TextView
        tvCancel.setOnClickListener { dialog.dismiss() }
        tvSubmit.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(requireContext(),getString(R.string.msg_rating_submitted),Toast.LENGTH_LONG).show()
        }
        dialog.show()
    }
}