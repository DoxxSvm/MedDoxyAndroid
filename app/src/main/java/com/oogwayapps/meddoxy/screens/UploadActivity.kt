package com.oogwayapps.meddoxy.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.databinding.ActivityUploadBinding
import com.oogwayapps.meddoxy.viewmodel.MedViewmodel

class UploadActivity : AppCompatActivity() {
    lateinit var binding: ActivityUploadBinding
    val viewmodel by viewModels<MedViewmodel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}