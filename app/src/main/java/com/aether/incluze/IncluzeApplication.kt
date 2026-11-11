package com.aether.incluze

import android.app.Application
import com.aether.incluze.data.local.AppDatabase
import com.aether.incluze.data.repository.ReportRepository

class IncluzeApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { ReportRepository(database.reportDao()) }
}
