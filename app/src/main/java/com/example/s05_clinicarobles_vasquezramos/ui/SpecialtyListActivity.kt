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
            Specialty(1, "TRAUMATOLOGÍA", "Dr. Alberto García Cerna", "Lun-Mie-Vie 9:00–13:00", R.drawable.imagen1),
            Specialty(2, "CARDIOLOGÍA", "Dr. Roberto Chavesta Bernal", "Mar-Jue 8:00–12:00", R.drawable.imagen2),
            Specialty(3, "CIRUGÍA CARDIOVASCULAR", "Dr. Romel Zamudio Silva", "Lun-Mie 14:00–18:00", R.drawable.imagen3),
            Specialty(4, "CIRUGÍA GENERAL", "Dr. Beto Miranda Sevillano", "Mar-Jue 9:00–13:00", R.drawable.imagen4),
            Specialty(5, "DERMATOLOGÍA", "Dr. Jaime Vega Chávez", "Lun-Mie 10:00–14:00", R.drawable.imagen5),
            Specialty(6, "ENDOCRINOLOGÍA", "Dr. Juan Pinto Sánchez", "Mar-Vie 8:00–12:00", R.drawable.imagen6),
            Specialty(7, "GASTROENTEROLOGÍA", "Dra. Kelly Casanova Lau", "Lun-Mie-Vie 14:00–18:00", R.drawable.imagen7),
            Specialty(8, "GINECOLOGÍA", "Dr. José Luis Espinoza Decena", "Lun-Mie 8:00–12:00", R.drawable.imagen8),
            Specialty(9, "MEDICINA FÍSICA", "Dr. Luis Vásquez", "Mar-Jue 10:00–14:00", R.drawable.imagen9),
            Specialty(10, "MEDICINA GENERAL", "Dr. Elwis Laveriano Hoyos", "Lun-Vie 8:00–12:00", R.drawable.imagen10),
            Specialty(11, "MEDICINA INTERNA", "Dr. Luis Cabrera Cipirán", "Mar-Jue 14:00–18:00", R.drawable.imagen11),
            Specialty(12, "NEUMOLOGÍA", "Dra. Yessica Montoya Caldas", "Lun-Mie 9:00–13:00", R.drawable.imagen12),
            Specialty(13, "NEUROCIRUGÍA", "Dr. Willy Caballero Grados", "Mar-Vie 8:00–12:00", R.drawable.imagen13),
            Specialty(14, "NEUROLOGÍA", "Dr. Rosnel Valdivieso Velarde", "Lun-Mie 14:00–18:00", R.drawable.imagen14),
            Specialty(15, "NUTRICIÓN", "Lic. Carmela Carbajal", "Mar-Jue 10:00–14:00", R.drawable.imagen15),
            Specialty(16, "ODONTOLOGÍA", "Dr. Pedro Cipriano Alegre", "Lun-Mie 8:00–12:00", R.drawable.imagen16),
            Specialty(17, "CIRUGÍA MAXILOFACIAL", "Dr. Julio Robles Zanelli", "Mar-Jue 14:00–18:00", R.drawable.imagen17),
            Specialty(18, "OTORRINO", "Dr. Jorge Bonilla Vargas", "Lun-Vie 9:00–13:00", R.drawable.imagen18),
            Specialty(19, "OFTALMOLOGÍA", "Dra. Anita Vigo Arroyo", "Mar-Jue 8:00–12:00", R.drawable.imagen19),
            Specialty(20, "PEDIATRÍA", "Dr. Marcos Vásquez Tantas", "Lun-Mie-Vie 8:00–12:00", R.drawable.imagen20),
            Specialty(21, "PSICOLOGÍA", "Lic. Astrid Manrique Marrón", "Mar-Jue 14:00–18:00", R.drawable.imagen21),
            Specialty(22, "PSIQUIATRÍA", "Dra. Celmira Lázaro Loyola", "Lun-Mie 10:00–14:00", R.drawable.imagen22),
            Specialty(23, "REUMATOLOGÍA", "Dr. Frank Ocaña Vásquez", "Mar-Jue 9:00–13:00", R.drawable.imagen23),
            Specialty(24, "UROLOGÍA", "Dr. Carlos Morales Flores", "Lun-Mie-Vie 14:00–18:00", R.drawable.imagen24),
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