package com.aether.incluze.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aether.incluze.data.local.ReportEntity
import com.aether.incluze.data.repository.ReportRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReportViewModel(private val repository: ReportRepository) : ViewModel() {
    val reports = repository.allReports.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addReport(name: String, desc: String, category: String) {
        viewModelScope.launch {
            repository.insert(ReportEntity(
                locationName = name,
                description = desc,
                category = category
            ))
        }
    }

    fun toggleResolutionStatus(report: ReportEntity) {
        viewModelScope.launch {
            repository.update(report.copy(isResolved = !report.isResolved))
        }
    }
}

class ReportViewModelFactory(private val repository: ReportRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReportViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReportViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
