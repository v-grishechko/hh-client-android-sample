package com.vgrishechco.hh.core.backend.entity

import com.squareup.moshi.Json
import java.util.*

data class Vacancy(@field:Json(name = "id") val id: Long,
                   @field:Json(name = "name") val name: String?,
                   @field:Json(name = "salary") val salary: Salary?,
                   @field:Json(name = "employer") val company: Company?,
                   @field:Json(name = "area") val area: Area?,
                   @field:Json(name = "description") val description: String?,
                   @field:Json(name = "published_at") val publishedAt: Date?)