package com.epn.medicamento.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDAO {
    @Query("SELECT * FROM medicines ORDER BY hora ASC, minuto ASC")
    fun obtenerTodas(): Flow<List<MedicineEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(medicina: MedicineEntity): Long

    @Delete
    suspend fun eliminar(medicina: MedicineEntity)

    @Update
    suspend fun actualizar(medicina: MedicineEntity)
}