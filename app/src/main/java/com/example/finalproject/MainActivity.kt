package com.example.finalproject

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth

class MainActivity : AppCompatActivity() {
    var backPressedTime: Long = 0

    private val fragmentAktivitas = FragmentAktivitas()
    private val fragmentSelesai = FragmentSelesai()
    private val fragmentTerlewat = FragmentTerlewat()
    private lateinit var progressBar: ProgressBar
    private val bundle = Bundle()

    val data = ArrayList<DataRecycleView>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = getSupportActionBar()
        actionBar!!.setTitle("Daftar Aktivitas")

        auth = Firebase.auth
        progressBar = findViewById(R.id.progressbar)
        fetchData()

        fragmentAktivitas.arguments = bundle
        fragmentSelesai.arguments = bundle
        fragmentTerlewat.arguments = bundle

        val btnaktivitas = findViewById<TextView>(R.id.btn_aktivitas)
        val btnselesai = findViewById<TextView>(R.id.btn_selesai)
        val btnterlewat = findViewById<TextView>(R.id.btn_terlewat)
        val btntambahaktivitas = findViewById<FloatingActionButton>(R.id.btn_addactivity)

        btntambahaktivitas.setOnClickListener {
            startActivity(Intent(this, TambahAktivitas::class.java))
        }

        btnaktivitas.setTextColor(Color.parseColor("#2196f3"))

        btnaktivitas.setOnClickListener {
            supportFragmentManager.beginTransaction().apply{
                replace(R.id.frame_layout, fragmentAktivitas)
                commit()
            }
            btnaktivitas.setTextColor(Color.parseColor("#2196f3"))
            btnselesai.setTextColor(Color.parseColor("#808080"))
            btnterlewat.setTextColor(Color.parseColor("#808080"))
        }
        btnselesai.setOnClickListener {
            supportFragmentManager.beginTransaction().apply{
                replace(R.id.frame_layout, fragmentSelesai)
                commit()
            }
            btnaktivitas.setTextColor(Color.parseColor("#808080"))
            btnselesai.setTextColor(Color.parseColor("#2196f3"))
            btnterlewat.setTextColor(Color.parseColor("#808080"))
        }
        btnterlewat.setOnClickListener {
            supportFragmentManager.beginTransaction().apply{
                replace(R.id.frame_layout, fragmentTerlewat)
                commit()
            }
            btnaktivitas.setTextColor(Color.parseColor("#808080"))
            btnselesai.setTextColor(Color.parseColor("#808080"))
            btnterlewat.setTextColor(Color.parseColor("#2196f3"))
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser == null){
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
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
    private fun fetchData() {
        progressBar.visibility = View.VISIBLE
        //logic seleksi data per user untuk di tampilkan ke recyclerview disini
        val db = FirebaseFirestore.getInstance()
        db.collection("aktivitas").whereEqualTo("userID", auth.currentUser!!.uid)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        data.add(
                            DataRecycleView(
                                document.data.getValue("judul").toString(),
                                document.data.getValue("jam").toString(),
                                document.data.getValue("deskripsi").toString(),
                                document.data.getValue("tanggal").toString(),
                                document.data.getValue("doneStatus").toString().toBooleanStrict(),
                                document.id
                            )
                        )
                    }
                    bundle.putSerializable("data", data)
                    supportFragmentManager.beginTransaction().apply{
                        replace(R.id.frame_layout, fragmentAktivitas)
                        commit()
                    }
                    progressBar.visibility = View.GONE
                }
            }
            .addOnFailureListener{
                progressBar.visibility = View.GONE
                Toast.makeText(
                    baseContext, "Terjadi Kesalahan Saat Menampilkan Data",
                    Toast.LENGTH_SHORT
                )
            }
    }
}