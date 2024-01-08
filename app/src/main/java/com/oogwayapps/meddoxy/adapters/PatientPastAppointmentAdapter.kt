package com.oogwayapps.meddoxy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.databinding.ItemPatientPastAppointmentBinding
import com.oogwayapps.meddoxy.models.AppointmentDetails

class PatientPastAppointmentAdapter(private val viewPrescription:(AppointmentDetails)->Unit,
                                    private val orderMedicine:(AppointmentDetails)->Unit):RecyclerView.Adapter<PatientPastAppointmentAdapter.DoctorUpcomingAppointmentAdapterViewHolder>()
{
    inner class DoctorUpcomingAppointmentAdapterViewHolder(val binding: ItemPatientPastAppointmentBinding):RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<AppointmentDetails>() {
        override fun areItemsTheSame(oldItem : AppointmentDetails, newItem : AppointmentDetails) : Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem : AppointmentDetails, newItem : AppointmentDetails) : Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorUpcomingAppointmentAdapterViewHolder {
        val binding = ItemPatientPastAppointmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorUpcomingAppointmentAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorUpcomingAppointmentAdapterViewHolder, position: Int) {
        val appointment = differ.currentList[position]
        holder.binding.apply {
            name.text = appointment.doctorName
            slot.text = appointment.slot
            date.text = appointment.date
            img.setImageResource(getDraw())

        }
        holder.binding.order.setOnClickListener {
            orderMedicine(appointment)
        }
        holder.binding.viewPres.setOnClickListener {
            viewPrescription(appointment)
        }

    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
    private fun getDraw():Int{
        val arr = arrayListOf<Int>(
            R.drawable.doc1,
            R.drawable.doc2,
            R.drawable.doc3,
            R.drawable.doc4,
            R.drawable.doc5)
        return arr[(0..4).random()]
    }
}