package com.vgrishechco.hh.core.backend.entity

import com.squareup.moshi.Json

data class Vacancies(@field:Json(name = "items") val vacancies: List<Vacancy>,
                     @field:Json(name = "pages") val pages: Int,
                     @field:Json(name = "found") val found: Int?,
                     @field:Json(name = "per_page") val perPage: Int?,
                     @field:Json(name = "page") val page: Int)