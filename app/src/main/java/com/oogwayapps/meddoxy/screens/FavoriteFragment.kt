package com.oogwayapps.meddoxy.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieDrawable
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.adapters.DoctorAdapter
import com.oogwayapps.meddoxy.adapters.DoctorPastAppointmentAdapter
import com.oogwayapps.meddoxy.databinding.FragmentFavoriteBinding
import com.oogwayapps.meddoxy.databinding.FragmentHomeBinding
import com.oogwayapps.meddoxy.models.AppointmentDetails
import com.oogwayapps.meddoxy.models.AppointmentList
import com.oogwayapps.meddoxy.models.DoctorDetails
import com.oogwayapps.meddoxy.models.DoctorList
import com.oogwayapps.meddoxy.utils.Constants
import com.oogwayapps.meddoxy.utils.NetworkResponse
import com.oogwayapps.meddoxy.viewmodel.MedViewmodel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    lateinit var binding: FragmentFavoriteBinding
    private val viewmodel by viewModels<MedViewmodel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.setAnimation(R.raw.pbar)
        binding.progressBar.repeatCount = LottieDrawable.INFINITE
        binding.progressBar.playAnimation()

        val map = HashMap<String,String>().apply {
            //put("doctorID",Constants.getID(this@PatientUpcomingAppointmentActivity))
            put("patientID",Constants.getID(requireActivity()))
        }
        viewmodel.favDocList(map)

        viewmodel.favDocListLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResponse.Success ->{
                    setUpRv(it.response)
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
    private fun setUpRv(response: DoctorList?) {
        val adap = DoctorAdapter(::onclick)
        Log.v("Doxx",response.toString())
        binding.docList.apply {
            adapter = adap
            layoutManager = LinearLayoutManager(requireContext())
        }
        if (response != null) {
            if(response.size == 0){
                hideViews()
            }
        }

        adap.differ.submitList(response as MutableList<DoctorDetails>)
    }
    private fun onclick(doctorDetails: DoctorDetails){
        val intent = Intent(requireActivity(),AppointmentActivity::class.java)
        intent.putExtra("details",doctorDetails)
        startActivity(intent)
    }

    private fun hideViews() {
        binding.notfound.animation= AnimationUtils.loadAnimation(requireContext(),R.anim.pop)
        binding.notfoundTv.animation= AnimationUtils.loadAnimation(requireContext(),R.anim.pop)
        binding.notfound.isVisible =true
        binding.notfoundTv.isVisible = true
    }
}