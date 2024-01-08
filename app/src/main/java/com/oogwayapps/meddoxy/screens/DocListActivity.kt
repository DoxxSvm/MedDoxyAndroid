package com.oogwayapps.meddoxy.screens

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieDrawable
import com.oogwayapps.meddoxy.R
import com.oogwayapps.meddoxy.adapters.DoctorAdapter
import com.oogwayapps.meddoxy.databinding.ActivityDocListBinding
import com.oogwayapps.meddoxy.databinding.FragmentHomeBinding
import com.oogwayapps.meddoxy.models.DoctorDetails
import com.oogwayapps.meddoxy.models.DoctorList
import com.oogwayapps.meddoxy.utils.NetworkResponse
import com.oogwayapps.meddoxy.viewmodel.MedViewmodel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notify

@AndroidEntryPoint
class DocListActivity : AppCompatActivity() {
    val args:DocListActivityArgs by navArgs()
    lateinit var binding: ActivityDocListBinding
    private val viewmodel by viewModels<MedViewmodel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDocListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val spc = args.specialization
        binding.progressBar.setAnimation(R.raw.pbar)
        binding.progressBar.repeatCount = LottieDrawable.INFINITE
        binding.progressBar.playAnimation()
        val colorDrawable = ColorDrawable(Color.parseColor("#113137"))
        supportActionBar?.setBackgroundDrawable(colorDrawable)


        viewmodel.docList(spc)

        viewmodel.docListLiveData.observe(this){
            when(it){
                is NetworkResponse.Success ->{
                    setUpRv(it.response)
                }
                is NetworkResponse.Error ->{
                    Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewmodel._isLoading.observe(this){
            when(it){
                 true ->{
                    binding.progressBar.isVisible = true
                }
                 false ->{
                     binding.progressBar.isVisible = false
                }
            }
        }


    }

    private fun setUpRv(response: DoctorList?) {
        val arr :ArrayList<DoctorDetails> = arrayListOf()
        response?.forEach{
            arr.add(it)
        }
        Log.v("Doxx",response.toString())
        val adap = DoctorAdapter(::onclick,)
        binding.docList.apply {
            adapter = adap
            layoutAnimation = LayoutAnimationController(AnimationUtils.loadAnimation(this@DocListActivity,R.anim.scale_up),1f)
            layoutManager = LinearLayoutManager(this@DocListActivity)
        }

        adap.differ.submitList(arr as MutableList<DoctorDetails>)
    }

    private fun onclick(doctorDetails: DoctorDetails){
        val intent = Intent(this,AppointmentActivity::class.java)
        intent.putExtra("details",doctorDetails)
        startActivity(intent)
    }
}