package com.oogwayapps.meddoxy.screens

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.databinding.FragmentLoginBinding
import com.oogwayapps.meddoxy.databinding.FragmentSignUpBinding
import com.oogwayapps.meddoxy.models.State
import com.oogwayapps.meddoxy.utils.NetworkResponse
import com.oogwayapps.meddoxy.viewmodel.MedViewmodel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class PatientSignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val viewmodel by viewModels<MedViewmodel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, getStateList())
        binding.stateEt.setAdapter(arrayAdapter)
        binding.progressBar.setAnimation(R.raw.pbar)
        binding.progressBar.repeatCount = LottieDrawable.INFINITE
        binding.progressBar.playAnimation()
        binding.stateEt.setOnItemClickListener { adapterView, _, i, _ ->
            val state = adapterView.getItemAtPosition(i)
            val cityAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, getCitiesList(state as String))
            binding.cityEt.setAdapter(cityAdapter)
        }

        binding.next.setOnClickListener {
            viewmodel.patientSignUp(getEmail(),binding.userInput.text.toString(),binding.cityEt.text.toString())
        }

        viewmodel._isLoading.observe(viewLifecycleOwner){
            when(it){
                true -> binding.progressBar.visibility = View.VISIBLE
                false -> binding.progressBar.visibility = View.GONE
            }
        }

        viewmodel.patientSignUpLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResponse.Success->{
                    findNavController().navigate(R.id.action_signUpFragment_to_otpFragment)
                }
                is NetworkResponse.Error ->{
                    Snackbar.make(requireView(),"Something went wrong",Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun getEmail():String{
        val sharedPreference =  requireActivity().getSharedPreferences("MEDDOXY", Context.MODE_PRIVATE)
        return sharedPreference.getString("email","svmguptapk90@gmail.com").toString()
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