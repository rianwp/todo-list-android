package com.example.finalproject

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class TampilDeskripsi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tampil_deskripsi)
        var intent = getIntent()
        var actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        val lampiran = findViewById<ImageView>(R.id.lampiran_haldesc)
        val judul = findViewById<TextView>(R.id.judul_haldesc)
        val deskripsi = findViewById<TextView>(R.id.deskripsi_haldesc)

        val idimage = intent.getStringExtra("id").toString()
        val tanggal = intent.getStringExtra("tanggal")
        val jam = intent.getStringExtra("jam")
        val doneStatus = intent.getBooleanExtra("doneStatus", true)

        val localfile = File.createTempFile("tempImage", "jpg")
        val storage = FirebaseStorage.getInstance().reference.child("images").child(idimage)

        storage.getFile(localfile)
            .addOnSuccessListener {
                if(it.task.isSuccessful){
                    val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                    lampiran.setImageBitmap(bitmap)
                }
            }
            .addOnFailureListener{
                if(it.message != "Object does not exist at location."){
                    Toast.makeText(
                        baseContext, "Error: ${it.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        val simpleDate = SimpleDateFormat("dd/MM/yyyy HH:mm")
        var currentDate = simpleDate.format(Date())

        judul.text = intent.getStringExtra("judul")
        deskripsi.text = intent.getStringExtra("deskripsi")

        var dataDate = "${tanggal} ${jam}"
        if(!(doneStatus) && simpleDate.parse(currentDate)!! > simpleDate.parse(dataDate)){
            actionBar.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FF7451")))
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}