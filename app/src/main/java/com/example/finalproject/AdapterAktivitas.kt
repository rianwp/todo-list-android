package com.example.finalproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date

class AdapterAktivitas(private val data: ArrayList<DataRecycleView>): RecyclerView.Adapter<ViewHolder>() {
    val simpleDate = SimpleDateFormat("dd/MM/yyyy HH:mm")
    var currentDate = simpleDate.format(Date())
    fun filterData(): ArrayList<DataRecycleView>{
        val filteredData = ArrayList<DataRecycleView>()
        for(i in data){
            var dataDate = "${i.tanggal} ${i.jam}"
            if(!(i.doneStatus) && simpleDate.parse(currentDate)!! < simpleDate.parse(dataDate)){
                filteredData.add(DataRecycleView(i.judul, i.jam, i.deskripsi, i.tanggal, i.doneStatus, i.id))
            }
        }
        return filteredData
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tampilData(filterData()[position])
        holder.itemView.setOnClickListener{
            holder.tampilDeskripsi(filterData()[position])
        }
    }
    override fun getItemCount(): Int {
        var dataCount = 0
        for(i in data){
            var dataDate = "${i.tanggal} ${i.jam}"
            if(!(i.doneStatus) && simpleDate.parse(currentDate)!! < simpleDate.parse(dataDate)){
                dataCount += 1
            }
        }
        return dataCount
    }
}