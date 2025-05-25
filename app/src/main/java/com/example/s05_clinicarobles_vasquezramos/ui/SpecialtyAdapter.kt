package com.example.s05_clinicarobles_vasquezramos.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.s05_clinicarobles_vasquezramos.R
import com.example.s05_clinicarobles_vasquezramos.model.Specialty

class SpecialtyAdapter(
    var specialties: List<Specialty>,
    private val onReserveClick: (Specialty) -> Unit
) : RecyclerView.Adapter<SpecialtyAdapter.SpecialtyViewHolder>() {

    inner class SpecialtyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val specialtyName: TextView = itemView.findViewById(R.id.specialtyName)
        private val doctorName: TextView = itemView.findViewById(R.id.doctorName)
        private val schedule: TextView = itemView.findViewById(R.id.schedule)
        private val specialtyImage: ImageView = itemView.findViewById(R.id.doctorImage)
        private val btnReserve: Button = itemView.findViewById(R.id.btnReserve)

        fun bind(specialty: Specialty) {
            specialtyName.text = specialty.name
            doctorName.text = specialty.doctorName
            schedule.text = "Horario: ${specialty.schedule}"
            specialtyImage.setImageResource(specialty.doctorImageResId)

            btnReserve.setOnClickListener {
                onReserveClick(specialty)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_specialty, parent, false)
        return SpecialtyViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpecialtyViewHolder, position: Int) {
        holder.bind(specialties[position])
    }

    override fun getItemCount(): Int = specialties.size
}