package com.oogwayapps.meddoxy.repo

import android.util.Log
import com.oogwayapps.meddoxy.models.*
import com.oogwayapps.meddoxy.network.ApiService
import com.oogwayapps.meddoxy.utils.NetworkResponse
import okhttp3.MultipartBody
import javax.inject.Inject

class MedRepo @Inject constructor (private val apiService: ApiService) {
    suspend fun existing(email:String):NetworkResponse<SingleFieldResponse?>{
        try {
            val req = ExistingRequestBody(email)
            val response = apiService.existing(req)
            if(response.code() ==200){
                return NetworkResponse.Success(response.body())
            }
            return NetworkResponse.Error()
        }
        catch (e:Exception){
            Log.v("Doxx","error"+e.message.toString())
            return NetworkResponse.Error()
        }
    }

    suspend fun patientSignUp(patientSignUpRequest: PatientSignUpRequest):NetworkResponse<SingleFieldResponse?>{
        try {
            val response = apiService.patientSignUp(patientSignUpRequest)
            Log.v("Doxx",response.toString())

            if(response.code() ==200){
                return NetworkResponse.Success(response.body())
            }
            return NetworkResponse.Error()
        }
        catch (e:Exception){
            Log.v("Doxx","error"+e.message.toString())
            return NetworkResponse.Error()
        }
    }

    suspend fun upload(image: MultipartBody.Part, Id: MultipartBody.Part):NetworkResponse<SingleFieldResponse?>{
        try {
            val response = apiService.upload(image, Id)
            Log.v("Doxx",response.toString())

            if(response.code() ==200){
                return NetworkResponse.Success(response.body())
            }
            return NetworkResponse.Error()
        }
        catch (e:Exception){
            Log.v("Doxx","error"+e.message.toString())
            return NetworkResponse.Error()
        }
    }

    suspend fun doctorSignUp(patientSignUpRequest: DoctorSignUpRequest):NetworkResponse<SingleFieldResponse?>{
        try {
            val response = apiService.doctorSignUp(patientSignUpRequest)
            Log.v("Doxx",response.toString())

            if(response.code() ==200){
                return NetworkResponse.Success(response.body())
            }
            return NetworkResponse.Error()
        }
        catch (e:Exception){
            Log.v("Doxx","error"+e.message.toString())
            return NetworkResponse.Error()
        }
    }

    suspend fun verifyOtp(verificationRequest: VerificationRequest):NetworkResponse<VerificationResponse?>{
        try {
            val response = apiService.verifyOtp(verificationRequest)
            Log.v("Doxx",response.toString())
            if(response.code() ==200){
                return NetworkResponse.Success(response.body())
            }
            return NetworkResponse.Error()
        }
        catch (e:Exception){
            Log.v("Doxx","error"+e.message.toString())
            return NetworkResponse.Error()
        }
    }

    suspend fun docList(docListRequest: DocListRequest):NetworkResponse<DoctorList?>{
        try {
            val response = apiService.docList(docListRequest)
            Log.v("Doxx",response.toString())
            if(response.code() ==200){
                return NetworkResponse.Success(response.body())
            }
            return NetworkResponse.Error()
        }
        catch (e:Exception){
            Log.v("Doxx","error"+e.message.toString())
            return NetworkResponse.Error()
        }
    }

    suspend fun requestAppointment(body: HashMap<String,String>):NetworkResponse<AppointmentDetails?>{
        try {
            val response = apiService.requestAppointment(body)
            Log.v("Doxx",response.toString())
            if(response.code() ==200){
                return NetworkResponse.Success(response.body())
            }
            return NetworkResponse.Error()
        }
        catch (e:Exception){
            Log.v("Doxx","error"+e.message.toString())
            return NetworkResponse.Error()
        }
    }

    suspend fun confirmAppointment(body: HashMap<String,String>):NetworkResponse<SingleFieldResponse?>{
        try {
            val response = apiService.confirmAppointment(body)
            Log.v("Doxx",response.toString())
            if(response.code() ==200){
                return NetworkResponse.Success(response.body())
            }
            return NetworkResponse.Error()
        }
        catch (e:Exception){
            Log.v("Doxx","error"+e.message.toString())
            return NetworkResponse.Error()
        }
    }

    suspend fun markAsCompleted(body: HashMap<String,String>):NetworkResponse<SingleFieldResponse?>{
        try {
            val response = apiService.markAsCompleted(body)
            Log.v("Doxx",response.toString())
            if(response.code() ==200){
                return NetworkResponse.Success(response.body())
            }
            return NetworkResponse.Error()
        }
        catch (e:Exception){
            Log.v("Doxx","error"+e.message.toString())
            return NetworkResponse.Error()
        }
    }

