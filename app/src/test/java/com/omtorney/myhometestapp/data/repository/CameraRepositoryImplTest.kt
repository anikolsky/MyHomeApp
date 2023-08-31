package com.omtorney.myhometestapp.data.repository

import com.omtorney.myhometestapp.data.local.dto.CameraRealm
import com.omtorney.myhometestapp.data.remote.KtorService
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class CameraRepositoryImplTest {

    private lateinit var db: Realm
    private lateinit var repository: CameraRepository
    private val mockService = KtorService.create()

    @Before
    fun setUp() {
        db = Realm.open(
            RealmConfiguration.Builder(setOf(CameraRealm::class))
                .inMemory()
                .build()
        )
        repository = CameraRepositoryImpl(mockService, db)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `Insert list of cameras and get`() = runBlocking {
        val camera = CameraRealm().apply {
            id = 100
            name = "camera 1"
        }
        repository.add(listOf(camera))

        val result = repository.getLocal().first()

        assert(result == listOf(camera))
    }
}
