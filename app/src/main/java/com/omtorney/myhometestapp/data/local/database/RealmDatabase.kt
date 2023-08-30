package com.omtorney.myhometestapp.data.local.database

import com.omtorney.myhometestapp.data.local.model.Camera
import com.omtorney.myhometestapp.data.local.model.Door
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

fun getRealmDatabase(): Realm {

    val config = RealmConfiguration.Builder(
        schema = setOf(
            Camera::class,
            Door::class
        )
    )
        .name("myhomeapp_db")
        .compactOnLaunch()
        .build()

    return Realm.open(config)
}
