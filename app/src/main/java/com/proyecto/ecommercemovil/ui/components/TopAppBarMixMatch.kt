package com.proyecto.ecommercemovil.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.TextUnit
import com.proyecto.ecommercemovil.ui.theme.KiwiFruit
import com.proyecto.ecommercemovil.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarMixMatch(
    onMenuClick: () -> Unit = {},
    logoFontSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Mix",
                    fontFamily = KiwiFruit,
                    fontWeight = FontWeight.Normal,
                    color = Red600,
                    fontSize = logoFontSize
                )
                Text(
                    text = "&Match",
                    fontFamily = KiwiFruit,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = logoFontSize
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Filled.Menu, contentDescription = "Menú")
            }
        },

    )
}