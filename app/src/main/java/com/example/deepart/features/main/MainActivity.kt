package com.example.deepart.features.main

import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.graphics.drawable.toBitmap
import com.example.deepart.R
import com.example.deepart.core.base.BaseActivity
import com.example.deepart.core.image.BlurTransformation
import com.example.deepart.core.image.convertUri
import com.example.deepart.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deepart.features.main.common.MainSideEffect
import com.example.deepart.features.main.common.MainState
import com.example.deepart.features.main.common.StyleImage
import com.example.deepart.utils.dpToPixel
import com.example.deepart.utils.getHeightDisplay
import com.example.deepart.utils.showToast
import com.nolbal.nolbal.core.ui.decorator.itemDecoration.ItemHorizontalDecorator
import org.orbitmvi.orbit.viewmodel.observe


class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun initBinding(layoutInflater: LayoutInflater): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    override fun onInitBinding() {
        setHeightThumbnail()
        setStyleRecyclerView()
        setStyleImage()
        setClickListener()
        setBgImage()
    }

    override fun setObserver() {
        super.setObserver()
        mainViewModel.observe(
            lifecycleOwner = this,
            state = ::render,
            sideEffect = ::handleSideEffect
        )
    }

    private fun render(state: MainState) {}
    private fun handleSideEffect(sideEffect: MainSideEffect) {
        when (sideEffect) {
            is MainSideEffect.OpenCamera -> {

            }
            is MainSideEffect.OpenGallery -> {

            }
            is MainSideEffect.EditImage -> {

            }
            is MainSideEffect.SaveImage -> {

            }
            is MainSideEffect.StartCommunity -> {

            }
            is MainSideEffect.ShowOptionPopup -> {

            }
            is MainSideEffect.ShowToast -> {
                showToast(sideEffect.message)
            }
        }
    }

    private fun setBgImage() {
        val imageBitmap = getDrawable(R.drawable.background5)?.toBitmap()
        imageBitmap?.let { it ->
            Picasso.get()
                .load(it.convertUri(this))
                .resize(100, 0)
                .transform(BlurTransformation(this, 25))
                .into(binding.bgImage)
        }
    }

    private fun setHeightThumbnail(){
        // 60(툴바) - 190(리스트) - 75(하단버튼)
        binding.thumbnail.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            getHeightDisplay() - 60.dpToPixel() - 190.dpToPixel() - 75.dpToPixel()
        ).apply {
            setMargins(0,10,0,0)
        }
    }

    private fun setStyleRecyclerView(){
        binding.styleRecyclerView.apply {
            adapter = StyleImageAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            while (itemDecorationCount > 0) { removeItemDecorationAt(0) }
            addItemDecoration(
                ItemHorizontalDecorator(
                    topMargin = 0.dpToPixel(),
                    bottomMargin = 0.dpToPixel(),
                    startMargin = 0.dpToPixel(),
                    endMargin = 0.dpToPixel(),
                    firstStartMargin = 0.dpToPixel(),
                    lastEndMargin = 0.dpToPixel()
                )
            )
        }
    }

    private fun setStyleImage(){
        (binding.styleRecyclerView.adapter as? StyleImageAdapter)?.run {
            model.clear()
            addData(ArrayList(StyleImage.values().toList()))
        }
    }

    private fun setClickListener(){
        binding.cameraBtn.setOnClickListener { mainViewModel.postAction(MainSideEffect.OpenCamera) }
        binding.galleryBtn.setOnClickListener { mainViewModel.postAction(MainSideEffect.OpenGallery) }
        binding.editBtn.setOnClickListener { mainViewModel.postAction(MainSideEffect.EditImage) }
        binding.saveBtn.setOnClickListener { mainViewModel.postAction(MainSideEffect.SaveImage) }
        binding.communityBtn.setOnClickListener { mainViewModel.postAction(MainSideEffect.StartCommunity) }
        binding.optionId.setOnClickListener { mainViewModel.postAction(MainSideEffect.ShowOptionPopup) }
    }

}