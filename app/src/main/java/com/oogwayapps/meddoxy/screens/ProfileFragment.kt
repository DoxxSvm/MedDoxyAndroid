package com.oogwayapps.meddoxy.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.oogwayapps.meddoxy.MainActivity
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.databinding.ActivityDocListBinding
import com.oogwayapps.meddoxy.databinding.FragmentProfileBinding
import com.oogwayapps.meddoxy.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference =  requireActivity().getSharedPreferences("MEDDOXY", Context.MODE_PRIVATE)
         binding.email.text = sharedPreference.getString("email","svmguptapk90@gmail.com").toString()
         binding.name.text = sharedPreference.getString("ID","svmguptapk90@gmail.com").toString()

        binding.upcoming.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment2_to_patientUpcomingAppointmentActivity2)
            //startActivity(Intent(this,PatientUpcomingAppointmentActivity::class.java))
        }
        binding.past.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment2_to_patientPastAppointmentActivity)
        }
        binding.logout.setOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().getSharedPreferences("MEDDOXY", 0).edit().clear().commit();
            requireActivity().finishAffinity()

        }
    }

}