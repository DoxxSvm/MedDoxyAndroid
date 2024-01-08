package com.oogwayapps.meddoxy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.databinding.ItemDoctorBinding
import com.oogwayapps.meddoxy.models.DoctorDetails

class DoctorAdapter(private val onclick:(DoctorDetails)->Unit):RecyclerView.Adapter<DoctorAdapter.DocViewHolder>()
{
    inner class DocViewHolder(val binding: ItemDoctorBinding):RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<DoctorDetails>() {
        override fun areItemsTheSame(oldItem : DoctorDetails, newItem : DoctorDetails) : Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem : DoctorDetails, newItem : DoctorDetails) : Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocViewHolder {
        val binding = ItemDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DocViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DocViewHolder, position: Int) {
        val doctor = differ.currentList[position]
        holder.binding.apply {
            name.text = "Dr. ${doctor.name}"
            exp.text = "${doctor.experience} years of experience"
            consultations.text = "${doctor.consultations} consultations"
            rating.text = doctor.rating
            if(doctor.name.startsWith("Mahi") || doctor.name.startsWith("Amiks")||doctor.name == "Laxita Sodhani"|| doctor.name == "Shivangi Prasad")  img.setImageResource(R.drawable.doc4)
            else img.setImageResource(getDraw())
        }
        holder.binding.root.setOnClickListener {
            onclick(doctor)
        }

    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    private fun getDraw():Int{
        val arr = arrayListOf<Int>(R.drawable.doc1,R.drawable.doc2,R.drawable.doc3,R.drawable.doc4,R.drawable.doc5)
        return arr[(0..4).random()]
    }
}