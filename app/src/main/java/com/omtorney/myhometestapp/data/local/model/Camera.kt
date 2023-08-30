package com.omtorney.myhometestapp.data.local.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Camera : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var room: String = ""
    var preview: String = ""
    var isFavorite: Boolean = false
    var isRecording: Boolean = false
}
