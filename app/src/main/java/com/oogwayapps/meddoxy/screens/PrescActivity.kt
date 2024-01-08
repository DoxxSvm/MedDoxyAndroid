package com.oogwayapps.meddoxy.screens

import android.graphics.drawable.Drawable
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.oogwayapps.meddoxy.R
import com.bumptech.glide.request.target.Target
import com.oogwayapps.meddoxy.databinding.ActivityPrescBinding
import com.oogwayapps.meddoxy.viewmodel.MedViewmodel

class PrescActivity : AppCompatActivity() {
    lateinit var binding: ActivityPrescBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPrescBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressBar.isVisible = true
        binding.progressBar.setAnimation(R.raw.pbar)
        binding.progressBar.repeatCount = LottieDrawable.INFINITE
        binding.progressBar.playAnimation()


        val url = intent.getStringExtra("url")
        Log.v("Doxx",url.toString())
        Glide.with(this)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    return false
                }
                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    //do something when picture already loaded
                    binding.progressBar.isVisible = false
                    return false
                }
            })
            .into(binding.img)


    }
}