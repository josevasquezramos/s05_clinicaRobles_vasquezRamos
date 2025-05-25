package com.example.s05_clinicarobles_vasquezramos.model

data class Specialty(
    val id: Int,
    val name: String,
    val doctorName: String,
    val schedule: String,
    val doctorImageResId: Int
)