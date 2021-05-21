package geekbrains.material.model

import retrofit2.Call
import retrofit2.http.GET

interface PeopleInSpaceAPI {

    @GET("astros.json")
    fun getPeopleInSpace(): Call<PeopleInSpaceServerResponseData>
}