package com.aether.incluze.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aether.incluze.ui.report.ReportViewModel
import com.aether.incluze.ui.theme.IncluzeBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(viewModel: ReportViewModel, onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    
    // Lista de categorias predefinidas
    val categories = listOf("Motora / Física", "Visual", "Auditiva", "Neurodivergente", "Idoso", "Outros")
    var expanded by remember { mutableStateOf(false) }
    
    var error by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mapear Barreira", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
        ) {
            
            Text(
                text = "Detalhes do Local",
                style = MaterialTheme.typography.titleMedium,
                color = IncluzeBlue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp, top = 8.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it; error = false },
                label = { Text("Nome ou Endereço do Local") },
                placeholder = { Text("Ex: Estação Luz, Metrô SP") },
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = IncluzeBlue) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                isError = error && name.isBlank(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = IncluzeBlue,
                    focusedLabelColor = IncluzeBlue
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
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
                    label = { Text("Tipo de Acessibilidade Afetada") },
                    leadingIcon = { Icon(Icons.Default.Category, contentDescription = null, tint = IncluzeBlue) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    isError = error && category.isBlank(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = IncluzeBlue,
                        focusedLabelColor = IncluzeBlue
                    )
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
                                error = false
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = description,
                onValueChange = { description = it; error = false },
                label = { Text("Descrição da Barreira (Seja específico)") },
                placeholder = { Text("Ex: O elevador principal da plataforma 2 está em manutenção, bloqueando cadeirantes.") },
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = null, tint = IncluzeBlue) },
                modifier = Modifier.fillMaxWidth().height(140.dp),
                shape = RoundedCornerShape(12.dp),
                isError = error && description.isBlank(),
                maxLines = 5,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = IncluzeBlue,
                    focusedLabelColor = IncluzeBlue
                )
            )
            
            if (error) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "* Preencha todos os campos para enviar o alerta.", 
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = {
                    if (name.isNotBlank() && description.isNotBlank() && category.isNotBlank()) {
                        viewModel.addReport(name, description, category)
                        onBack()
                    } else {
                        error = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = IncluzeBlue)
            ) {
                Text("Publicar Alerta no Mapa", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
