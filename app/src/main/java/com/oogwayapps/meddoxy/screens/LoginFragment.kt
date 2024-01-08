package com.oogwayapps.meddoxy.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.databinding.FragmentLoginBinding
import com.oogwayapps.meddoxy.utils.NetworkResponse
import com.oogwayapps.meddoxy.viewmodel.MedViewmodel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private val viewmodel by viewModels<MedViewmodel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submit.setOnClickListener {
            saveEmail(binding.editTextEmail.text.toString())
            viewmodel.existing(binding.editTextEmail.text.toString())
            //findNavController().navigate(R.id.action_loginFragment2_to_userTypeFragment)
        }
        binding.progressBar.setAnimation(R.raw.pbar)
        binding.progressBar.repeatCount = LottieDrawable.INFINITE
        binding.progressBar.playAnimation()
        viewmodel._isLoading.observe(viewLifecycleOwner){
            when(it){
                true -> binding.progressBar.visibility = View.VISIBLE
                false -> binding.progressBar.visibility = View.GONE
            }
        }
        viewmodel._existingLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResponse.Success->{
                    findNavController().navigate(R.id.action_loginFragment2_to_otpFragment)
                }
                is NetworkResponse.Error ->{
                    findNavController().navigate(R.id.action_loginFragment2_to_userTypeFragment)
                }
            }
        }
        binding.loginCard.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.upwards)

    }

    private fun saveEmail(email: String) {
        val sharedPreference =  requireActivity().getSharedPreferences("MEDDOXY", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("email",email)
        editor.commit()
    }
}