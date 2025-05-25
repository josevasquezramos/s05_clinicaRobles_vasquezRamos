package com.example.s05_clinicarobles_vasquezramos.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.s05_clinicarobles_vasquezramos.R
import androidx.activity.result.contract.ActivityResultContracts

class ConfirmationActivity : AppCompatActivity() {

    private val CHANNEL_ID = "confirmation_channel"
    private val NOTIFICATION_ID = 1

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            showNotification()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Cita Reservada"

        val specialtyName = intent.getStringExtra("SPECIALTY_NAME") ?: ""
        val doctorName = intent.getStringExtra("DOCTOR_NAME") ?: ""
        val schedule = intent.getStringExtra("SCHEDULE") ?: ""
        val imageResId = intent.getIntExtra("IMAGE_RES_ID", 0)

        findViewById<TextView>(R.id.tvSpecialty).text = specialtyName
        findViewById<TextView>(R.id.tvDoctor).text = "Dr. $doctorName"
        findViewById<TextView>(R.id.tvSchedule).text = "Horario: $schedule"
        findViewById<ImageView>(R.id.ivSpecialty).setImageResource(imageResId)

        findViewById<Button>(R.id.btnFinish).setOnClickListener {
            finishAffinity()
        }

        createNotificationChannel()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                showNotification()
            } else {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            showNotification()
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Confirmación"
            val descriptionText = "Canal para confirmar citas"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification() {
        // Verificar permiso antes de mostrar la notificación
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED) {
                // Permiso no concedido, salir sin mostrar la notificación
                return
            }
        }

        val intent = Intent(this, ConfirmationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_calendar_month_24)
            .setContentTitle("Cita Reservada")
            .setContentText("Tu cita ha sido reservada con éxito.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }
}
