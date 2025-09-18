
package com.fvbib.lineup.ui.coaches

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fvbib.lineup.R
import com.fvbib.lineup.data.LineupPayload
import com.fvbib.lineup.databinding.ActivityEntrenadoresBinding
import com.fvbib.lineup.util.Json
import com.google.android.material.button.MaterialButton
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class CoachesActivity : AppCompatActivity() {
  private lateinit var b: ActivityEntrenadoresBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    b = ActivityEntrenadoresBinding.inflate(layoutInflater)
    setContentView(b.root)

    b.toolbar.setNavigationOnClickListener { finish() }
    (findViewById<MaterialButton>(R.id.btnA)).isChecked = true

    b.btnGenerate.setOnClickListener {
      val team = b.etTeam.text?.toString()?.trim().orEmpty()
      val set = b.etSet.text?.toString()?.toIntOrNull() ?: 1
      val side = if (b.btnA.isChecked) "A" else "B"

      val payload = LineupPayload(
        team = team, set = set, side = side,
        i = b.etI.text?.toString()?.toIntOrNull(),
        ii = b.etII.text?.toString()?.toIntOrNull(),
        iii = b.etIII.text?.toString()?.toIntOrNull(),
        iv = b.etIV.text?.toString()?.toIntOrNull(),
        v = b.etV.text?.toString()?.toIntOrNull(),
        vi = b.etVI.text?.toString()?.toIntOrNull()
      )
      val json = Json.toJson(payload)
      val encoder = BarcodeEncoder()
      val bmp: Bitmap = encoder.encodeBitmap(json, BarcodeFormat.QR_CODE, 800, 800)
      b.imgQr.setImageBitmap(bmp)
    }
  }
}
