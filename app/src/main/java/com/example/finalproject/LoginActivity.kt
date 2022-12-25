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

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val actionBar = getSupportActionBar()
        actionBar!!.hide()
        val inputemail = findViewById<EditText>(R.id.emailinput)
        val inputpassword = findViewById<EditText>(R.id.passwordinput)
        val btnlogin = findViewById<Button>(R.id.btn_login)
        val linksignup = findViewById<TextView>(R.id.linksignup)
        auth = Firebase.auth

        btnlogin.setOnClickListener{
            auth.signInWithEmailAndPassword(inputemail.text.toString(), inputpassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(baseContext, "Login Berhasil",
                            Toast.LENGTH_SHORT).show()
                        finish()
                        overridePendingTransition(0, 0)
                        startActivity(getIntent())
                        overridePendingTransition(0, 0)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Login Gagal.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
        linksignup.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            //intent ke MainActivity
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}