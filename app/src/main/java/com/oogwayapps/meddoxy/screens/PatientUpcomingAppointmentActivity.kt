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
import com.oogwayapps.meddoxy.adapters.PatientUpcomingAppointmentAdapter
import com.oogwayapps.meddoxy.databinding.ActivityPatientUpcomingAppointmentBinding
import com.oogwayapps.meddoxy.models.AppointmentDetails
import com.oogwayapps.meddoxy.models.AppointmentList
import com.oogwayapps.meddoxy.models.DoctorDetails
import com.oogwayapps.meddoxy.utils.Constants
import com.oogwayapps.meddoxy.utils.NetworkResponse
import com.oogwayapps.meddoxy.viewmodel.MedViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientUpcomingAppointmentActivity : AppCompatActivity() {
    lateinit var binding: ActivityPatientUpcomingAppointmentBinding
    private val viewmodel by viewModels<MedViewmodel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientUpcomingAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val colorDrawable = ColorDrawable(Color.parseColor("#113137"))
        supportActionBar?.setBackgroundDrawable(colorDrawable)

        binding.progressBar.setAnimation(R.raw.pbar)
        binding.progressBar.repeatCount = LottieDrawable.INFINITE
        binding.progressBar.playAnimation()

        val map = HashMap<String,String>().apply {
            put("patientID", Constants.getID(this@PatientUpcomingAppointmentActivity))
        }

        viewmodel.patientUpcomingAppointment(map)

        viewmodel.patUpAppointmentLiveData.observe(this){
            when(it){
                is NetworkResponse.Success ->{
                    setUpRv(it.response)
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
        val adap = PatientUpcomingAppointmentAdapter()
        Log.v("Doxx",response.toString())
        binding.patientUpcomingAppointmentActivity.apply {
            adapter = adap
            layoutManager = LinearLayoutManager(this@PatientUpcomingAppointmentActivity)
        }
        if (response != null) {
            if(response.size == 0){
                hideViews()
            }
            else showViews()
        }


        adap.differ.submitList(response as MutableList<AppointmentDetails>)
    }



    private fun onclick(doctorDetails: DoctorDetails){
        val intent = Intent(this,AppointmentActivity::class.java)
        intent.putExtra("details",doctorDetails)
        startActivity(intent)
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
}