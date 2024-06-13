package com.example.lalit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lalit.databinding.ActivityRagisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class ragister : AppCompatActivity() {
    lateinit var binding: ActivityRagisterBinding
    lateinit var auth: FirebaseAuth
    lateinit var name:String
    lateinit var email: String
    lateinit var pass:String
    lateinit var rpass:String
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRagisterBinding.inflate(layoutInflater)
        setContentView(binding.root)



       //
        binding.singin.setOnClickListener()
        {
            var intent= Intent(this,login::class.java)
            startActivity(intent)
        }

        // authenticatin ke liye
        auth=Firebase.auth

        //data save ke liye
        databaseReference=Firebase.database.reference

        binding.register.setOnClickListener()
        {
             email=binding.emaill.text.toString().trim()
             name=binding.name.text.toString().trim()
             pass=binding.password.text.toString().trim()
             rpass=binding.repassword.text.toString().trim()
            if(email.isEmpty()||name.isEmpty()||pass.isEmpty()||rpass.isEmpty())
            {
                Toast.makeText(this, "please fill all blanks", Toast.LENGTH_SHORT).show()
            }
            else if(pass!=rpass)
            {
                Toast.makeText(this, "please fill both are same", Toast.LENGTH_SHORT).show()
            }
            else
            {
                creatuser(email,pass)
                var intent=Intent(this,login::class.java)
                startActivity(intent)
                finish()

            }

        }

    }

    private fun creatuser(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email,pass)
            .addOnCompleteListener(this){ task->
                if(task.isSuccessful)
                {
                    //r1.6 agar accaount craeting task successful ho gya to toast show hoaga or saveuserdata fun call kiya
                    userdata()
                    Toast.makeText(this, "creat accaount", Toast.LENGTH_SHORT).show()

                }
                else
                {
                    Toast.makeText(this, "faild", Toast.LENGTH_SHORT).show()
                }

            }

    }
    //r yaha hamne user ke ragister data ko save akraya firebase me

    private fun userdata() {
         email=binding.emaill.text.toString().trim()
         name=binding.name.text.toString().trim()
         pass=binding.password.text.toString().trim()
         rpass=binding.repassword.text.toString().trim()
        var user=usermodal(email,name,pass,rpass)
        var userid=FirebaseAuth.getInstance().currentUser?.uid
        databaseReference.child("user").child(userid!!).setValue(user)
    }
}