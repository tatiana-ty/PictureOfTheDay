package geekbrains.material.model

sealed class PeopleInSpaceData {
    data class Success(val serverResponseData: PeopleInSpaceServerResponseData) : PeopleInSpaceData()
    data class Error(val error: Throwable) : PeopleInSpaceData()
    data class Loading(val progress: Int?) : PeopleInSpaceData()
}
