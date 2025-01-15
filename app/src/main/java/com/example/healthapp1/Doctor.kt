package com.example.healthapp1

data class Doctor(
    val name: String,
    val specialization: String,
    val availability: Map<String, List<String>>  // Date -> List of Time Slots
)