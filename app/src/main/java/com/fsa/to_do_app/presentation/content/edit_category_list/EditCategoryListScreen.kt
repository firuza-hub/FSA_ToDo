package com.fsa.to_do_app.presentation.content.edit_category_list

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.presentation.common.noRippleClickable
import com.fsa.to_do_app.presentation.content.create_task.composables.TaskToolBar
import com.fsa.to_do_app.presentation.content.dashboard.composables.Categories
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditCategoryListScreen(
    navigateBack: () -> Boolean,
    viewModel: EditCategoryListViewModel = koinViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    val context = LocalContext.current
    Column(Modifier.fillMaxSize()) {
        TaskToolBar(cancel = navigateBack, save = {}, onAreaClicked = {})
        Categories(
            categories = categories,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onCategorySelected = {})


        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .background(Color.Gray.copy(0.1f), RoundedCornerShape(10.dp))
                .noRippleClickable { }//Input box for new category
                .fillMaxWidth()
                .border(BorderStroke(1.dp, Color.Black.copy(0.1f)), RoundedCornerShape(10.dp))
                .height(70.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = "Create Category"
            )
        }
    }
}