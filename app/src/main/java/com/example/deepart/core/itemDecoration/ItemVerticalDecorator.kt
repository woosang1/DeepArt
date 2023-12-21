package com.nolbal.nolbal.core.ui.decorator.itemDecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemVerticalDecorator(
    private val topMargin: Int = 0,
    private val bottomMargin: Int = 0,
    private val startMargin: Int = 0,
    private val endMargin: Int = 0,
    private val firstTopMargin: Int = 0,
    private val lastBottomMargin: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION) return

        outRect.run {
            left = startMargin
            right = endMargin


            when {
                state.itemCount == 1 -> {
                    top = firstTopMargin
                    bottom = lastBottomMargin
                }
                itemPosition == 0 -> {
                    top = firstTopMargin
                    bottom = bottomMargin
                }
                itemPosition + 1 == state.itemCount -> {
                    top = topMargin
                    bottom = lastBottomMargin
                }
                else -> {
                    top = topMargin
                    bottom = bottomMargin
                }
            }
        }

    }
}