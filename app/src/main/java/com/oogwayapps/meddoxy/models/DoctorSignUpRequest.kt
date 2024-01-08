package com.oogwayapps.meddoxy.models

data class DoctorSignUpRequest(
    val address: String,
    val days: List<String>,
    val email: String,
    val fees: Int,
    val name: String,
    val speciality: String,
    val description: String,
    val experience:Int
)