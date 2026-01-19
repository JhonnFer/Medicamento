package com.epn.medicamento.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")
data class MedicineEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val dosis: String,
    val hora: Int,
    val minuto: Int
)