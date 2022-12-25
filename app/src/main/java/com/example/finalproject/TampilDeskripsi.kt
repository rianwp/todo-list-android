package com.example.finalproject

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
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

        val judul = findViewById<TextView>(R.id.judul_haldesc)
        val deskripsi = findViewById<TextView>(R.id.deskripsi_haldesc)
        val tanggal = intent.getStringExtra("tanggal")
        val jam = intent.getStringExtra("jam")
        val doneStatus = intent.getBooleanExtra("doneStatus", true)

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