package com.oogwayapps.meddoxy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oogwayapps.meddoxy.models.*
import com.oogwayapps.meddoxy.repo.MedRepo
import com.oogwayapps.meddoxy.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class MedViewmodel @Inject constructor(private val medRepo: MedRepo):ViewModel() {
    private var isLoading:MutableLiveData<Boolean> =MutableLiveData()
    val _isLoading:LiveData<Boolean> = isLoading

    private var existingLiveData:MutableLiveData<NetworkResponse<SingleFieldResponse?>> = MutableLiveData()
    val _existingLiveData:LiveData<NetworkResponse<SingleFieldResponse?>> = existingLiveData

    private var _patientSignUpLiveData:MutableLiveData<NetworkResponse<SingleFieldResponse?>> = MutableLiveData()
    val patientSignUpLiveData:LiveData<NetworkResponse<SingleFieldResponse?>> = _patientSignUpLiveData

    private var _verifyOtpLiveData:MutableLiveData<NetworkResponse<VerificationResponse?>> = MutableLiveData()
    val verifyOtpLiveData:LiveData<NetworkResponse<VerificationResponse?>> = _verifyOtpLiveData

    private var _docListLiveData:MutableLiveData<NetworkResponse<DoctorList?>> = MutableLiveData()
    val docListLiveData:LiveData<NetworkResponse<DoctorList?>> = _docListLiveData

    private var _favDocListLiveData:MutableLiveData<NetworkResponse<DoctorList?>> = MutableLiveData()
    val favDocListLiveData:LiveData<NetworkResponse<DoctorList?>> = _favDocListLiveData

    private var _docDetailsLiveData:MutableLiveData<NetworkResponse<DoctorDetails?>> = MutableLiveData()
    val docDetailsLiveData:LiveData<NetworkResponse<DoctorDetails?>> = _docDetailsLiveData

    private var _requestAppointmentLiveData:MutableLiveData<NetworkResponse<AppointmentDetails?>> = MutableLiveData()
    val requestAppointmentLiveData:LiveData<NetworkResponse<AppointmentDetails?>> = _requestAppointmentLiveData

    private var _confirmAppointmentLiveData:MutableLiveData<NetworkResponse<SingleFieldResponse?>> = MutableLiveData()
    val confirmAppointmentLiveData:LiveData<NetworkResponse<SingleFieldResponse?>> = _confirmAppointmentLiveData

    private var _rejectAppointmentLiveData:MutableLiveData<NetworkResponse<SingleFieldResponse?>> = MutableLiveData()
    val rejectAppointmentLiveData:LiveData<NetworkResponse<SingleFieldResponse?>> = _rejectAppointmentLiveData

    private var _markAsCompAppointmentLiveData:MutableLiveData<NetworkResponse<SingleFieldResponse?>> = MutableLiveData()
    val markAsCompAppointmentLiveData:LiveData<NetworkResponse<SingleFieldResponse?>> = _markAsCompAppointmentLiveData

    private var _docUpAppointmentLiveData:MutableLiveData<NetworkResponse<AppointmentList?>> = MutableLiveData()
    val docUpAppointmentLiveData:LiveData<NetworkResponse<AppointmentList?>> = _docUpAppointmentLiveData

    private var _docPastAppointmentLiveData:MutableLiveData<NetworkResponse<AppointmentList?>> = MutableLiveData()
    val docPastAppointmentLiveData:LiveData<NetworkResponse<AppointmentList?>> = _docPastAppointmentLiveData

    private var _docPendingAppointmentLiveData:MutableLiveData<NetworkResponse<AppointmentList?>> = MutableLiveData()
    val docPendingAppointmentLiveData:LiveData<NetworkResponse<AppointmentList?>> = _docPendingAppointmentLiveData

    private var _patUpAppointmentLiveData:MutableLiveData<NetworkResponse<AppointmentList?>> = MutableLiveData()
    val patUpAppointmentLiveData:LiveData<NetworkResponse<AppointmentList?>> = _patUpAppointmentLiveData

    private var _patPastAppointmentLiveData:MutableLiveData<NetworkResponse<AppointmentList?>> = MutableLiveData()
    val patPastAppointmentLiveData:LiveData<NetworkResponse<AppointmentList?>> = _patPastAppointmentLiveData

    private var _doctSignUpLiveData:MutableLiveData<NetworkResponse<SingleFieldResponse?>> = MutableLiveData()
    val doctSignUpLiveData:LiveData<NetworkResponse<SingleFieldResponse?>> = _doctSignUpLiveData

    private var _uploadLiveData:MutableLiveData<NetworkResponse<SingleFieldResponse?>> = MutableLiveData()
    val uploadLiveData:LiveData<NetworkResponse<SingleFieldResponse?>> = _uploadLiveData

    private var _addFavDoctorLiveData:MutableLiveData<NetworkResponse<SingleFieldResponse?>> = MutableLiveData()
    val addFavDoctorLiveData:LiveData<NetworkResponse<SingleFieldResponse?>> = _addFavDoctorLiveData



    fun existing(email:String) = viewModelScope.launch {
        isLoading.postValue(true)
        val response = medRepo.existing(email)
        isLoading.postValue(false)
        existingLiveData.postValue(response)
    }

    fun patientSignUp(email:String,name :String, address: String) = viewModelScope.launch {
        isLoading.postValue(true)
        val response = medRepo.patientSignUp(PatientSignUpRequest(email,name,address))
        isLoading.postValue(false)
        _patientSignUpLiveData.postValue(response)
    }

    fun addFavDoctor(body :HashMap<String,String>) = viewModelScope.launch {
        isLoading.postValue(true)
        val response = medRepo.addFavDoctor(body)
        isLoading.postValue(false)
        _addFavDoctorLiveData.postValue(response)
    }

    fun upload(image: MultipartBody.Part, Id: MultipartBody.Part) = viewModelScope.launch {
        isLoading.postValue(true)
        val response = medRepo.upload(image, Id)
        isLoading.postValue(false)
        _uploadLiveData.postValue(response)
    }

    fun verifyOtp(email:String, otp:String) = viewModelScope.launch {
        isLoading.postValue(true)
        val response = medRepo.verifyOtp(VerificationRequest(email, otp))
        isLoading.postValue(false)
        _verifyOtpLiveData.postValue(response)
    }

    fun docList(specialization:String) = viewModelScope.launch {
        isLoading.postValue(true)
        val response = medRepo.docList(DocListRequest(specialization))
        isLoading.postValue(false)
        _docListLiveData.postValue(response)
    }

    fun requestAppointment(body :HashMap<String,String>) = viewModelScope.launch {
        isLoading.postValue(true)
        val response = medRepo.requestAppointment(body)
        isLoading.postValue(false)
        _requestAppointmentLiveData.postValue(response)
    }

    fun confirmAppointment(body :HashMap<String,String>) = viewModelScope.launch {
        isLoading.postValue(true)
        val response = medRepo.confirmAppointment(body)
        isLoading.postValue(false)
        _confirmAppointmentLiveData.postValue(response)
    }
    fun rejectAppointment(body :HashMap<String,String>) = viewModelScope.launch {
        isLoading.postValue(true)
        val response = medRepo.rejectAppointment(body)
        isLoading.postValue(false)
        _rejectAppointmentLiveData.postValue(response)
    }
    fun markAsCompleted(body :HashMap<String,String>) = viewModelScope.launch {
        isLoading.postValue(true)
        val response = medRepo.markAsCompleted(body)
        isLoading.postValue(false)
        _markAsCompAppointmentLiveData.postValue(response)
    }
    fun doctorPastAppointment(body :HashMap<String,String>) = viewModelScope.launch {
        isLoading.postValue(true)
        val response = medRepo.doctorPastAppointment(body)
        isLoading.postValue(false)
        _docPastAppointmentLiveData.postValue(response)
    }
    fun doctorPendingAppointment(body :HashMap<String,String>) = viewModelScope.launch {
        isLoading.postValue(true)
        val response = medRepo.doctorPendingAppointment(body)
        isLoading.postValue(false)
        _docPendingAppointmentLiveData.postValue(response)
    }
    fun doctorUpcomingAppointment(body :HashMap<String,String>) = viewModelScope.launch {
        isLoading.postValue(true)
        val response = medRepo.doctorUpcomingAppointment(body)
        isLoading.postValue(false)
        _docUpAppointmentLiveData.postValue(response)
    }
    fun patientPastAppointment(body :HashMap<String,String>) = viewModelScope.launch {
        isLoading.postValue(true)
        val response = medRepo.patientPastAppointment(body)
        isLoading.postValue(false)
        _patPastAppointmentLiveData.postValue(response)
    }
    fun patientUpcomingAppointment(body :HashMap<String,String>) = viewModelScope.launch {
        isLoading.postValue(true)
        val response = medRepo.patientUpcomingAppointment(body)
        isLoading.postValue(false)
        _patUpAppointmentLiveData.postValue(response)
    }

    fun favDocList(body :HashMap<String,String>) = viewModelScope.launch {
        isLoading.postValue(true)
        val response = medRepo.favDocList(body)
        isLoading.postValue(false)
        _favDocListLiveData.postValue(response)
    }

    fun docDetails(body :HashMap<String,String>) = viewModelScope.launch {
        isLoading.postValue(true)
        val response = medRepo.docDetails(body)
        isLoading.postValue(false)
        _docDetailsLiveData.postValue(response)
    }

    fun doctorSignUp(doctorSignUpRequest: DoctorSignUpRequest) = viewModelScope.launch {
        isLoading.postValue(true)
        val response = medRepo.doctorSignUp(doctorSignUpRequest)
        isLoading.postValue(false)
        _doctSignUpLiveData.postValue(response)
    }







}