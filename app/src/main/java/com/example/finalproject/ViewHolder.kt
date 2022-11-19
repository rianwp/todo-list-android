package com.example.finalproject

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(R.layout.recycle_view, parent, false)) {
    private var judul: TextView? = null
    private var jam: TextView? = null
    private var deskripsi: TextView? = null
    private var tanggal: TextView? = null

    init{
        judul = itemView.findViewById(R.id.judul)
        jam = itemView.findViewById(R.id.jam)
        tanggal = itemView.findViewById(R.id.tanggal)
    }
    fun tampilData(data: DataRecycleView){
        judul?.text = data.judul
        jam?.text = data.jam
        tanggal?.text = data.tanggal
    }
    fun tampilDeskripsi(data: DataRecycleView){
        val intent = Intent(this.itemView.context, TampilDeskripsi::class.java)
        intent.putExtra("judul", data.judul)
        intent.putExtra("deskripsi", data.deskripsi)
        intent.putExtra("jam", data.jam)
        intent.putExtra("tanggal", data.tanggal)
        intent.putExtra("doneStatus", data.doneStatus)
        this.itemView.context.startActivity(intent)
    }
}