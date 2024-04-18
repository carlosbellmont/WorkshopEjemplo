package com.workshop

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Hero (
    val count: Long,
    val next: String,
    val previous: JsonElement? = null,
    val results: List<Result>
)

@Serializable
data class Result (
    val name: String,
    val height: String,
    val mass: String,

    @SerialName("hair_color")
    val hairColor: String,

    @SerialName("skin_color")
    val skinColor: String,

    @SerialName("eye_color")
    val eyeColor: String,

    @SerialName("birth_year")
    val birthYear: String,

    val gender: Gender,
    val homeworld: String,
    val films: List<String>,
    val species: List<String>,
    val vehicles: List<String>,
    val starships: List<String>,
    val created: String,
    val edited: String,
    val url: String
)

@Serializable
enum class Gender(val value: String) {
    @SerialName("female") Female("female"),
    @SerialName("male") Male("male"),
    @SerialName("n/a") NA("n/a");
}