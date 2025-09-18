
package com.fvbib.lineup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fvbib.lineup.databinding.ActivityHomeBinding
import com.fvbib.lineup.ui.coaches.CoachesActivity
import com.fvbib.lineup.ui.referee.RefereeActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
  private lateinit var b: ActivityHomeBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    b = ActivityHomeBinding.inflate(layoutInflater)
    setContentView(b.root)

    b.btnCoaches.setOnClickListener { startActivity(Intent(this, CoachesActivity::class.java)) }
    b.btnReferees.setOnClickListener { startActivity(Intent(this, RefereeActivity::class.java)) }
    b.btnHelp.setOnClickListener {
      MaterialAlertDialogBuilder(this)
        .setTitle("Instrucciones")
        .setMessage("Entrenadores: genera QR con I..VI.\nÁrbitros: escanea A y B para ver rotación.")
        .setPositiveButton("OK", null).show()
    }
  }
}
