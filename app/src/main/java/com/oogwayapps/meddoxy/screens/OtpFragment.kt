package com.oogwayapps.meddoxy.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.snackbar.Snackbar
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.databinding.FragmentLoginBinding
import com.oogwayapps.meddoxy.databinding.FragmentOtpBinding
import com.oogwayapps.meddoxy.utils.NetworkResponse
import com.oogwayapps.meddoxy.viewmodel.MedViewmodel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OtpFragment : Fragment(R.layout.fragment_otp) {
    private lateinit var binding: FragmentOtpBinding
    private val viewmodel by viewModels<MedViewmodel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOtpBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.otpTv.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.down)
        binding.card.animation=AnimationUtils.loadAnimation(requireContext(),R.anim.pop)
        binding.progressBar.setAnimation(R.raw.pbar)
        binding.progressBar.repeatCount = LottieDrawable.INFINITE
        binding.progressBar.playAnimation()

        binding.next.setOnClickListener {
            viewmodel.verifyOtp(getEmail(),binding.otpEt.text.toString())
        }

        viewmodel._isLoading.observe(viewLifecycleOwner){
            when(it){
                true -> binding.progressBar.visibility = View.VISIBLE
                false -> binding.progressBar.visibility = View.GONE
            }
        }

        viewmodel.verifyOtpLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResponse.Success->{
                    save(it.response!!.user.userID,it.response.user.userID,it.response.user.userType)
                    if(it.response.user.userType == "PATIENT"){
                        findNavController().navigate(R.id.action_otpFragment_to_dashboardActivity)
                        requireActivity().finishAffinity()
                    }
                    if(it.response.user.userType == "DOCTOR"){
                        findNavController().navigate(R.id.action_otpFragment_to_doctorProfileActivity)
                        requireActivity().finishAffinity()
                    }
                }
                is NetworkResponse.Error ->{
                    Snackbar.make(requireView(),"wrong OTP" , Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    }
    private fun save(id: String,name:String,type:String) {
        val sharedPreference =  requireActivity().getSharedPreferences("MEDDOXY", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("ID",id)
        editor.putString("name",name)
        editor.putString("type",type)

        editor.commit()
    }
    private fun getEmail():String{
        val sharedPreference =  requireActivity().getSharedPreferences("MEDDOXY", Context.MODE_PRIVATE)
        return sharedPreference.getString("email","svmguptapk90@gmail.com").toString()
    }
}