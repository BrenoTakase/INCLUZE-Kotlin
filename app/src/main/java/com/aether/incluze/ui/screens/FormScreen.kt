package com.aether.incluze.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.aether.incluze.ui.report.ReportViewModel
import com.aether.incluze.ui.theme.IncluzeBlueDark
import com.aether.incluze.ui.theme.IncluzeLightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(viewModel: ReportViewModel, onBack: () -> Unit) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    // Lista de categorias predefinidas
    val categories = listOf("Motora / Física", "Visual", "Auditiva", "Neurodivergente", "Idoso", "Outros")
    var expanded by remember { mutableStateOf(false) }

    // Só mostra os erros depois da primeira tentativa de envio
    var submitted by remember { mutableStateOf(false) }
    val nameError = submitted && name.isBlank()
    val categoryError = submitted && category.isBlank()
    val descriptionError = submitted && description.trim().length < 10

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = IncluzeBlueDark,
        focusedLabelColor = IncluzeBlueDark,
        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        errorContainerColor = Color.White
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Reportar barreira", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = IncluzeBlueDark,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            // Dica no topo do formulário
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(IncluzeLightBlue)
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Info, contentDescription = null, tint = IncluzeBlueDark)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Seu relato ajuda outras pessoas a saberem o que vão encontrar no local.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = IncluzeBlueDark
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Detalhes do local",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome ou endereço do local *") },
                placeholder = { Text("Ex: Estação Luz, Metrô SP") },
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = IncluzeBlueDark) },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                isError = nameError,
                supportingText = { if (nameError) Text("Informe o local") },
                singleLine = true,
                colors = fieldColors
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Dropdown para categoria
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de acessibilidade afetada *") },
                    leadingIcon = { Icon(Icons.Default.Category, contentDescription = null, tint = IncluzeBlueDark) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = MaterialTheme.shapes.small,
                    isError = categoryError,
                    supportingText = { if (categoryError) Text("Escolha uma categoria") },
                    colors = fieldColors
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                ) {
                    categories.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                category = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descreva a barreira *") },
                placeholder = { Text("Ex: O elevador da plataforma 2 está em manutenção, bloqueando cadeirantes.") },
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = null, tint = IncluzeBlueDark) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 140.dp),
                shape = MaterialTheme.shapes.small,
                isError = descriptionError,
                supportingText = {
                    if (descriptionError) Text("Descreva com pelo menos 10 caracteres")
                    else Text("Seja específico: o que, onde e quem é afetado")
                },
                maxLines = 6,
                colors = fieldColors
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    submitted = true
                    val valid = name.isNotBlank() && category.isNotBlank() && description.trim().length >= 10
                    if (valid) {
                        viewModel.addReport(name.trim(), description.trim(), category)
                        Toast.makeText(context, "Relato salvo! Obrigado por colaborar.", Toast.LENGTH_SHORT).show()
                        onBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Send, contentDescription = null)
                Spacer(modifier = Modifier.width(12.dp))
                Text("Publicar relato", style = MaterialTheme.typography.labelLarge)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "* Campos obrigatórios",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
