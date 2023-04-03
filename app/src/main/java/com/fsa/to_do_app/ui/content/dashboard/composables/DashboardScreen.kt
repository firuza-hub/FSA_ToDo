package com.fsa.to_do_app.ui.content.dashboard.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.ui.content.dashboard.DashboardViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel(),
    navigateToCreateAction: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(text = "Dashboard", style = MaterialTheme.typography.h2)
        Button(
            onClick = navigateToCreateAction,
            modifier = Modifier
                .align(End)
                .clip(shape = CircleShape)
        ) {
            Text(text = "Create", style = MaterialTheme.typography.button)
        }
    }

}