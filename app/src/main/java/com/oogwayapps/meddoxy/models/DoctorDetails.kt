package com.oogwayapps.meddoxy.models

import java.io.Serializable

data class DoctorDetails(
    val __v: Int,
    val _id: String,
    val address: String,
    val createdAt: String,
    val daysAvailable: List<String>,
    val description: String,
    val doctorID: String,
    val fees: Int,
    val name: String,
    val rating: String,
    val experience:Int,
    val consultations:Int,
    val specialization: String,
    val updatedAt: String
):Serializable

