package com.epn.medicamento.data.repository

import com.epn.medicamento.data.local.MedicineDAO
import com.epn.medicamento.data.local.MedicineEntity
import kotlinx.coroutines.flow.Flow

class MedicineRepository(private val medicineDao: MedicineDAO) {
    val todasLasMedicinas: Flow<List<MedicineEntity>> = medicineDao.obtenerTodas()

    suspend fun agregar(medicina: MedicineEntity): Long = medicineDao.insertar(medicina)
    suspend fun eliminar(medicina: MedicineEntity) = medicineDao.eliminar(medicina)
}