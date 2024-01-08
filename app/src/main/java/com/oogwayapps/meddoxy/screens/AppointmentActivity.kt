package com.oogwayapps.meddoxy.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.navArgs
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.snackbar.Snackbar
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.databinding.ActivityAppointmentBinding
import com.oogwayapps.meddoxy.models.DoctorDetails
import com.oogwayapps.meddoxy.utils.Constants
import com.oogwayapps.meddoxy.utils.NetworkResponse
import com.oogwayapps.meddoxy.viewmodel.MedViewmodel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.HashMap

@AndroidEntryPoint
class AppointmentActivity : AppCompatActivity() {
    val args:DocListActivityArgs by navArgs()
    lateinit var binding: ActivityAppointmentBinding
    private val viewmodel by viewModels<MedViewmodel>()
    lateinit var details: DoctorDetails
    private var date:String=""
    private var slot:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val colorDrawable = ColorDrawable(Color.parseColor("#113137"))
        supportActionBar?.setBackgroundDrawable(colorDrawable)
        binding.progressBar.setAnimation(R.raw.pbar)
        binding.progressBar.repeatCount = LottieDrawable.INFINITE
        binding.progressBar.playAnimation()
         details = intent.getSerializableExtra("details") as DoctorDetails
        with(binding){
            name.text = details.name
            speciality.text = details.specialization
            desc.text= details.description
            fees.text=details?.fees.toString()
            consultations.text = details?.consultations.toString()
            days.text=extract(details?.daysAvailable)
            rating.text = details?.rating
            else address.text =details.address
            img.setImageResource(Constants.getDraw())

        }
        viewmodel.requestAppointmentLiveData.observe(this){
            when(it){
                is NetworkResponse.Success ->{
                    Snackbar.make(binding.root,"Appointment request created",Snackbar.LENGTH_SHORT).show()
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

        binding.dateBtn.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    binding.dateTv.text =
                        (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    date = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                },
                year, month, day)

            datePickerDialog.show()
        }

        binding.slotBtn.setOnClickListener {
            // on below line we are getting
            // the instance of our calendar.
            val c = Calendar.getInstance()

            // on below line we are getting our hour, minute.
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)


            val timePickerDialog = TimePickerDialog(
                this,
                { view, hourOfDay, minute ->

                    binding.slotTv.setText("$hourOfDay:$minute")
                    slot="$hourOfDay:$minute"
                },
                hour,
                minute,
                false
            )
            // at last we are calling show to
            // display our time picker dialog.
            timePickerDialog.show()
        }
        viewmodel.addFavDoctorLiveData.observe(this){
            when(it){
                is NetworkResponse.Success ->{
                    Snackbar.make(binding.root,"Added to favorite",Snackbar.LENGTH_SHORT).show()
                }
                is NetworkResponse.Error ->{
                    Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.book.setOnClickListener {
            val map = HashMap<String,String>().apply {
                put("doctorID",details.doctorID)
                put("patientID",Constants.getID(this@AppointmentActivity))
                put("date",date)
                put("slot",slot)

            }
            viewmodel.requestAppointment(map)
        }
        binding.favorite.setOnClickListener {
            val map = HashMap<String,String>().apply {
                put("doctorID",details.doctorID)
                put("patientID",Constants.getID(this@AppointmentActivity))
            }
            viewmodel.addFavDoctor(map)
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