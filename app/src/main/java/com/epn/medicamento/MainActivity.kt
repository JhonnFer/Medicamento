package com.epn.medicamento

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epn.medicamento.data.local.AppDatabase
import com.epn.medicamento.data.repository.MedicineRepository
import com.epn.medicamento.ui.MedicineScreen
import com.epn.medicamento.ui.MedicineViewModel
import com.epn.medicamento.ui.MedicineViewModelFactory // Necesitaremos crear esta pequeña clase
import com.epn.medicamento.ui.theme.MedReminderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Inicializar Base de Datos y Repositorio
        val database = AppDatabase.getDatabase(this)
        val repository = MedicineRepository(database.medicineDao())

        setContent {
            MedReminderTheme {
                // 2. Crear el ViewModel usando una Factory para pasar el repositorio
                val viewModel: MedicineViewModel = viewModel(
                    factory = MedicineViewModelFactory(repository)
                )

                // 3. Cargar la pantalla principal
                MedicineScreen(viewModel = viewModel)
            }
        }
    }
}