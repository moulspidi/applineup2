
package com.fvbib.lineup.util

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

object Json {
  private val gson = Gson()
  inline fun <reified T> fromJson(raw: String): T? =
    try { gson.fromJson(raw, T::class.java) } catch (_: JsonSyntaxException) { null }
  fun toJson(any: Any): String = gson.toJson(any)
}
