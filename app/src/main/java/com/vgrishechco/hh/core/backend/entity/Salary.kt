package com.vgrishechco.hh.core.backend.entity

import com.squareup.moshi.Json

data class Salary(@field:Json(name = "to") val to: Double?,
                  @field:Json(name = "from") val from: Double?,
                  @field:Json(name = "currency") val currency: String?)