package com.example.nextgrowthlabstask.utils

import android.view.View

object ViewVisibilityUtil {
    fun View.setVisible() { visibility = View.VISIBLE }

    fun View.setInvisible() { visibility = View.INVISIBLE }

    fun View.setGone() { visibility = View.GONE }
}