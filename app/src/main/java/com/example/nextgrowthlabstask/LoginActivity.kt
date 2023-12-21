package com.example.nextgrowthlabstask

import android.annotation.SuppressLint

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.nextgrowthlabstask.databinding.ActivityLoginBinding
import com.example.nextgrowthlabstask.databinding.ActivityMainBinding
import com.example.nextgrowthlabstask.ui.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private var RC_SIGN_IN = 200


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_NextGrowthLabsTask)
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initApp()
        initGoogleSignIn()

    }

    private fun initApp() {
        mAuth = Firebase.auth

        onClickListeners()
    }

    private fun onClickListeners() {
        binding.googleSignInButton.setOnClickListener {
            googleSignIn()
        }

    }
    public override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            updateUI()
        }
    }

    private fun initGoogleSignIn() {
        val default_web_client_id = "610454201286-lbb01aktn0d38gtfdvfivjim8vkcrvvk.apps.googleusercontent.com"

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(default_web_client_id)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                logger("firebase auth with google success")
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                logger("firebase auth with google failure")
                Toast.makeText(
                    baseContext, "Sign in Failure. Retry",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    logger("sign in google with credential success")
                    updateUI()
                } else {
                    logger("sign in google with credential failure")
                    Toast.makeText(
                        baseContext, "Sign in Failure. Retry",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun updateUI() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK
        )
        startActivity(intent)
    }

    private fun logger(s: String) {
        Log.d("Logs: ", "$s")
    }

}