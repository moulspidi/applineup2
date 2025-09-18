
package com.fvbib.lineup.ui.referee

import android.os.Bundle
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fvbib.lineup.R
import com.fvbib.lineup.data.LineupPayload
import com.fvbib.lineup.databinding.ActivityArbitrosBinding
import com.fvbib.lineup.util.Json
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class RefereeActivity : AppCompatActivity() {
  private lateinit var b: ActivityArbitrosBinding
  private var selectedSet = 1

  private val scanLauncherA = registerForActivityResult(ScanContract()) { result ->
    if (result?.contents != null) handleScan(result.contents!!, true)
  }
  private val scanLauncherB = registerForActivityResult(ScanContract()) { result ->
    if (result?.contents != null) handleScan(result.contents!!, false)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    b = ActivityArbitrosBinding.inflate(layoutInflater)
    setContentView(b.root)

    b.toolbar.setNavigationOnClickListener { finish() }
    b.btnSet1.isChecked = true

    b.tgSets.addOnButtonCheckedListener { _, id, checked ->
      if (!checked) return@addOnButtonCheckedListener
      selectedSet = when (id) {
        R.id.btnSet1 -> 1; R.id.btnSet2 -> 2; R.id.btnSet3 -> 3; R.id.btnSet4 -> 4; else -> 5
      }
      clearGrids()
    }

    b.btnScanA.setOnClickListener { launchScan(true) }
    b.btnScanB.setOnClickListener { launchScan(false) }
  }

  private fun launchScan(isA: Boolean) {
    val opt = ScanOptions()
      .setOrientationLocked(true)
      .setBeepEnabled(false)
      .setPrompt(getString(R.string.scan_prompt))
    if (isA) scanLauncherA.launch(opt) else scanLauncherB.launch(opt)
  }

  private fun handleScan(raw: String, isA: Boolean) {
    val p = Json.fromJson<LineupPayload>(raw) ?: return
    if (isA) b.lblTeamA.text = "Equipo A: ${p.team}" else b.lblTeamB.text = "Equipo B: ${p.team}"
    val grid: GridLayout = if (isA) b.gridA else b.gridB
    val cells = listOf(p.i, p.ii, p.iii, p.vi, p.v, p.iv) // court order
    for (i in 0 until 6) {
      val tv = grid.getChildAt(i) as TextView
      tv.text = cells.getOrNull(i)?.takeIf { it != null && it > 0 }?.toString() ?: ""
    }
  }

  private fun clearGrids() {
    (0 until b.gridA.childCount).forEach { (b.gridA.getChildAt(it) as TextView).text = "" }
    (0 until b.gridB.childCount).forEach { (b.gridB.getChildAt(it) as TextView).text = "" }
    b.lblTeamA.text = "Equipo A"
    b.lblTeamB.text = "Equipo B"
  }
}
