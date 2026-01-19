package com.epn.medicamento.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.epn.medicamento.data.repository.MedicineRepository

class MedicineViewModelFactory(private val repository: MedicineRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MedicineViewModel::class.java)) {
            return MedicineViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}