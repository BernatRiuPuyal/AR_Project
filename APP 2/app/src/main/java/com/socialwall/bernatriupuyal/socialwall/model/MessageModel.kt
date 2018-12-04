package com.socialwall.bernatriupuyal.socialwall.model

import java.util.*

data class MessageModel(
    var text: String? = null,
    var createdAt: Date? = null,
    var username: String? = null,
    var userId: String? = null,
    var likes: Int? = null
){

}