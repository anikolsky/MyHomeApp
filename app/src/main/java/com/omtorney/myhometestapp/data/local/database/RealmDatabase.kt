package com.omtorney.myhometestapp.data.local.database

import com.omtorney.myhometestapp.data.local.dto.CameraRealm
import com.omtorney.myhometestapp.data.local.dto.DoorRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

fun getRealmDatabase(): Realm {

    val config = RealmConfiguration.Builder(
        schema = setOf(
            CameraRealm::class,
            DoorRealm::class
        )
    )
        .name("myhomeapp_db")
        .compactOnLaunch()
        .build()

    return Realm.open(config)
}
