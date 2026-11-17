package com.aether.incluze.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aether.incluze.R
import com.aether.incluze.ui.theme.IncluzeBlue
import com.aether.incluze.ui.theme.IncluzeBlueDark
import com.aether.incluze.ui.theme.IncluzeLightBlue

@Composable
fun HomeScreen(onNavigateToList: () -> Unit, onNavigateToForm: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // Cabeçalho com a identidade visual (logo da mão em Libras sobre o azul INCLUZE)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp))
                .background(Brush.verticalGradient(listOf(IncluzeBlueDark, IncluzeBlue)))
                .padding(top = 40.dp, bottom = 36.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(112.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_incluze_mao),
                    contentDescription = "Logo INCLUZE: mão fazendo a letra I em Libras",
                    modifier = Modifier.height(80.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "INCLUZE",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Construindo cidades para todos.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.92f),
                textAlign = TextAlign.Center
            )
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(24.dp))

            // Contextualização: problema e público beneficiado
            InfoCard(
                icon = Icons.Default.ReportProblem,
                title = "O problema",
                text = "Calçadas, transportes e estabelecimentos ainda têm barreiras invisíveis " +
                    "que impedem pessoas de circular com autonomia."
            )
            Spacer(modifier = Modifier.height(12.dp))
            InfoCard(
                icon = Icons.Default.Groups,
                title = "Para quem",
                text = "Pessoas com deficiência física, visual ou auditiva, neurodivergentes, " +
                    "idosos e suas famílias. A própria comunidade mapeia e avalia os locais."
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onNavigateToForm,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Icon(Icons.Default.AddLocationAlt, contentDescription = null)
                Spacer(modifier = Modifier.width(12.dp))
                Text("Reportar barreira", style = MaterialTheme.typography.labelLarge)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onNavigateToList,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Map, contentDescription = null)
                Spacer(modifier = Modifier.width(12.dp))
                Text("Ver locais mapeados", style = MaterialTheme.typography.labelLarge)
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ODS relacionados
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OdsChip("ODS 10 · Igualdade")
                Spacer(modifier = Modifier.width(8.dp))
                OdsChip("ODS 11 · Cidades")
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun InfoCard(icon: ImageVector, title: String, text: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(IncluzeLightBlue),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = IncluzeBlueDark, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun OdsChip(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = IncluzeBlueDark,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(IncluzeLightBlue)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}
