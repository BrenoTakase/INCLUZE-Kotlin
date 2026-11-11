package com.aether.incluze.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "reports")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val locationName: String,
    val description: String,
    val category: String, // e.g., Físico, Visual, Auditivo, Neurodivergente
    val isResolved: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
) {
    val formattedDate: String
        get() {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
            return sdf.format(Date(timestamp))
        }
}
