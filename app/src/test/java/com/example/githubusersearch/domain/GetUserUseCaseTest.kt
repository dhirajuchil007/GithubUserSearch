package com.example.githubusersearch.domain

import com.example.githubusersearch.dummy.getDummyUserDomainModel
import com.example.githubusersearch.dummy.getDummyUserNetworkModel
import com.example.githubusersearch.repo.UserRepo
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetUserUseCaseTest {

    @MockK
    lateinit var userRepository: UserRepo

    lateinit var sut: GetUserUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        sut = GetUserUseCase(userRepository)
    }

    @Test
    fun `usecase returns user when successful`(): Unit = runBlocking {

        coEvery { userRepository.getUser("username") } returns Result.Success(
            getDummyUserNetworkModel()
        )

        val result = sut("username")

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data).isEqualTo(getDummyUserDomainModel())
    }

    @Test
    fun `usecase returns error when failed`(): Unit = runBlocking {
        coEvery { userRepository.getUser("username") } returns Result.Error(NetworkError.UNKNOWN)

        val result = sut("username")

        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).error).isEqualTo(NetworkError.UNKNOWN)
    }
}