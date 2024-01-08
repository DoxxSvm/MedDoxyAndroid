package com.oogwayapps.meddoxy.utils

import android.app.Activity
import android.content.Context
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.databinding.ActivityDoctorPendingAppointmentBinding

object Constants {

    const val BASE_URL = "http://ec2-13-233-108-237.ap-south-1.compute.amazonaws.com:5001"
     fun getID(activity:Activity):String{
        val sharedPreference =  activity.getSharedPreferences("MEDDOXY", Context.MODE_PRIVATE)
        return sharedPreference.getString("ID","svmguptapk90@gmail.com").toString()
    }

    fun getDraw():Int{

        val arr = arrayListOf<Int>(
            R.drawable.doc1,
            R.drawable.doc2,
            R.drawable.doc3,
            R.drawable.doc4,
            R.drawable.doc5)
        return arr[(0..4).random()]
    }




}
