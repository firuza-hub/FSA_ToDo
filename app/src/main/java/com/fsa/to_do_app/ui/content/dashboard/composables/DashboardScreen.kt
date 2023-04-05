package com.fsa.to_do_app.ui.content.dashboard.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fsa.to_do_app.ui.content.dashboard.DashboardViewModel
import com.fsa.to_do_app.ui.theme.SFPro
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel(),
    navigateToCreateAction: () -> Unit
) {
    val actions by viewModel.actions.collectAsState()
    val categories by viewModel.categories.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Today",
                style = MaterialTheme.typography.h1,
                fontFamily = SFPro,
                modifier = Modifier.padding(start = 44.dp, top = 16.dp)
            )

            Actions(actions, Modifier.weight(1f), onActionChecked =viewModel::onActionChecked)
            Categories(
                categories,
                Modifier
                    .weight(1f)
                    .padding(end = 16.dp, start = 40.dp)
            )
        }


        FloatingActionButton(
            onClick = navigateToCreateAction,
            backgroundColor = Color.White,
            modifier = Modifier
                .align(BottomEnd)
                .padding(bottom = 30.dp, end = 16.dp)
        ) {
            Text(text = "+", fontSize = 30.sp, color = Color.Blue)
        }
    }

}

