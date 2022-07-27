package eu.krzdabrowski.starter.basicfeature.data.remote.api

import eu.krzdabrowski.starter.basicfeature.data.remote.model.RocketResponse
import retrofit2.http.GET

interface RocketApi {

    @GET("rockets")
    suspend fun getRockets(): List<RocketResponse>
}
