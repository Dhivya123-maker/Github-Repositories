package com.example.nextgrowthlabstask.utils

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.example.nextgrowthlabstask.R

object SnackBarExt {
    fun Context.showSnackBar(view: View?, msg: String?) {
        if (view != null) {
            val snackBar = Snackbar.make(view, msg.toString(), Snackbar.LENGTH_SHORT).apply {
                setBackgroundTint(ContextCompat.getColor(this@showSnackBar, R.color.snackbar))
                setTextColor(ContextCompat.getColor(this@showSnackBar, R.color.white))

            }
            snackBar.show()
        }
    }
}