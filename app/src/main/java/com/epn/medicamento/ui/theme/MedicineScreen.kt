package com.epn.medicamento.ui

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.epn.medicamento.data.local.MedicineEntity
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineScreen(viewModel: MedicineViewModel) {

    val nombre by viewModel.nombre.collectAsState()
    val dosis by viewModel.dosis.collectAsState()
    val medicinas by viewModel.medicinas.collectAsState(initial = emptyList())
    val context = LocalContext.current

    // Hora seleccionada por el usuario
    var hora by remember { mutableIntStateOf(8) }
    var minuto by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Medicinas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            OutlinedTextField(
                value = nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre del Medicamento") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = dosis,
                onValueChange = { viewModel.onDosisChange(it) },
                label = { Text("Dosis (ej. 1 tableta, 5ml)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // BOTÓN PARA SELECCIONAR LA HORA
            Button(
                onClick = {
                    val calendar = Calendar.getInstance()

                    TimePickerDialog(
                        context,
                        { _, h, m ->
                            hora = h
                            minuto = m
                        },
                        hora,
                        minuto,
                        true
                    ).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                val h = hora.toString().padStart(2, '0')
                val m = minuto.toString().padStart(2, '0')
                Text("Seleccionar hora: $h:$m")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // BOTÓN PARA GUARDAR Y PROGRAMAR
            Button(
                onClick = { viewModel.guardarMedicina(context, hora, minuto) },
                modifier = Modifier.fillMaxWidth()
            ) {
                val h = hora.toString().padStart(2, '0')
                val m = minuto.toString().padStart(2, '0')
                Text("Programar para las $h:$m")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Tus Recordatorios",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(medicinas) { med ->
                    MedicineItem(
                        med = med,
                        onDelete = { viewModel.eliminar(med) }
                    )
                }
            }
        }
    }
}

@Composable
fun MedicineItem(
    med: MedicineEntity,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = med.nombre,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Dosis: ${med.dosis}",
                    style = MaterialTheme.typography.bodyMedium
                )

                val h = med.hora.toString().padStart(2, '0')
                val m = med.minuto.toString().padStart(2, '0')

                Text(
                    text = "Hora: $h:$m",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
