package com.example.deepart.features.main.container.styleImage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.deepart.R
import com.example.deepart.core.base.BaseRecyclerAdapter
import com.example.deepart.databinding.LayoutStyleImageBinding
import com.example.deepart.features.main.MainViewModel
import com.example.deepart.features.main.common.MainSideEffect
import com.example.deepart.features.main.common.StyleImage

class StyleImageAdapter(
    private val mainViewModel: MainViewModel
) : BaseRecyclerAdapter<StyleImage>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StyleImageViewHolder {
        return StyleImageViewHolder(binding = LayoutStyleImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(defaultViewHolder: RecyclerView.ViewHolder, position: Int) {
        val styleImage = model[position]
        (defaultViewHolder as? StyleImageViewHolder)?.bindView(
            styleImage = styleImage,
            setClickEvent = {
                styleImage.isSelected = !styleImage.isSelected
                model.forEachIndexed { index, styleImage -> if (index != position) styleImage.isSelected = false }
                notifyDataSetChanged()
                val drawable = ContextCompat.getDrawable(defaultViewHolder.itemView.context, styleImage.image)
                drawable?.toBitmapOrNull()?.let {
                    mainViewModel.postAction(MainSideEffect.TransImage(it))
                }
            }
        )
    }

    override fun getItemCount(): Int = model.size
}