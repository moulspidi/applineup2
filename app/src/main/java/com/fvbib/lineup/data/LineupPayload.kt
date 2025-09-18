
package com.fvbib.lineup.data

data class LineupPayload(
  val team: String,
  val set: Int,
  val side: String,
  val i: Int?, val ii: Int?, val iii: Int?,
  val iv: Int?, val v: Int?, val vi: Int?
)
