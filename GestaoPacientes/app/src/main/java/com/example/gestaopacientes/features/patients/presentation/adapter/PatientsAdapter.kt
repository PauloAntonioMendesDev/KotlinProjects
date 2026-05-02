package com.example.gestaopacientes.features.patients.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestaopacientes.R
import com.example.gestaopacientes.features.home.domain.Patient

class PatientsAdapter(
    private val onClick: (Patient) -> Unit
) : RecyclerView.Adapter<PatientsAdapter.PatientViewHolder>(){
    private val items = mutableListOf<Patient>()

    fun updateList(list: List<Patient>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class PatientViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val txtName = view.findViewById<TextView>(R.id.txtName)
        private val txtInfo = view.findViewById<TextView>(R.id.txtInfo)

        fun bind(patient: Patient){
            txtName.text = patient.nome
            txtInfo.text = "${patient.idade} anos"
            itemView.setOnClickListener { onClick(patient) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_patient, parent, false)
        return PatientViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        holder.bind(items[position])
    }
}