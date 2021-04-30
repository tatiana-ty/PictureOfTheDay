package geekbrains.material.model

import com.google.gson.annotations.SerializedName

data class PeopleInSpaceServerResponseData(
        @field:SerializedName("message") val message: String?,
        @field:SerializedName("number") val number: Int?,
        @field:SerializedName("people") val people: List<People>?
)

data class People(
        @field:SerializedName("name") val name: String?
)