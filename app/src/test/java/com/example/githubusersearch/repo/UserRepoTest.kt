package com.example.githubusersearch.repo

import com.example.githubusersearch.domain.NetworkError
import com.example.githubusersearch.domain.Result
import com.example.githubusersearch.dummy.getDummyUserNetworkModel
import com.example.githubusersearch.dummy.getDummyUserNetworkModelResponse
import com.example.githubusersearch.network.api.ApiService
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response
import java.net.UnknownHostException

@ExtendWith(MockKExtension::class)
class UserRepoTest {

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        sut = UserRepo(apiService)
    }

    var apiService: ApiService = mockk()

    lateinit var sut: UserRepo


    @Test
    fun `getUser on success return UserNetworkModel`(): Unit = runBlocking<Unit> {
        //before
        coEvery { apiService.getUser("username") } returns getDummyUserNetworkModelResponse(
            getDummyUserNetworkModel()
        )

        //when
        val result = sut.getUser("username")

        //then
        assertThat((result as Result.Success).data).isEqualTo(getDummyUserNetworkModel())

    }

    @Test
    fun `getUser on network failure  return check connection`(): Unit = runBlocking<Unit> {
        //before
        coEvery { apiService.getUser("username") } throws UnknownHostException()
        //when
        val result = sut.getUser("username")


        //then
        assertThat(result).isInstanceOf(Result.Error::class.java)
        val error = (result as Result.Error).error
        assertThat(error).isEqualTo(NetworkError.CHECK_CONNECTION)
    }

    @Test
    fun `getUser on unknown failure  return unknown error`(): Unit = runBlocking<Unit> {
        //before
        coEvery { apiService.getUser("username") } throws Exception()
        //when
        val result = sut.getUser("username")


        //then
        assertThat(result).isInstanceOf(Result.Error::class.java)
        val error = (result as Result.Error).error
        assertThat(error).isEqualTo(NetworkError.UNKNOWN)
    }

    @Test
    fun `getUser on bad request return correct error`(): Unit = runBlocking {
        //before
        coEvery { apiService.getUser("username") } returns Response.error(
            400, "{}".toResponseBody("application/json".toMediaTypeOrNull())
        )
        //when
        val result = sut.getUser("username")

        //then
        assertThat(result).isInstanceOf(Result.Error::class.java)
        val error = (result as Result.Error).error
        assertThat(error).isEqualTo(NetworkError.BAD_REQUEST)
    }

    @Test
    fun `getUser on server error return correct error`(): Unit = runBlocking {
        //before
        coEvery { apiService.getUser("username") } returns Response.error(
            500, "{}".toResponseBody("application/json".toMediaTypeOrNull())
        )
        //when
        val result = sut.getUser("username")

        //then
        assertThat(result).isInstanceOf(Result.Error::class.java)
        val error = (result as Result.Error).error
        assertThat(error).isEqualTo(NetworkError.SERVER_ERROR)
    }


    @Test
    fun `getUser on user not found return correct error`(): Unit = runBlocking {
        //before
        coEvery { apiService.getUser("username") } returns Response.error(
            404, "{}".toResponseBody("application/json".toMediaTypeOrNull())
        )
        //when
        val result = sut.getUser("username")

        //then
        assertThat(result).isInstanceOf(Result.Error::class.java)
        val error = (result as Result.Error).error
        assertThat(error).isEqualTo(NetworkError.NOT_FOUND)
    }

}