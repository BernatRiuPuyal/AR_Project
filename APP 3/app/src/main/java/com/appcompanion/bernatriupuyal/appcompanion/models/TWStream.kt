package com.appcompanion.bernatriupuyal.appcompanion.models

import com.google.gson.annotations.SerializedName

data class TWStream (
    @SerializedName("user_id") var  userId : String? = null,
    @SerializedName("user_name")    var userName: String? = null,
    @SerializedName("game_id")      var gameId: String? = null,
    var type: String? = null,
    var title: String? = null,
    @SerializedName("viewer_count")      var viewerCount: String? = null,
    @SerializedName("thumbnail_url")      var thumbnailUrl: String? = null




)


data class TWStreamResponse(
    var data: ArrayList<TWStream>? = null
)