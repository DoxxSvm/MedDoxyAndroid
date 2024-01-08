package com.oogwayapps.meddoxy.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieDrawable
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.adapters.DoctorPastAppointmentAdapter
import com.oogwayapps.meddoxy.adapters.DoctorUpcomingAppointmentAdapter
import com.oogwayapps.meddoxy.adapters.PatientPastAppointmentAdapter
import com.oogwayapps.meddoxy.databinding.ActivityDoctorPastAppointmentBinding
import com.oogwayapps.meddoxy.databinding.ActivityDoctorUpcomingAppointmentBinding
import com.oogwayapps.meddoxy.databinding.ActivityPatientPastAppointmentBinding
import com.oogwayapps.meddoxy.models.AppointmentDetails
import com.oogwayapps.meddoxy.models.AppointmentList
import com.oogwayapps.meddoxy.utils.Constants
import com.oogwayapps.meddoxy.utils.NetworkResponse
import com.oogwayapps.meddoxy.viewmodel.MedViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorUpcomingAppointmentActivity : AppCompatActivity() {
    lateinit var binding: ActivityDoctorUpcomingAppointmentBinding
    private val viewmodel by viewModels<MedViewmodel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorUpcomingAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.setAnimation(R.raw.pbar)
        binding.progressBar.repeatCount = LottieDrawable.INFINITE
        binding.progressBar.playAnimation()

        val map = HashMap<String,String>().apply {
            //put("doctorID",Constants.getID(this@PatientUpcomingAppointmentActivity))
            put("doctorID",Constants.getID(this@DoctorUpcomingAppointmentActivity))
        }

        viewmodel.doctorUpcomingAppointment(map)

        viewmodel.docUpAppointmentLiveData.observe(this){
            when(it){
                is NetworkResponse.Success ->{
                    setUpRv(it.response)
                }
                is NetworkResponse.Error ->{
                    Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewmodel.markAsCompAppointmentLiveData.observe(this){
            when(it){
                is NetworkResponse.Success ->{
                    Toast.makeText(this,"Updated", Toast.LENGTH_SHORT).show()
                    viewmodel.doctorUpcomingAppointment(map)
                }
                is NetworkResponse.Error ->{
                    Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewmodel._isLoading.observe(this){
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

    private fun setUpRv(response: AppointmentList?) {
        val adap = DoctorUpcomingAppointmentAdapter(::completed)
        Log.v("Doxx",response.toString())
        binding.patientUpcomingAppointmentActivity.apply {
            adapter = adap
            layoutManager = LinearLayoutManager(this@DoctorUpcomingAppointmentActivity)
        }

        if (response != null) {
            if(response.size == 0){
                hideViews()
            }
            else showViews()
        }

        adap.differ.submitList(response as MutableList<AppointmentDetails>)
    }
    private fun hideViews() {
        binding.notfound.animation= AnimationUtils.loadAnimation(this,R.anim.pop)
        binding.notfoundTv.animation= AnimationUtils.loadAnimation(this,R.anim.pop)
        binding.notfound.isVisible =true
        binding.notfoundTv.isVisible = true
    }
    private fun showViews() {
        binding.notfound.isVisible =false
        binding.notfoundTv.isVisible = false
    }

    private fun completed(appointmentDetails: AppointmentDetails){
        val map = HashMap<String,String>().apply {
            //put("appointmentID",a)
            put("appointmentID",appointmentDetails.appointmentID)
        }
        viewmodel.markAsCompleted(map)
    }

}