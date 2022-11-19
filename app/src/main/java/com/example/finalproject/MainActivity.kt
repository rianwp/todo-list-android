package com.example.finalproject

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    val data = ArrayList<DataRecycleView>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        getSupportActionBar()!!.hide()
        val fragmentAktivitas = FragmentAktivitas()
        val fragmentSelesai = FragmentSelesai()
        val fragmentTerlewat = FragmentTerlewat()
        val bundle = Bundle()
        bundle.putSerializable("data", data)
        fragmentAktivitas.arguments = bundle
        fragmentSelesai.arguments = bundle
        fragmentTerlewat.arguments = bundle
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.frame_layout, fragmentAktivitas)
            commit()
        }

        val btnaktivitas = findViewById<TextView>(R.id.btn_aktivitas)
        val btnselesai = findViewById<TextView>(R.id.btn_selesai)
        val btnterlewat = findViewById<TextView>(R.id.btn_terlewat)

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
    private fun init(){
        data.add(DataRecycleView("Membantu Ibu", "13:20", "Jam 10 bantu ibu bersih2 rumah", "05/07/2023", false))
        data.add(DataRecycleView("Membantu g", "13:20", "Jam 10 bantu ibu bersih2 rumah", "05/07/2003", true))
        data.add(DataRecycleView("Membantu ayah", "13:20", "Jam 10 bantu ibu bersih2 rumah", "05/07/2003", false))
        data.add(DataRecycleView("Membantu b", "13:20", "Jam 10 bantu ibu bersih2 rumah", "05/07/2003", false))
    }

    fun setToMainActivity2(view: View) {
        startActivity(Intent(this, MainActivity2::class.java));
    }
}