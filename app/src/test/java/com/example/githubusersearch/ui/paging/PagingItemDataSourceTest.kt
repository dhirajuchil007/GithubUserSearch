@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.githubusersearch.ui.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.example.githubusersearch.domain.GetFollowingUseCase
import com.example.githubusersearch.domain.Result
import com.example.githubusersearch.dummy.getDummyUsersListDomainModel
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class PagingItemDataSourceTest {

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    @MockK
    lateinit var getFollowingUseCase: GetFollowingUseCase

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }


    @Test
    fun `testPagingItemDataSource when data available`() = runTest {

        coEvery { getFollowingUseCase("userName", any()) } returns
                Result.Success(getDummyUsersListDomainModel())


        val pagingItemDataSource = PagingItemDataSource(
            apiCallFunction = { page -> getFollowingUseCase("userName", page) }
        )

        val pager = TestPager(PagingConfig(pageSize = 10), pagingItemDataSource)

        val result = pager.refresh() as PagingSource.LoadResult.Page

        assertEquals(10, result.data.size)
        assertEquals(null, result.prevKey)
        assertEquals(2, result.nextKey)
        assertThat(result).containsExactlyElementsIn(getDummyUsersListDomainModel())
    }

    @Test
    fun `testPagingItemDataSource when data empty`() = runTest {

        coEvery { getFollowingUseCase("userName", any()) } returns
                Result.Success(emptyList())


        val pagingItemDataSource = PagingItemDataSource(
            apiCallFunction = { page -> getFollowingUseCase("userName", page) }
        )

        val pager = TestPager(PagingConfig(pageSize = 10), pagingItemDataSource)

        val result = pager.refresh() as PagingSource.LoadResult.Page

        assertEquals(0, result.data.size)
        assertEquals(null, result.prevKey)
        assertEquals(null, result.nextKey)
        assertThat(result).isEmpty()
    }
}