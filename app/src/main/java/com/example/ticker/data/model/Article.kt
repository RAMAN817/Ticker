package com.example.ticker.data.model

import com.squareup.moshi.JsonClass

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Article(
    val category: String,
    val datetime:Long,
    val headline: String,
    val id: Long,
    val image: String?,
    val related: String,
    val source: String,
    val summary: String,
    val url:String
): Parcelable


