package com.example.nextgrowthlabstask.ui.main

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.nextgrowthlabstask.R
import com.example.nextgrowthlabstask.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.nextgrowthlabstask.ui.main.MainViewModel
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var mAuth: FirebaseAuth
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_NextGrowthLabsTask)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initThemeMode()
        mAuth = Firebase.auth

        actionBarToggle = ActionBarDrawerToggle(this, binding.myDrawerLayout, 0, 0)
        binding.myDrawerLayout.addDrawerListener(actionBarToggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        actionBarToggle.syncState()

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.nav_logout -> {
                    showAlertDialog(
                        "Logout",
                        "Are you sure to Log out?",
                        "Yes",
                        { dialog, _ ->
                            dialog.dismiss()
                            signOut()
                        },
                        "Cancel",
                        { dialog, _ ->
                            dialog.dismiss()
                        },
                        false
                    )
                    true
                }
                else -> {
                    false
                }
            }
        }

    }


    private fun signOut() {
        Firebase.auth.signOut()
        finishAffinity()

    }

    private fun showAlertDialog(
        title: String,
        msg: String,
        positiveLabel: String,
        positiveOnClick: DialogInterface.OnClickListener,
        negativeLabel: String,
        negativeOnClick: DialogInterface.OnClickListener,
        isCancelable: Boolean
    ): AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton(positiveLabel, positiveOnClick)
        builder.setNegativeButton(negativeLabel, negativeOnClick)
        builder.setCancelable(isCancelable)

        val alert = builder.create()
        alert.show()
        return alert
    }
    private fun initThemeMode() {
        viewModel.isDarkModeActive().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}