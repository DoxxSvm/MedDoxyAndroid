package com.oogwayapps.meddoxy.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.databinding.FragmentSplashBinding


class SplashFragment : Fragment(R.layout.fragment_splash) {
    private lateinit var binding:FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAnimation()

        if(getType()=="PATIENT"){
            findNavController().navigate(R.id.action_splashFragment_to_dashboardActivity)
            requireActivity().finish()
        }
        if(getType()=="DOCTOR"){
            findNavController().navigate(R.id.action_splashFragment_to_doctorProfileActivity)
            requireActivity().finish()
        }



        var count=0;
        val drawable = arrayListOf(R.raw.search_doctor,R.raw.queue, R.raw.medicine )
        setupAnim(drawable[count])
        val text = arrayListOf("Find best doctors around you", "Live status of your turn", "Order medicine from appointment prescription directly")
        binding.next.setOnClickListener {
            count++;
            if (count>2) {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment2)
                return@setOnClickListener
            }
            binding.textView.text = text[count]
            setupAnim(drawable[count])
        }
    }

    private fun getType():String{
        val sharedPreference =  requireActivity().getSharedPreferences("MEDDOXY", Context.MODE_PRIVATE)
        return sharedPreference.getString("type","no").toString()
    }


    private fun setupAnim(i: Int) {
        binding.animationView.setAnimation(i)
        binding.animationView.repeatCount = LottieDrawable.INFINITE
        binding.animationView.playAnimation()
    }

    private fun setUpAnimation() {
        binding.next.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.upwards)
        binding.animationView.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.pop)
    }


}