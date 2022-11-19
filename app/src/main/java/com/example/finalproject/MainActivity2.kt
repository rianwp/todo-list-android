package com.example.finalproject

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.ActionBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val actionBar = getSupportActionBar()
        actionBar!!.setTitle("Tambah Aktivitas")
        actionBar.setDisplayHomeAsUpEnabled(true)
        var cal = Calendar.getInstance()
        val simpleDate = SimpleDateFormat("dd/MM/yyyy hh:mm")
        val btndate = findViewById<TextView>(R.id.btn_kalender)
        val btntime = findViewById<TextView>(R.id.btn_jam)
        val tanggal = findViewById<TextView>(R.id.tanggal)
        val btnlampiran = findViewById<TextView>(R.id.btn_lampiran)
        val btncheck = findViewById<FloatingActionButton>(R.id.btn_check)

        val dateSetListener = object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                tanggal.text = simpleDate.format(cal.getTime())
            }
        }
        val timeSetListener = object : TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(
                view: TimePicker, hour: Int, minute: Int
            ) {
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                tanggal.text = simpleDate.format(cal.getTime())
            }
        }
        btndate.setOnClickListener{
            DatePickerDialog(this@MainActivity2,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        btntime.setOnClickListener{
            TimePickerDialog(
                this@MainActivity2,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
        btnlampiran.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivity(intent)
        }
        btncheck.setOnClickListener{
            val intent = Intent(this@MainActivity2, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}