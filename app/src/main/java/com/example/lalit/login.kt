package com.example.lalit

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lalit.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class login : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        //[google sing ]
        //r1
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //[ye web id firebase ye leke yaha ek string me dalni hoti hai]
            .requestIdToken(getString(R.string.webid)).requestEmail().build()

        //inittilize googlesigninclite
        //2.( googleSignInClient ko initilize  kiya yani usme us webid ko dala)
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        //r2
        binding.googles.setOnClickListener() {
            val gs = googleSignInClient.signInIntent
            launcher.launch(gs)
        }


        //hum text pe bhi clik listner laga skte hai
        binding.forgetp.setOnClickListener() {
            var intent = Intent(this, forgetp::class.java)
            startActivity(intent)
        }
        binding.singupi.setOnClickListener()
        {
            var intent = Intent(this, ragister::class.java)
            startActivity(intent)
        }
        binding.logii.setOnClickListener()
        {
            var emailp = binding.email.text.toString()
            var pass = binding.passwordd.text.toString()
            if (emailp.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "please fill both are frist", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(emailp, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            var intent = Intent(this,start1::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "invalid try again", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }

    }

    //r3
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount = task.result
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener() { stask ->
                            if (stask.isSuccessful) {
                                Toast.makeText(this, "login accauont", Toast.LENGTH_SHORT).show()
                                var intent = Intent(this, start1::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "google sign faild", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "faild", Toast.LENGTH_SHORT).show()
                }

            }

        }
            override fun onStart() {
                super.onStart()
                var currentUser = auth.currentUser
                if (currentUser != null) {

                    var intent = Intent(this, start1::class.java)
                    startActivity(intent)
                    finish()

                }
            }

        }
