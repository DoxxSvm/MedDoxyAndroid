package com.oogwayapps.meddoxy.models

data class User(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val email: String,
    val otp: String,
    val updatedAt: String,
    val userID: String,
    val userType: String
)

enum class UserType{
    PATIENT,DOCTOR
}
enum class AppointmentStatus{
    PENDING,CONFIRMED,COMPLETED
}


