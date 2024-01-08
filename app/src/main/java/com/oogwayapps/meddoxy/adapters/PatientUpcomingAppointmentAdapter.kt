package com.oogwayapps.meddoxy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.databinding.ItemPatientUpcomingAppointmentBinding
import com.oogwayapps.meddoxy.models.AppointmentDetails

class PatientUpcomingAppointmentAdapter:RecyclerView.Adapter<PatientUpcomingAppointmentAdapter.DoctorUpcomingAppointmentAdapterViewHolder>()
{
    inner class DoctorUpcomingAppointmentAdapterViewHolder(val binding: ItemPatientUpcomingAppointmentBinding):RecyclerView.ViewHolder(binding.root)


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
        val binding = ItemPatientUpcomingAppointmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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