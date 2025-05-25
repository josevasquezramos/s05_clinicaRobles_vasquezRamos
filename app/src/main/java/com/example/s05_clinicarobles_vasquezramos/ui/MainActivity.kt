package com.example.s05_clinicarobles_vasquezramos.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.s05_clinicarobles_vasquezramos.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnReserve = findViewById<Button>(R.id.btnReserve)
        btnReserve.setOnClickListener {
            val intent = Intent(this, SpecialtyListActivity::class.java)
            startActivity(intent)
        }
    }
}