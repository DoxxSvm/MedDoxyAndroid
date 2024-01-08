package com.oogwayapps.meddoxy.screens

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.oogwayapps.meddoxy.MainActivity
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.adapters.DoctorUpcomingAppointmentAdapter
import com.oogwayapps.meddoxy.databinding.ActivityDoctorProfileBinding
import com.oogwayapps.meddoxy.models.DoctorDetails
import com.oogwayapps.meddoxy.models.DoctorSignUpRequest
import com.oogwayapps.meddoxy.utils.Constants
import com.oogwayapps.meddoxy.utils.NetworkResponse
import com.oogwayapps.meddoxy.viewmodel.MedViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_doctor.*

@AndroidEntryPoint
class DoctorProfileActivity : AppCompatActivity() {
    lateinit var  binding:ActivityDoctorProfileBinding
    private val viewmodel by viewModels<MedViewmodel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val colorDrawable = ColorDrawable(Color.parseColor("#113137"))
        supportActionBar?.setBackgroundDrawable(colorDrawable)

        val map = HashMap<String,String>().apply {
            val id = Constants.getID(this@DoctorProfileActivity)
            Log.v("Doxx",id)
            put("doctorID", Constants.getID(this@DoctorProfileActivity))
        }

        binding.pending.setOnClickListener {
            startActivity(Intent(this,DoctorPendingAppointmentActivity::class.java))
        }
        binding.upcoming.setOnClickListener {
            startActivity(Intent(this,DoctorUpcomingAppointmentActivity::class.java))
        }
        binding.past.setOnClickListener {
            startActivity(Intent(this,DoctorPastAppointmentActivity::class.java))
        }
        binding.logout.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            getSharedPreferences("MEDDOXY", 0).edit().clear().commit();
            finishAffinity()
        }


        viewmodel.docDetails(map)

        viewmodel.docDetailsLiveData.observe(this){
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


    private fun setUpRv(details: DoctorDetails?) {
        with(binding){
            name.text = details!!.name
            speciality.text = details.specialization
            desc.text= details.description
            fees.text=details?.fees.toString()
            consultations.text = details?.consultations.toString()
            days.text=extract(details?.daysAvailable)
            rating.text = details?.rating
            exp.text = details.experience.toString()
            if(details.name.startsWith("Mahi") || details.name.startsWith("Amiksha") )img.setImageResource(R.drawable.doc4)


            else img.setImageResource(Constants.getDraw())
        }
    }

    private fun extract(daysAvailable: List<String>?): String {
        var str=""
        daysAvailable?.forEach {
            str+=it
            str+=", "
        }
        return str
    }

}