package com.oogwayapps.meddoxy.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.databinding.FragmentDocListBinding
import com.oogwayapps.meddoxy.databinding.FragmentDoctorSignUpBinding
import com.oogwayapps.meddoxy.viewmodel.MedViewmodel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DocListFragment : Fragment() {
    private lateinit var binding: FragmentDocListBinding
    private val viewmodel by viewModels<MedViewmodel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDocListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}