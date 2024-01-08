package com.oogwayapps.meddoxy.screens

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import com.oogwayapps.meddoxy.adapters.DoctorPendingAppointmentAdapter
import com.oogwayapps.meddoxy.adapters.DoctorUpcomingAppointmentAdapter
import com.oogwayapps.meddoxy.adapters.PatientPastAppointmentAdapter
import com.oogwayapps.meddoxy.databinding.ActivityDoctorPastAppointmentBinding
import com.oogwayapps.meddoxy.databinding.ActivityDoctorPendingAppointmentBinding
import com.oogwayapps.meddoxy.databinding.ActivityDoctorUpcomingAppointmentBinding
import com.oogwayapps.meddoxy.databinding.ActivityPatientPastAppointmentBinding
import com.oogwayapps.meddoxy.models.AppointmentDetails
import com.oogwayapps.meddoxy.models.AppointmentList
import com.oogwayapps.meddoxy.utils.Constants
import com.oogwayapps.meddoxy.utils.NetworkResponse
import com.oogwayapps.meddoxy.viewmodel.MedViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorPendingAppointmentActivity : AppCompatActivity() {
    lateinit var binding: ActivityDoctorPendingAppointmentBinding
    private val viewmodel by viewModels<MedViewmodel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorPendingAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val colorDrawable = ColorDrawable(Color.parseColor("#113137"))
        supportActionBar?.setBackgroundDrawable(colorDrawable)

        binding.progressBar.setAnimation(R.raw.pbar)
        binding.progressBar.repeatCount = LottieDrawable.INFINITE
        binding.progressBar.playAnimation()

        val map = HashMap<String,String>().apply {
            put("doctorID",Constants.getID(this@DoctorPendingAppointmentActivity))

        }

        viewmodel.doctorPendingAppointment(map)

        viewmodel.docPendingAppointmentLiveData.observe(this){
            when(it){
                is NetworkResponse.Success ->{
                    setUpRv(it.response)
                }
                is NetworkResponse.Error ->{
                    Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewmodel.confirmAppointmentLiveData.observe(this){
            when(it){
                is NetworkResponse.Success ->{
                    Toast.makeText(this,"Appointment Confirmed", Toast.LENGTH_SHORT).show()
                    viewmodel.doctorPendingAppointment(map)
                }
                is NetworkResponse.Error ->{
                    Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewmodel.rejectAppointmentLiveData.observe(this){
            when(it){
                is NetworkResponse.Success ->{

                    Toast.makeText(this,"Appointment Rejected", Toast.LENGTH_SHORT).show()
                    viewmodel.doctorPendingAppointment(map)
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
        val adap = DoctorPendingAppointmentAdapter(::reject,::confirm)
        Log.v("Doxx",response.toString())
        binding.patientUpcomingAppointmentActivity.apply {
            adapter = adap
            layoutManager = LinearLayoutManager(this@DoctorPendingAppointmentActivity)
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

    private fun confirm(appointmentDetails: AppointmentDetails){
        val map = HashMap<String,String>().apply {
            put("appointmentID",appointmentDetails.appointmentID)
        }
        viewmodel.confirmAppointment(map)
    }

    private fun reject(appointmentDetails: AppointmentDetails){
        val map = HashMap<String,String>().apply {
            put("appointmentID",appointmentDetails.appointmentID)
        }
        viewmodel.rejectAppointment(map)
    }

}