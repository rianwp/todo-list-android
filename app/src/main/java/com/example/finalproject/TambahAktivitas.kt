package com.example.finalproject

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth

class TambahAktivitas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_aktivitas)
        val actionBar = getSupportActionBar()
        actionBar!!.setTitle("Tambah Aktivitas")
        actionBar.setDisplayHomeAsUpEnabled(true)
        auth = Firebase.auth

        val cal = Calendar.getInstance()
        val jamFormat = SimpleDateFormat("HH:mm")
        val tanggalFormat = SimpleDateFormat("dd/MM/yyyy")
        val fullDate = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val inputjudul = findViewById<EditText>(R.id.judulinput)
        val inputdeskripsi = findViewById<EditText>(R.id.deskripsiinput)
        val btndate = findViewById<TextView>(R.id.btn_kalender)
        val btntime = findViewById<TextView>(R.id.btn_jam)
        val tanggal = findViewById<TextView>(R.id.tanggal)
        val btnlampiran = findViewById<TextView>(R.id.btn_lampiran)
        val btncheck = findViewById<FloatingActionButton>(R.id.btn_check)

        tanggal.text = fullDate.format(cal.getTime())

        val dateSetListener = object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                tanggal.text = fullDate.format(cal.getTime())
            }
        }
        val timeSetListener = object : TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(
                view: TimePicker, hour: Int, minute: Int
            ) {
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                tanggal.text = fullDate.format(cal.getTime())
            }
        }
        btndate.setOnClickListener{
            DatePickerDialog(this@TambahAktivitas,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        btntime.setOnClickListener{
            TimePickerDialog(
                this@TambahAktivitas,
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
            saveFireStore(inputjudul.text.toString(),
                jamFormat.format(cal.getTime()),
                inputdeskripsi.text.toString(),
                tanggalFormat.format(cal.getTime()),
                false
            )
            val intent = Intent(this@TambahAktivitas, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser == null){
            val intent = Intent(this@TambahAktivitas, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.logout -> {
                Firebase.auth.signOut()
                finish()
                overridePendingTransition(0, 0)
                startActivity(getIntent())
                overridePendingTransition(0, 0)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun saveFireStore(judul: String,
                      jam: String,
                      deskripsi: String,
                      tanggal: String,
                      doneStatus: Boolean){

        val db = FirebaseFirestore.getInstance()
        val aktivitas: MutableMap<String, Any> = HashMap()
        aktivitas["judul"] = judul
        aktivitas["jam"] = jam
        aktivitas["deskripsi"] = deskripsi
        aktivitas["tanggal"] = tanggal
        aktivitas["doneStatus"] = doneStatus
        aktivitas["userID"] = auth.currentUser!!.uid

        db.collection("aktivitas")
            .add(aktivitas)
            .addOnCompleteListener {
                Toast.makeText(
                    baseContext, "Data Berhasil Ditambahkan",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener{
                Toast.makeText(
                    baseContext, "Data Gaagal Ditambahkan",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}