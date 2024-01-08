package com.oogwayapps.meddoxy.network

import com.oogwayapps.meddoxy.models.*
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface ApiService {


    @POST("patient/existing")
    suspend fun existing( @Body existingRequestBody: ExistingRequestBody):Response<SingleFieldResponse>

    @POST("patient/signup")
    suspend fun patientSignUp( @Body patientSignUpRequest: PatientSignUpRequest):Response<SingleFieldResponse>

    @POST("patient/docsignup")
    suspend fun doctorSignUp( @Body doctorSignUpRequest: DoctorSignUpRequest):Response<SingleFieldResponse>

    @POST("patient/verifyotp")
    suspend fun verifyOtp( @Body verificationRequest: VerificationRequest):Response<VerificationResponse>


    @Multipart
    @POST("upload")
    suspend fun upload(@Part image: MultipartBody.Part, @Part id: MultipartBody.Part): Response<SingleFieldResponse>
//    @HTTP(method = "GET", path = "event/eventRemovePicture", hasBody = true)
    @POST("utils/doclist")
    suspend fun docList(@Body docListRequest: DocListRequest):Response<DoctorList>

    @POST("utils/docdetails")
    suspend fun docDetails(@Body body:HashMap<String,String> ):Response<DoctorDetails>

    @POST("utils/favdocdetails")
    suspend fun favDocList(@Body body:HashMap<String,String> ):Response<DoctorList>

    @POST("utils/addfavdoctor")
    suspend fun addFavDoctor(@Body body:HashMap<String,String> ):Response<SingleFieldResponse>

    @POST("appointment/requestappointment")
    suspend fun requestAppointment(@Body body:HashMap<String,String> ):Response<AppointmentDetails>

    @POST("appointment/confirmappointment")
    suspend fun confirmAppointment(@Body body:HashMap<String,String> ):Response<SingleFieldResponse>

    @POST("appointment/doctorpastappointment")
    suspend fun doctorPastAppointment(@Body body:HashMap<String,String> ):Response<AppointmentList>

    @POST("appointment/doctorupcomingappointment")
    suspend fun doctorUpcomingAppointment(@Body body:HashMap<String,String> ):Response<AppointmentList>

    @POST("appointment/patientpastappointment")
    suspend fun patientPastAppointment(@Body body:HashMap<String,String> ):Response<AppointmentList>

    @POST("appointment/patientupcomingappointment")
    suspend fun patientUpcomingAppointment(@Body body:HashMap<String,String> ):Response<AppointmentList>

    @POST("appointment/rejectappointment")
    suspend fun rejectAppointment(@Body body:HashMap<String,String> ):Response<SingleFieldResponse>

    @POST("appointment/markascompleted")
    suspend fun markAsCompleted(@Body body:HashMap<String,String> ):Response<SingleFieldResponse>

    @POST("appointment/doctorpendingappointment")
    suspend fun doctorPendingAppointment(@Body body:HashMap<String,String> ):Response<AppointmentList>








}