package com.project.patigo.data.repository

import com.project.patigo.data.datasource.FirebaseFirestoreDataSource
import com.project.patigo.data.entity.Pet
import com.project.patigo.data.entity.User
import com.project.patigo.data.entity.Yemek

class FirebaseFirestoreRepository(private val firebaseFirestoreDataSource: FirebaseFirestoreDataSource) {
    suspend fun getUserById(
        userId: String,
    ) = firebaseFirestoreDataSource.getUserById(userId)


    suspend fun saveUser(
        user: User,
    ) = firebaseFirestoreDataSource.saveUser(user)

    suspend fun updateUser(
        userId: String,
        map: Map<String, Any>,
    ) = firebaseFirestoreDataSource.updateUser(userId, map)

    suspend fun insertPet(
        pet: Pet,
    ) = firebaseFirestoreDataSource.insertPet(pet)


}