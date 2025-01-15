package com.example.myapplication3.data.model
import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name") val name: String,
    @SerializedName("region") val region: String,
    @SerializedName("code") val code: String,
    @SerializedName("capital") val capital: String,
    @SerializedName("currency") val currency: Currency,
    @SerializedName("flag") val flag: String,
    @SerializedName("language") val language: Language
)

data class Currency(
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String
)

data class Language(
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String
)