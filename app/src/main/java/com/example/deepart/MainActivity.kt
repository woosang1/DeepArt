package com.example.deepart

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.graphics.drawable.toBitmap
import com.example.deepart.core.base.BaseActivity
import com.example.deepart.core.image.BlurTransformation
import com.example.deepart.core.image.convertUri
import com.example.deepart.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun initBinding(layoutInflater: LayoutInflater): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBgImage()
    }

    private fun setBgImage(){
        // drawable 타입을 bitmap으로 변경
//        val imageBitmap = (binding.previewIV.drawable as? BitmapDrawable)?.bitmap
        val imageBitmap = getDrawable(R.drawable.background5)?.toBitmap()
        imageBitmap?.let { it ->
            Picasso.get()
                .load(it.convertUri(this))
                .resize(100, 0)
                .transform(BlurTransformation(this, 25))
                .into(binding.bgImage)
        }
    }

}