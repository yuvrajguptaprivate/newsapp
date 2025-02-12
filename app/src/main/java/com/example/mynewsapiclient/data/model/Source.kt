package com.example.mynewsapiclient.data.model


import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
){
    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}