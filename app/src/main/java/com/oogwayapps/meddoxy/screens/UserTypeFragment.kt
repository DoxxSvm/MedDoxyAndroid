package com.oogwayapps.meddoxy.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.databinding.FragmentUserTypeBinding


class UserTypeFragment : Fragment() {
    private lateinit var binding: FragmentUserTypeBinding
    private var flag=true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUserTypeBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dpi = resources.displayMetrics.density;
        binding.patientCard.setOnClickListener {
            flag=true
            binding.patientCard.strokeWidth = (2*dpi).toInt()
            binding.doctorCard.strokeWidth = 0
        }
        binding.doctorCard.setOnClickListener {
            flag=false
            binding.patientCard.strokeWidth = 0
            binding.doctorCard.strokeWidth = (2*dpi).toInt()
        }

        binding.next.setOnClickListener {
            if(flag)  findNavController().navigate(R.id.action_userTypeFragment_to_signUpFragment)
            else findNavController().navigate(R.id.action_userTypeFragment_to_doctorSignUpFragment)
        }


    }
}