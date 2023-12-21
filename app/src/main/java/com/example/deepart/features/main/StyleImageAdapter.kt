package com.example.deepart.features.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deepart.core.base.BaseRecyclerAdapter
import com.example.deepart.databinding.LayoutStyleImageBinding
import com.example.deepart.features.main.common.StyleImage

class StyleImageAdapter() : BaseRecyclerAdapter<StyleImage>() {

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
            }
        )
    }

    override fun getItemCount(): Int = model.size
}