package com.oogwayapps.meddoxy.models

import com.google.gson.annotations.SerializedName

data class PatientSignUpRequest(
    @SerializedName(value = "email")
    var email:String="",
    @SerializedName(value = "name")
    var name:String="",
    @SerializedName(value = "address")
    var address:String="",
)