    suspend fun doctorPastAppointment(body: HashMap<String,String>):NetworkResponse<AppointmentList?>{
        try {
            val response = apiService.doctorPastAppointment(body)
            Log.v("Doxx",response.toString())
            if(response.code() ==200){
                return NetworkResponse.Success(response.body())
            }
            return NetworkResponse.Error()
        }
        catch (e:Exception){
            Log.v("Doxx","error"+e.message.toString())
            return NetworkResponse.Error()
        }
    }

    suspend fun doctorPendingAppointment(body: HashMap<String,String>):NetworkResponse<AppointmentList?>{
        try {
            Log.v("Doxx",body["doctorID"].toString())
            val response = apiService.doctorPendingAppointment(body)
            Log.v("Doxx",response.toString())
            if(response.code() ==200){
                return NetworkResponse.Success(response.body())
            }
            return NetworkResponse.Error()
        }
        catch (e:Exception){
            Log.v("Doxx","error"+e.message.toString())
            return NetworkResponse.Error()
        }
    }
    suspend fun addFavDoctor(body: HashMap<String,String>):NetworkResponse<SingleFieldResponse?>{
        try {
            val response = apiService.addFavDoctor(body)
            Log.v("Doxx",response.toString())
            if(response.code() ==200){
                return NetworkResponse.Success(response.body())
            }
            return NetworkResponse.Error()
        }
        catch (e:Exception){
            Log.v("Doxx","error"+e.message.toString())
            return NetworkResponse.Error()
        }
    }

    suspend fun doctorUpcomingAppointment(body: HashMap<String,String>):NetworkResponse<AppointmentList?>{
        try {
            val response = apiService.doctorUpcomingAppointment(body)
            Log.v("Doxx",response.toString())
            if(response.code() ==200){
                return NetworkResponse.Success(response.body())
            }
            return NetworkResponse.Error()
        }
        catch (e:Exception){
            Log.v("Doxx","error"+e.message.toString())
            return NetworkResponse.Error()
        }
    }

    suspend fun patientPastAppointment(body: HashMap<String,String>):NetworkResponse<AppointmentList?>{
        try {
            val response = apiService.patientPastAppointment(body)
            Log.v("Doxx",response.toString())
            if(response.code() ==200){
                return NetworkResponse.Success(response.body())
            }
            return NetworkResponse.Error()
        }
        catch (e:Exception){
            Log.v("Doxx","error"+e.message.toString())
            return NetworkResponse.Error()
        }
    }

    suspend fun patientUpcomingAppointment(body: HashMap<String,String>):NetworkResponse<AppointmentList?>{
        try {
            val response = apiService.patientUpcomingAppointment(body)
            Log.v("Doxx",response.toString())
            if(response.code() ==200){
                return NetworkResponse.Success(response.body())
            }
            return NetworkResponse.Error()
        }
        catch (e:Exception){
            Log.v("Doxx","error"+e.message.toString())
            return NetworkResponse.Error()
        }
    }

    suspend fun rejectAppointment(body: HashMap<String,String>):NetworkResponse<SingleFieldResponse?>{
        try {
            val response = apiService.rejectAppointment(body)
            Log.v("Doxx",response.toString())
            if(response.code() ==200){
                return NetworkResponse.Success(response.body())
            }
            return NetworkResponse.Error()
        }
        catch (e:Exception){
            Log.v("Doxx","error"+e.message.toString())
            return NetworkResponse.Error()
        }
    }

    suspend fun favDocList(body: HashMap<String,String>):NetworkResponse<DoctorList?>{
        try {
            val response = apiService.favDocList(body)
            Log.v("Doxx",response.toString())
            if(response.code() ==200){
                return NetworkResponse.Success(response.body())
            }
            return NetworkResponse.Error()
        }
        catch (e:Exception){
            Log.v("Doxx","error"+e.message.toString())
            return NetworkResponse.Error()
        }
    }

    suspend fun docDetails(body: HashMap<String,String>):NetworkResponse<DoctorDetails?>{
        try {
            val response = apiService.docDetails(body)
            Log.v("Doxx",response.toString())
            if(response.code() ==200){
                return NetworkResponse.Success(response.body())
            }
            return NetworkResponse.Error()
        }
        catch (e:Exception){
            Log.v("Doxx","error"+e.message.toString())
            return NetworkResponse.Error()
        }
    }






}