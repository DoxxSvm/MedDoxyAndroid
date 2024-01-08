package com.oogwayapps.meddoxy.models

import com.google.gson.annotations.SerializedName

data class ExistingRequestBody(
    @SerializedName("email")
    val email: String,

)