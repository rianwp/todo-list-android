package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth

class SignupActivity : AppCompatActivity() {
    var backPressedTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val actionBar = getSupportActionBar()
        actionBar!!.hide()
        val inputemail = findViewById<EditText>(R.id.emailinput)
        val inputpassword = findViewById<EditText>(R.id.passwordinput)
        val btnsignup = findViewById<Button>(R.id.btn_signup)
        val linklogin = findViewById<TextView>(R.id.linklogin)
        auth = Firebase.auth

        btnsignup.setOnClickListener{
            auth.createUserWithEmailAndPassword(inputemail.text.toString(), inputpassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(baseContext, "Sign Up Berhasil",
                            Toast.LENGTH_SHORT).show()
                        finish()
                        overridePendingTransition(0, 0)
                        startActivity(getIntent())
                        overridePendingTransition(0, 0)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Sign Up Gagal",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
        linklogin.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            //intent ke MainActivity
            val intent = Intent(this@SignupActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finishAffinity()
        } else {
            Toast.makeText(this, "Ketuk lagi untuk keluar dari aplikasi", Toast.LENGTH_LONG).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}