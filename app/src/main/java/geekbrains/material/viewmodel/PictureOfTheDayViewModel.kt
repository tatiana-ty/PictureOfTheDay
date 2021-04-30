package geekbrains.material.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import geekbrains.material.BuildConfig
import geekbrains.material.model.PODRetrofitImpl
import geekbrains.material.model.PODServerResponseData
import geekbrains.material.model.PictureOfTheDayData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayViewModel(
        private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
        private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) :
        ViewModel() {

    fun getData(day: Int): LiveData<PictureOfTheDayData> {
        sendServerRequest(day)
        return liveDataForViewToObserve
    }

    private var pattern = "yyyy-MM-dd"
    private var date = SimpleDateFormat(pattern).format(Calendar.getInstance().time)

    private fun sendServerRequest(day: Int) {
        when (day) {
            1 -> date = SimpleDateFormat(pattern).format(Calendar.getInstance().time)
            2 -> {
                val cal = Calendar.getInstance()
                cal.add(Calendar.DATE, -1)
                date = SimpleDateFormat(pattern).format(cal.time)
            }
            3 -> {
                val cal = Calendar.getInstance()
                cal.add(Calendar.DATE, -2)
                date = SimpleDateFormat(pattern).format(cal.time)
            }
        }
        liveDataForViewToObserve.value =
                PictureOfTheDayData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(date, apiKey).enqueue(object :
                    Callback<PODServerResponseData> {
                override fun onResponse(
                        call: Call<PODServerResponseData>,
                        response: Response<PODServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                                PictureOfTheDayData.Success(
                                        response.body()!!
                                )
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                    PictureOfTheDayData.Error(
                                            Throwable("Unidentified error")
                                    )
                        } else {
                            liveDataForViewToObserve.value =
                                    PictureOfTheDayData.Error(
                                            Throwable(message)
                                    )
                        }
                    }
                }

                override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value =
                            PictureOfTheDayData.Error(
                                    t
                            )
                }
            })
        }
    }
}
