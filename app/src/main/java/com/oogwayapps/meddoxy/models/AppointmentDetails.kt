package com.oogwayapps.meddoxy.models

data class AppointmentDetails(
    val __v: Int,
    val _id: String,
    val appointmentID: String,
    val appointmentStatus: String,
    val patientName:String,
    val doctorName:String,
    val createdAt: String,
    val date: String,
    val doctorID: String,
    val patientID: String,
    val slot: String,
    val updatedAt: String,
    val prescription:String
)