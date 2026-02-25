package com.easychat.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easychat.ui.theme.EasyChatTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onLogout: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    HomeContent(
        state = state,
        onStartChat = viewModel::startChat,
        onStartVideoCall = viewModel::startVideoCall,
        onLogout = onLogout
    )
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    onStartChat: () -> Unit,
    onStartVideoCall: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF0F172A), Color(0xFF1E3A8A), Color(0xFF22D3EE))
                )
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.92f)),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Inicio", style = MaterialTheme.typography.headlineSmall)
                    IconButton(onClick = onLogout) {
                        Icon(imageVector = Icons.Default.Logout, contentDescription = "Cerrar sesión")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(state.statusMessage, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(10.dp))
                Text("Chats iniciados: ${state.chatCount}")
                Text("Videollamadas iniciadas: ${state.videoCallCount}")
                Text(state.lastAction, style = MaterialTheme.typography.bodyMedium)
            }
        }

        Column {
            Text(
                text = "Menú flotante rápido",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ExtendedFloatingActionButton(
                    onClick = onStartChat,
                    icon = { Icon(Icons.Default.Chat, contentDescription = "Iniciar chat") },
                    text = { Text("Iniciar chat") },
                    modifier = Modifier.weight(1f)
                )
                ExtendedFloatingActionButton(
                    onClick = onStartVideoCall,
                    icon = { Icon(Icons.Default.VideoCall, contentDescription = "Iniciar videollamada") },
                    text = { Text("Videollamada") },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeContentPreview() {
    EasyChatTheme {
        HomeContent(
            state = HomeUiState(chatCount = 3, videoCallCount = 1, lastAction = "Última acción: iniciar chat"),
            onStartChat = {},
            onStartVideoCall = {},
            onLogout = {}
        )
    }
}
