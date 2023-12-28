package com.project.patigo.data.firebase

import com.project.patigo.data.entity.User
import com.project.patigo.data.entity.Yemek
import com.project.patigo.data.firebase.FirebaseFirestoreResult

interface FirebaseFirestoreInterface {
    suspend fun getUserById(userId: String): FirebaseFirestoreResult
    suspend fun updateUser(userId: String, map: Map<String, Any>): FirebaseFirestoreResult
    suspend fun saveUser(user: User): FirebaseFirestoreResult

    suspend fun getFavorites(userId: String): FirebaseFirestoreResult
    suspend fun deleteFavorite(userId: String,favoriteId:Int): FirebaseFirestoreResult
    suspend fun insertFavorite(userId: String,yemek:Yemek): FirebaseFirestoreResult
    suspend fun checkFavorited(userId: String,favoriteId:Int): FirebaseFirestoreResult

}