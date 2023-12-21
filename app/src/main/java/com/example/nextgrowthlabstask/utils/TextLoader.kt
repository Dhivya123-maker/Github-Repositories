package com.example.nextgrowthlabstask.utils

import android.widget.TextView
import com.example.nextgrowthlabstask.utils.ViewVisibilityUtil.setGone

object TextLoader {
    fun TextView.loadData(data: String?) {
        if (!data.isNullOrEmpty()) this.text = data else this.setGone()
    }
}