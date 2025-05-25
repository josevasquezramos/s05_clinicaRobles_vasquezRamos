package com.example.s05_clinicarobles_vasquezramos.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.s05_clinicarobles_vasquezramos.R
import com.example.s05_clinicarobles_vasquezramos.model.Specialty

class SpecialtyListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specialty_list)

        // Configurar Toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Especialidades Médicas"

        // Configurar RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Crear lista de especialidades
        val specialties = listOf(
            Specialty(1, "Especialidad", "Nombre", "Horario", R.drawable.imagen1),
        )

        val adapter = SpecialtyAdapter(specialties) { specialty ->
            val intent = Intent(this, ConfirmationActivity::class.java).apply {
                putExtra("SPECIALTY_NAME", specialty.name)
                putExtra("DOCTOR_NAME", specialty.doctorName)
                putExtra("SCHEDULE", specialty.schedule)
                putExtra("IMAGE_RES_ID", specialty.doctorImageResId)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        val searchView = findViewById<SearchView>(R.id.searchView)

        // Quitar fondo de la placa (la barra donde se escribe)
        val plateId = resources.getIdentifier("search_plate", "id", "android")
        val plate = searchView.findViewById<View>(plateId)
        plate?.setBackgroundColor(Color.TRANSPARENT)
        plate?.background = null // A veces esto también es necesario

        // Quitar fondo del EditText interno
        val textId = resources.getIdentifier("search_src_text", "id", "android")
        val searchEditText = searchView.findViewById<View>(textId)
        searchEditText?.setBackgroundColor(Color.TRANSPARENT)
        searchEditText?.background = null
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val filtered = specialties.filter { specialty ->
                        specialty.name.contains(it, true) ||
                                specialty.doctorName.contains(it, true)
                    }
                    adapter.specialties = filtered
                    adapter.notifyDataSetChanged()
                }
                return true
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}