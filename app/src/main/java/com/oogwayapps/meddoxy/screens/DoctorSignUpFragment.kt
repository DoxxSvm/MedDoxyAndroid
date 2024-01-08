package com.oogwayapps.meddoxy.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.google.gson.Gson
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.databinding.FragmentDoctorSignUpBinding
import com.oogwayapps.meddoxy.databinding.FragmentSignUpBinding
import com.oogwayapps.meddoxy.models.DoctorSignUpRequest
import com.oogwayapps.meddoxy.models.State
import com.oogwayapps.meddoxy.utils.NetworkResponse
import com.oogwayapps.meddoxy.viewmodel.MedViewmodel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class DoctorSignUpFragment : Fragment() {
    private lateinit var binding: FragmentDoctorSignUpBinding
    private val viewmodel by viewModels<MedViewmodel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDoctorSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    val specialization = arrayListOf("Neurology","Dermatology","Psychiatry","Ayurveda","Cardiology","Dental","Diet","Endocrinology","ENT","Gastro","General Surgery","Homeopathy",
        "Nephrology","Oncology","Nephrology","Ophthalmologist","Pediatrics","Physiotherapy","Rheumatology")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, getStateList())
        binding.stateEt.setAdapter(arrayAdapter)
        val specAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,specialization)
        binding.specEt.setAdapter(specAdapter)
        binding.progressBar.setAnimation(R.raw.pbar)
        binding.progressBar.repeatCount = LottieDrawable.INFINITE
        binding.progressBar.playAnimation()
        val sharedPreference =  requireActivity().getSharedPreferences("MEDDOXY", Context.MODE_PRIVATE)
        binding.stateEt.setOnItemClickListener { adapterView, _, i, _ ->
            val state = adapterView.getItemAtPosition(i)
            val cityAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, getCitiesList(state as String))
            binding.cityEt.setAdapter(cityAdapter)
        }
        binding.next.setOnClickListener {
            viewmodel.doctorSignUp(DoctorSignUpRequest(
                binding.cityEt.text.toString(), getDays(), sharedPreference.getString("email","svmguptapk90@gmail.com").toString(),
                binding.fees.text.toString().toInt(),binding.name.text.toString(),binding.specEt.text.toString().lowercase(),binding.desc.text.toString(),binding.exp.text.toString().toInt()
            ))
        }

        viewmodel.doctSignUpLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResponse.Success ->{
                    findNavController().navigate(R.id.action_doctorSignUpFragment_to_otpFragment)
                }
                is NetworkResponse.Error ->{
                    Toast.makeText(requireContext(),"Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewmodel._isLoading.observe(viewLifecycleOwner){
            when(it){
                true ->{
                    binding.progressBar.isVisible = true
                }
                false ->{
                    binding.progressBar.isVisible = false
                }
            }
        }



    }

    private fun getDays(): List<String> {
        val ans = arrayListOf<String>()
        if(binding.mon.isChecked) ans.add("MON")
        if(binding.tue.isChecked) ans.add("TUE")
        if(binding.wed.isChecked) ans.add("WED")
        if(binding.thu.isChecked) ans.add("THU")
        if(binding.fri.isChecked) ans.add("FRI")
        if(binding.sat.isChecked) ans.add("SAT")
        if(binding.sun.isChecked) ans.add("SUN")
        return ans
    }

    private fun getCitiesList(state: String):ArrayList<String>{
        val list = Gson().fromJson(getJsonDataFromAsset(requireContext(), "contries.json"), State::class.java)
        val cityArray= arrayListOf<String>()
        list.Countries.forEach {
            if((it.CountryName) == "India"){
                it.States.forEach {_state->
                    if(_state.StateName == state){
                        cityArray.addAll(_state.Cities)
                    }
                }
            }

        }
        return cityArray
    }

    private fun getStateList(): ArrayList<String> {
        val list = Gson().fromJson(getJsonDataFromAsset(requireContext(), "contries.json"), State::class.java)
        val stateArray= arrayListOf<String>()
        list.Countries.forEach {
            if((it.CountryName) == "India"){
                it.States.forEach {state->
                    if(state.Cities.isNotEmpty()) stateArray.add(state.StateName)
                }

            }

        }
        return stateArray
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}