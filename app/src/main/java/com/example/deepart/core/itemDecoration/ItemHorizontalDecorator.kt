package com.nolbal.nolbal.core.ui.decorator.itemDecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemHorizontalDecorator(
    private val topMargin: Int = 0,
    private val bottomMargin: Int = 0,
    private val startMargin: Int = 0,
    private val endMargin: Int = 0,
    private val firstStartMargin: Int = 0,
    private val lastEndMargin: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION) return

        outRect.run {
            top = topMargin
            bottom = bottomMargin

            when {
                itemPosition == 0 -> {
                    left = firstStartMargin
                    right = endMargin
                }
                itemPosition + 1 == state.itemCount -> {
                    left = startMargin
                    right = lastEndMargin
                }
                else -> {
                    left = startMargin
                    right = endMargin
                }
            }
        }

    }
}