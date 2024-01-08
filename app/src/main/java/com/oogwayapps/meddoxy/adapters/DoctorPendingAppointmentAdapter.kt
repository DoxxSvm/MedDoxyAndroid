package com.oogwayapps.meddoxy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.databinding.ActivityAppointmentBinding.inflate
import com.oogwayapps.meddoxy.databinding.ItemDoctorBinding
import com.oogwayapps.meddoxy.databinding.ItemDoctorPastAppointmentBinding
import com.oogwayapps.meddoxy.databinding.ItemDoctorPendingAppointmentBinding
import com.oogwayapps.meddoxy.databinding.ItemDoctorUpcomingAppointmentBinding
import com.oogwayapps.meddoxy.models.AppointmentDetails
import com.oogwayapps.meddoxy.models.DoctorDetails

class DoctorPendingAppointmentAdapter(private val onReject:(AppointmentDetails)->Unit,
                                      private val onAccept:(AppointmentDetails)->Unit):RecyclerView.Adapter<DoctorPendingAppointmentAdapter.DoctorUpcomingAppointmentAdapterViewHolder>()
{
    inner class DoctorUpcomingAppointmentAdapterViewHolder(val binding: ItemDoctorPendingAppointmentBinding):RecyclerView.ViewHolder(binding.root)


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
        val binding = ItemDoctorPendingAppointmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorUpcomingAppointmentAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorUpcomingAppointmentAdapterViewHolder, position: Int) {
        val appointment = differ.currentList[position]
        holder.binding.apply {
            name.text = appointment.patientName
            slot.text = appointment.slot
            date.text = appointment.date
        }
        holder.binding.reject.setOnClickListener {
            onReject(appointment)
        }
        holder.binding.accept.setOnClickListener {
            onAccept(appointment)
        }

    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
}