package com.example.gestaopacientes.features.home.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestaopacientes.R
import com.example.gestaopacientes.features.home.domain.Patient

class PatientsRecyclerView : RecyclerView.Adapter<PatientsRecyclerView.ViewHolder>() {
    private val items = mutableListOf<Patient>()

    fun updatelist(list: List<Patient>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name = view.findViewById<TextView>(R.id.txtName)
        val info = view.findViewById<TextView>(R.id.txtInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_patient, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.nome
        holder.info.text = "${item.idade} anos"
    }

}