package com.example.deepart.features.main.container

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deepart.R
import com.example.deepart.core.base.BaseActivity
import com.example.deepart.core.image.BlurTransformation
import com.example.deepart.core.image.convertUri
import com.example.deepart.databinding.ActivityMainBinding
import com.example.deepart.features.main.MainViewModel
import com.example.deepart.features.main.common.MainSideEffect
import com.example.deepart.features.main.common.MainState
import com.example.deepart.features.main.common.MainUiState
import com.example.deepart.features.main.common.StyleImage
import com.example.deepart.features.main.container.styleImage.StyleImageAdapter
import com.example.deepart.utils.ImageUtils
import com.example.deepart.utils.dpToPixel
import com.example.deepart.utils.getHeightDisplay
import com.example.deepart.utils.showToast
import com.nolbal.nolbal.core.ui.decorator.itemDecoration.ItemHorizontalDecorator
import com.squareup.picasso.Picasso
import org.orbitmvi.orbit.viewmodel.observe
import java.io.File
import java.io.IOException


class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        /** Camera **/
        private const val CAMERA_PERMISSION_REQUEST_CODE = 1001
        const val REQUEST_IMAGE_CAPTURE = 1

        const val IMAGE_LOW_BOUND_VALUE = 1280

    }

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
        mainViewModel.observe(lifecycleOwner = this, state = ::render, sideEffect = ::handleSideEffect)
    }

    private fun render(state: MainState) {
        when(state.mainUiState){
            is MainUiState.Init -> {

            }
            is MainUiState.Result -> {
                Log.i("logger", "is MainUiState.Result -> ")
                Log.i("logger", "state.mainUiState.bitmap : ${state.mainUiState.bitmap}")
//                start_img = currentBitmap
//                setUpPreviewImageData()
                setStyleImage()
                binding.thumbnail.setImageBitmap(state.mainUiState.bitmap)
            }
        }
    }

//    fun setUpPreviewImageData(currentBitmap: Bitmap) {
//        var currentResizedResolution :  = ImageUtils.getResizedResolution(
//            currentBitmap.height, currentBitmap.width,
//            MainActivity.IMAGE_LOW_BOUND_VALUE
//        )
//        var currentResizedBitmap = ImageUtils.getResizedBitmap(
//            currentBitmap, currentResizedResolution.get(0), currentResizedResolution.get(1)
//        )
//        currentResizedResolution.get(1) = MainActivity.IMAGE_LOW_BOUND_VALUE
//        currentResizedResolution.get(0) = currentResizedResolution.get(1)
//        currentResizedBitmap = ImageUtils.cropBitmap(currentResizedBitmap)
//        val currentResizedBitmapCopy = Bitmap.createBitmap(currentResizedBitmap)
//    }

    private fun handleSideEffect(sideEffect: MainSideEffect) {
        when (sideEffect) {
            is MainSideEffect.OpenCamera -> {
                checkCameraPermission()
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

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                // 카메라 권한 허용 시,
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else { }
            }
        }
    }


    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // If permission is not granted, request it.
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            openCamera()
            // If permission is already granted, proceed with the camera action.
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("logger", "onActivityResult")
        Log.i("logger", "requestCode : ${requestCode}")
        Log.i("logger", "resultCode : ${resultCode}")
        // 사진 촬영 성공 후 되돌아올 시,
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // 이미지를 Bitmap으로 변환
            val bitmap = data?.extras?.get("data") as Bitmap?
            // Bitmap을 사용하여 필요한 작업 수행
            if (bitmap != null) {
                val rotate = ImageUtils.getRotateMatrix(mainViewModel.currentImagePath)
                val currentBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, rotate, true)
                mainViewModel.changeResultState(currentBitmap)
            }
        }
    }
}