package com.nolbal.nolbal.core.ui.decorator.itemDecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecorator(private val topMargin: Int, private val bottomMargin: Int, private val startMargin: Int, private val endMargin: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.run {
            left = startMargin
            right = endMargin
            top = topMargin
            bottom = bottomMargin
        }

    }
}