package com.oogwayapps.meddoxy.screens

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.snackbar.Snackbar
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.adapters.DoctorPastAppointmentAdapter
import com.oogwayapps.meddoxy.adapters.PatientPastAppointmentAdapter
import com.oogwayapps.meddoxy.databinding.ActivityDoctorPastAppointmentBinding
import com.oogwayapps.meddoxy.databinding.ActivityPatientPastAppointmentBinding
import com.oogwayapps.meddoxy.models.AppointmentDetails
import com.oogwayapps.meddoxy.models.AppointmentList
import com.oogwayapps.meddoxy.utils.Constants
import com.oogwayapps.meddoxy.utils.NetworkResponse
import com.oogwayapps.meddoxy.viewmodel.MedViewmodel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class DoctorPastAppointmentActivity : AppCompatActivity() {
    lateinit var binding: ActivityDoctorPastAppointmentBinding
    private val viewmodel by viewModels<MedViewmodel>()
    lateinit var appointmentDetails: AppointmentDetails
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorPastAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val colorDrawable = ColorDrawable(Color.parseColor("#113137"))
        supportActionBar?.setBackgroundDrawable(colorDrawable)

        binding.progressBar.setAnimation(R.raw.pbar)
        binding.progressBar.repeatCount = LottieDrawable.INFINITE
        binding.progressBar.playAnimation()

        val map = HashMap<String,String>().apply {
            put("doctorID", Constants.getID(this@DoctorPastAppointmentActivity))
        }

        viewmodel.doctorPastAppointment(map)

        viewmodel.docPastAppointmentLiveData.observe(this){
            when(it){
                is NetworkResponse.Success ->{
                    setUpRv(it.response)
                }
                is NetworkResponse.Error ->{
                    Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewmodel.uploadLiveData.observe(this){
            when(it){
                is NetworkResponse.Success ->{
                    Snackbar.make(binding.root,"Prescription uploaded",Snackbar.LENGTH_SHORT).show()
                    viewmodel.doctorPastAppointment(map)

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
        val adap = DoctorPastAppointmentAdapter(::upload)
        Log.v("Doxx",response.toString())
        binding.patientUpcomingAppointmentActivity.apply {
            adapter = adap
            layoutManager = LinearLayoutManager(this@DoctorPastAppointmentActivity)
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

    private fun upload(_appointmentDetails: AppointmentDetails){
        appointmentDetails=_appointmentDetails
        getContent.launch("image/*")
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()){
        if(it==null){
            Snackbar.make(binding.root,"No image selected",Snackbar.LENGTH_SHORT).show()
        }
        else{
            uploadIcon(appointmentDetails.appointmentID,it)
        }
    }

    private fun uploadIcon(appointmentID: String,iconUri: Uri) {
        val file = File(applicationContext.filesDir, "image.png")
        val inputStream =contentResolver.openInputStream(iconUri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val image = MultipartBody.Part.createFormData("file",file.name,requestBody)
        val id = MultipartBody.Part.createFormData("appointmentID",appointmentID)

        viewmodel.upload(image, id)

    }

}