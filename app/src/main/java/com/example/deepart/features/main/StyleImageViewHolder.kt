package com.example.deepart.features.main

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.deepart.R
import com.example.deepart.databinding.LayoutStyleImageBinding
import com.example.deepart.features.main.common.StyleImage

class StyleImageViewHolder(
    private val binding: LayoutStyleImageBinding
) : ViewHolder(binding.root) {

    fun bindView(styleImage: StyleImage, setClickEvent : () -> Unit) {
        binding.titleTV.text = styleImage.title
        binding.styleIV.setImageResource(styleImage.image)
        binding.applyIV.setImageResource(
            if (styleImage.isSelected) R.drawable.ic_selected_black_24
            else R.drawable.ic_not_select_black_24
        )
        binding.rootLayout.setOnClickListener {
            setClickEvent.invoke()
        }
        binding.executePendingBindings()
    }
}