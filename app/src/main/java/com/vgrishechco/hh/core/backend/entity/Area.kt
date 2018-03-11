package com.vgrishechco.hh.core.backend.entity

import com.squareup.moshi.Json

data class Area(@field:Json(name = "id") val id: Long,
                @field:Json(name = "name") val name: String?)