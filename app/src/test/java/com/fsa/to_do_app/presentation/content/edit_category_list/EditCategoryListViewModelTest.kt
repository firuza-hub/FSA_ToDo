package com.fsa.to_do_app.presentation.content.edit_category_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.graphics.Color
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.fsa.to_do_app.domain.usecase.category.CreateCategoryUseCase
import com.fsa.to_do_app.domain.usecase.category.DeleteCategoryUseCase
import com.fsa.to_do_app.domain.usecase.category.GetCategoriesUseCase
import com.fsa.to_do_app.domain.usecase.category.UpdateCategoryUseCase
import com.fsa.to_do_app.presentation.content.edit_category_list.data.local.repo.FakeCategoryRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditCategoryListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: EditCategoryListViewModel

    @Before
    fun setup() {
        val repo = FakeCategoryRepositoryImpl()
        viewModel = EditCategoryListViewModel(
            GetCategoriesUseCase(repo),
            CreateCategoryUseCase(repo),
            DeleteCategoryUseCase(repo),
            UpdateCategoryUseCase(repo)
        )
    }

    @Test
    fun createNewCategory() = runTest{
        viewModel.updateCategoryName("Test category Create")
        viewModel.updateCategoryColor(Color.Cyan)
        viewModel.createNewCategory()
        viewModel.categories.test {
            val item = awaitItem()
            assert(item.count() > 1)
        }
    }

    fun updateCategoryName() {
    }

    fun updateCategoryColor() {
    }

    fun deleteCategory() {
    }
}