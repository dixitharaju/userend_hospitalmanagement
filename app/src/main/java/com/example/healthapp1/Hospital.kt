package com.example.healthapp1

data class Hospital(
    val name: String,
    val imageResource: Int,
    val distance: Float, // Distance in kilometers
    val doctors: List<Doctor>
)


