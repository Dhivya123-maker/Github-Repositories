package com.example.nextgrowthlabstask.utils

import android.view.View
import com.google.android.material.tabs.TabLayout

object TabSelector {
    fun View.selectTabOn(position: Int, tab: TabLayout) = setOnClickListener {
        tab.getTabAt(position)?.select()
    }
}