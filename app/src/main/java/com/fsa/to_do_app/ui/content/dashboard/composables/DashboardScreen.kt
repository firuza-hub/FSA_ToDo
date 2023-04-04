package com.fsa.to_do_app.ui.content.dashboard.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.data.local.entities.Action
import com.fsa.to_do_app.ui.common.bottomBorder
import com.fsa.to_do_app.ui.content.dashboard.DashboardViewModel
import com.fsa.to_do_app.ui.theme.SFPro
import com.fsa.to_do_app.util.getTimeShort
import org.koin.androidx.compose.koinViewModel
import java.util.*

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

            Actions(actions)
        }



        Button(
            onClick = navigateToCreateAction,
            modifier = Modifier
                .align(BottomEnd)
                .clip(shape = CircleShape)
        ) {
            Text(text = "Create", style = MaterialTheme.typography.button)
        }
    }

}

@Composable
fun Actions(actions: List<Action>) {
    LazyColumn {
        items(actions) {
            Row(Modifier.fillMaxWidth()) {
                RadioButton(
                    selected = false,
                    onClick = { },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .width(28.dp)
                        .align(CenterVertically),
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = Color.LightGray,
                        selectedColor = Color.Black
                    )
                )
                Row(modifier = Modifier.bottomBorder(1.dp, Color.LightGray)) {
                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .defaultMinSize(minHeight = 80.dp)
                            .weight(1f)
                            .padding(vertical = 16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = it.content, overflow = TextOverflow.Ellipsis, maxLines = 2,
                            style = MaterialTheme.typography.body1
                        )
                        ActionTime(date = it.date, modifier = Modifier.padding(top = 4.dp))
                    }
                    CategoryIndicator(
                        Modifier
                            .align(CenterVertically)
                            .wrapContentWidth(unbounded = true)
                            .padding(horizontal = 16.dp)
                    )
                }

            }
        }
    }
}

@Composable
fun ActionTime(date: Date, modifier: Modifier) {
    Row(verticalAlignment = CenterVertically, modifier = modifier) {
        Icon(
            modifier = Modifier
                .height(16.dp)
                .padding(end = 4.dp),
            tint = Color.Gray,
            painter = painterResource(id = R.drawable.ic_alarm),
            contentDescription = "Alarm icon"
        )
        Text(text = date.getTimeShort(), style = MaterialTheme.typography.body2)
    }
}

@Composable
fun CategoryIndicator(modifier: Modifier) {
    CircleShapeDemo(modifier)
}

@Composable
fun CircleShapeDemo(modifier: Modifier) {
    ExampleBox(shape = CircleShape, modifier)
}

@Composable
fun ExampleBox(shape: Shape, modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(shape)
                .background(Color.Red)
        )
    }
}

   
