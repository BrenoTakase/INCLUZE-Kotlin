package com.aether.incluze.data.repository

import com.aether.incluze.data.local.ReportDao
import com.aether.incluze.data.local.ReportEntity
import kotlinx.coroutines.flow.Flow

class ReportRepository(private val reportDao: ReportDao) {
    val allReports: Flow<List<ReportEntity>> = reportDao.getAllReports()

    suspend fun insert(report: ReportEntity) {
        reportDao.insertReport(report)
    }

    suspend fun update(report: ReportEntity) {
        reportDao.updateReport(report)
    }
}
