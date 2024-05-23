@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.githubusersearch.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingConfig
import com.example.githubusersearch.domain.GetFollowersUseCase
import com.example.githubusersearch.domain.GetFollowingUseCase
import com.example.githubusersearch.domain.GetUserUseCase
import com.example.githubusersearch.domain.Result
import com.example.githubusersearch.dummy.getDummyUserDomainModel
import com.example.githubusersearch.ui.paging.UserPager
import com.example.githubusersearch.ui.states.UserDetailsState
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class UserDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    @MockK
    lateinit var getUserUseCase: GetUserUseCase

    @MockK
    lateinit var getFollowersUseCase: GetFollowersUseCase

    @MockK
    lateinit var getFollowingUseCase: GetFollowingUseCase

    lateinit var userPager: UserPager


    lateinit var sut: UserDetailsViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        userPager = UserPager(getFollowersUseCase, getFollowingUseCase, PagingConfig(10))
        sut = UserDetailsViewModel(getUserUseCase, userPager)
    }

    @Test
    fun `getUser empty or null username returns correct error`() {
        val username = ""
        sut.getUser(username)
        assertThat(UserDetailsState.Error("Invalid username $username")).isEqualTo(sut.state.value)
    }

    @Test
    fun `getUser returns data on success`() = runTest {
        coEvery { getUserUseCase("username") } returns Result.Success(getDummyUserDomainModel())

        sut.getUser("username")

        assertThat(UserDetailsState.Loading).isEqualTo(sut.state.value)

        advanceUntilIdle()

        assertThat(UserDetailsState.ShowUser(getDummyUserDomainModel())).isEqualTo(sut.state.value)

    }
}