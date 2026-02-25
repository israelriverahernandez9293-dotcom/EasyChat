package com.easychat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.easychat.ui.auth.AuthScreen
import com.easychat.ui.auth.AuthViewModel
import com.easychat.ui.home.HomeScreen
import com.easychat.ui.home.HomeViewModel
import com.easychat.ui.theme.EasyChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EasyChatTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
                    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.factory(application))
                    val authState by authViewModel.uiState.collectAsStateWithLifecycle()

                    if (authState.isAuthenticated) {
                        HomeScreen(
                            viewModel = homeViewModel,
                            onLogout = authViewModel::logout
                        )
                    } else {
                        AuthScreen(viewModel = authViewModel)
                    }
                }
            }
        }
    }
}
