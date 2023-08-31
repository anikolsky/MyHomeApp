package com.omtorney.myhometestapp.data.repository

import android.util.Log
import com.omtorney.myhometestapp.data.local.dto.DoorRealm
import com.omtorney.myhometestapp.data.remote.KtorService
import com.omtorney.myhometestapp.domain.mapper.toDoor
import com.omtorney.myhometestapp.domain.mapper.toDoorRealm
import com.omtorney.myhometestapp.domain.model.Door
import com.omtorney.myhometestapp.util.Resource
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class DoorRepositoryImpl(
    private val service: KtorService,
    private val db: Realm
) : DoorRepository {

    override suspend fun fetchRemote(): Flow<Resource<List<Door>>> = flow {
        try {
            emit(Resource.Loading())
            val result = service.fetchDoors().data?.map { it.toDoor() } ?: emptyList()
            emit(Resource.Success(data = result))
        } catch (e: HttpRequestTimeoutException) {
            emit(Resource.Error(message = "Request timed out: ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown error"))
        }
    }

    override suspend fun getLocal(): Flow<List<Door>> {
        return db.query<DoorRealm>().asFlow().map { realmResults ->
            realmResults.list.map { it.toDoor() }
        }
    }

    override suspend fun add(doors: List<Door>) {
        doors.forEach { door ->
            db.write {
                copyToRealm(
                    instance = door.toDoorRealm(),
                    updatePolicy = UpdatePolicy.ALL
                )
            }
        }
    }

    override suspend fun updateFavorite(door: Door, favorite: Boolean) {
        db.write {
            query<DoorRealm>(query = "id == $0", door.id).first().find()?.apply {
                isFavorite = !isFavorite
            }
        }
    }

    override suspend fun updateName(door: Door) {
        db.write {
            query<DoorRealm>(query = "id == $0", door.id).first().find()?.apply {
                name = door.name
            }
        }
    }

    override suspend fun delete(door: Door) {
        db.write {
            query<DoorRealm>(query = "id == $0", door.id).first().find()?.let { door ->
                try {
                    delete(door)
                } catch (e: Exception) {
                    Log.d("DoorRepositoryImpl", e.localizedMessage ?: "Unknown error")
                }
            }
        }
    }
}
