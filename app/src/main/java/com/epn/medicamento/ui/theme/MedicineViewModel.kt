package com.epn.medicamento.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epn.medicamento.alarm.MedicineScheduler
import com.epn.medicamento.data.local.MedicineEntity
import com.epn.medicamento.data.repository.MedicineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MedicineViewModel(private val repository: MedicineRepository) : ViewModel() {
    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre

    private val _dosis = MutableStateFlow("")
    val dosis: StateFlow<String> = _dosis

    val medicinas = repository.todasLasMedicinas

    fun onNombreChange(v: String) { _nombre.value = v }
    fun onDosisChange(v: String) { _dosis.value = v }

    fun guardarMedicina(context: Context, hora: Int, minuto: Int) {
        if (_nombre.value.isBlank()) return
        viewModelScope.launch {
            val med = MedicineEntity(
                nombre = _nombre.value,
                dosis = _dosis.value,
                hora = hora,
                minuto = minuto
            )
            val id = repository.agregar(med)
            // Programar alarma con el ID generado por Room
            MedicineScheduler.programar(context, med.copy(id = id.toInt()))

            _nombre.value = ""
            _dosis.value = ""
        }
    }

    fun eliminar(med: MedicineEntity) {
        viewModelScope.launch { repository.eliminar(med) }
    }
}