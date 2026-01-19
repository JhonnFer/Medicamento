package com.epn.medicamento.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.epn.medicamento.data.local.MedicineEntity
import java.util.Calendar

object MedicineScheduler {
    fun programar(context: Context, medicina: MedicineEntity) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MedicineReceiver::class.java).apply {
            putExtra("MED_NAME", medicina.nombre)
            putExtra("MED_DOSIS", medicina.dosis)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context, medicina.id, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, medicina.hora)
            set(Calendar.MINUTE, medicina.minuto)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) add(Calendar.DATE, 1)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}