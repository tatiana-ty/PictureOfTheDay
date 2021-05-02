package geekbrains.material.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import geekbrains.material.BuildConfig
import geekbrains.material.model.PeopleInSpaceData
import geekbrains.material.model.PeopleInSpaceRetrofitImpl
import geekbrains.material.model.PeopleInSpaceServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PeopleInSpaceViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PeopleInSpaceData> = MutableLiveData(),
    private val retrofitImpl: PeopleInSpaceRetrofitImpl = PeopleInSpaceRetrofitImpl()
) :
    ViewModel() {

    fun getData(): LiveData<PeopleInSpaceData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {

        liveDataForViewToObserve.value =
            PeopleInSpaceData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PeopleInSpaceData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPeopleInSpace().enqueue(object :
                Callback<PeopleInSpaceServerResponseData> {
                override fun onResponse(
                    call: Call<PeopleInSpaceServerResponseData>,
                    response: Response<PeopleInSpaceServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            PeopleInSpaceData.Success(
                                response.body()!!
                            )
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                PeopleInSpaceData.Error(
                                    Throwable("Unidentified error")
                                )
                        } else {
                            liveDataForViewToObserve.value =
                                PeopleInSpaceData.Error(
                                    Throwable(message)
                                )
                        }
                    }
                }

                override fun onFailure(call: Call<PeopleInSpaceServerResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value =
                        PeopleInSpaceData.Error(
                            t
                        )
                }
            })
        }
    }
}