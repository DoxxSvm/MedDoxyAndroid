package com.oogwayapps.meddoxy.screens

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.databinding.FragmentHomeBinding
import com.oogwayapps.meddoxy.databinding.FragmentLoginBinding
import com.oogwayapps.meddoxy.models.State
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var binding:FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
//        val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("neurology")



//        findNavController().navigate(R.id.action)

    }

    private fun setUpListeners() {
        with(binding){
            neurology.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("neurology")
                findNavController().navigate(action)
            }
            dermatology.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("dermatology")
                findNavController().navigate(action)
            }
            psychiatry.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("psychiatry")
                findNavController().navigate(action)
            }
            ayurveda.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("ayurveda")
                findNavController().navigate(action)
            }
            cardiology.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("cardiology")
                findNavController().navigate(action)
            }
            dental.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("dental")
                findNavController().navigate(action)
            }
            diet.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("diet")
                findNavController().navigate(action)
            }
            endocrinology.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("endocrinology")
                findNavController().navigate(action)
            }
            ent.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("ent")
                findNavController().navigate(action)
            }
            gastro.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("gastro")
                findNavController().navigate(action)
            }
            generalsurgery.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("general surgery")
                findNavController().navigate(action)
            }
            homeoopathy.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("homeopathy")
                findNavController().navigate(action)
            }
            nephrology.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("nephrology")
                findNavController().navigate(action)
            }
            oncology.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("oncology")
                findNavController().navigate(action)
            }
            nephrology.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("nephrology")
                findNavController().navigate(action)
            }
            ophthalmologist.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("ophthalmologist")
                findNavController().navigate(action)
            }
            pediatrics.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("pediatrics")
                findNavController().navigate(action)
            }
            physiotherapy.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("physiotherapy")
                findNavController().navigate(action)
            }
            rheumatology.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragment2ToDocListActivity("rheumatology")
                findNavController().navigate(action)
            }
        }

    }


}