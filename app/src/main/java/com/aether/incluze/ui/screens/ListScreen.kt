package com.aether.incluze.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.aether.incluze.data.local.ReportEntity
import com.aether.incluze.ui.report.ReportViewModel
import com.aether.incluze.ui.theme.IncluzeBlueDark
import com.aether.incluze.ui.theme.IncluzeGreen
import com.aether.incluze.ui.theme.IncluzeGreenLight
import com.aether.incluze.ui.theme.IncluzeLightBlue
import com.aether.incluze.ui.theme.IncluzeOrange
import com.aether.incluze.ui.theme.IncluzeOrangeLight
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(viewModel: ReportViewModel, onBack: () -> Unit, onNavigateToForm: () -> Unit) {
    val reports by viewModel.reports.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Locais mapeados", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = IncluzeBlueDark,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToForm,
                containerColor = IncluzeBlueDark,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Novo relato") }
            )
        }
    ) { padding ->
        if (reports.isEmpty()) {
            // Estado sem dados
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(IncluzeLightBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.TravelExplore,
                        contentDescription = null,
                        tint = IncluzeBlueDark,
                        modifier = Modifier.size(48.dp)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "Nenhum local mapeado ainda",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Ajude a comunidade: toque em \"Novo relato\" para registrar a primeira barreira.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            val pending = reports.count { !it.isResolved }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "${reports.size} relato(s) · $pending precisando de atenção",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                    )
                }
                items(reports, key = { it.id }) { report ->
                    ReportCard(
                        report = report,
                        onToggle = {
                            viewModel.toggleResolutionStatus(report)
                            val msg = if (report.isResolved) "Relato reaberto" else "Local marcado como acessível"
                            scope.launch {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar(msg)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ReportCard(report: ReportEntity, onToggle: () -> Unit) {
    val statusColor = if (report.isResolved) IncluzeGreen else IncluzeOrange
    val statusBg = if (report.isResolved) IncluzeGreenLight else IncluzeOrangeLight

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            // Faixa lateral com a cor do status
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .fillMaxHeight()
                    .background(statusColor)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = report.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = IncluzeBlueDark,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(IncluzeLightBlue)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                    Text(
                        text = report.formattedDate,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = IncluzeBlueDark,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = report.locationName,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = report.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(statusBg)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = if (report.isResolved) Icons.Default.CheckCircle else Icons.Default.Pending,
                            contentDescription = null,
                            tint = statusColor,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (report.isResolved) "Acessível" else "Atenção",
                            style = MaterialTheme.typography.labelMedium,
                            color = statusColor
                        )
                    }

                    FilledTonalButton(
                        onClick = onToggle,
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = IncluzeLightBlue,
                            contentColor = IncluzeBlueDark
                        )
                    ) {
                        Text(if (report.isResolved) "Reabrir" else "Marcar resolvido")
                    }
                }
            }
        }
    }
}
