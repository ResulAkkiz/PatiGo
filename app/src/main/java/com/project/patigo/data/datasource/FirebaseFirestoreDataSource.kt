package com.project.patigo.data.datasource

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.project.patigo.data.entity.Pet
import com.project.patigo.data.entity.User
import com.project.patigo.data.entity.Yemek
import com.project.patigo.data.firebase.FirebaseFirestoreInterface
import com.project.patigo.data.firebase.FirebaseFirestoreResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseFirestoreDataSource(private val firebaseFirestoreInstance: FirebaseFirestore) :
    FirebaseFirestoreInterface {

    companion object {
        var error = ""
    }

    override suspend fun getUserById(userId: String): FirebaseFirestoreResult =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val docSnapshot =
                    firebaseFirestoreInstance.collection("users").document(userId).get().await()
                if (docSnapshot.exists()) {
                    val data = docSnapshot.data
                    val user = data.let { User.fromMap(data!!) }
                    FirebaseFirestoreResult.Success(user)
                } else {
                    FirebaseFirestoreResult.Failure(error)
                }
            } catch (exception: Exception) {
                error = exception.localizedMessage
                    ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
                FirebaseFirestoreResult.Failure(error)
            }
        }

    override suspend fun updateUser(
        userId: String,
        map: Map<String, Any>,
    ): FirebaseFirestoreResult = withContext(Dispatchers.IO) {
        return@withContext try {
            firebaseFirestoreInstance.collection("users").document(userId).update(map)
                .await()
            FirebaseFirestoreResult.Success(true)
        } catch (e: FirebaseFirestoreException) {
            error =
                e.localizedMessage ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
            FirebaseFirestoreResult.Failure(error)
        } catch (e: Exception) {
            error =
                e.localizedMessage ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
            FirebaseFirestoreResult.Failure(error)
        }
    }

    override suspend fun saveUser(user: User): FirebaseFirestoreResult =
        withContext(Dispatchers.IO) {
            return@withContext try {
                firebaseFirestoreInstance.collection("users").document(user.userId)
                    .set(user.toMap())
                    .await()
                FirebaseFirestoreResult.Success(true)
            } catch (e: FirebaseFirestoreException) {
                error =
                    e.localizedMessage
                        ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
                FirebaseFirestoreResult.Failure(error)
            } catch (e: Exception) {
                error =
                    e.localizedMessage
                        ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
                FirebaseFirestoreResult.Failure(error)
            }
        }

    override suspend fun insertPet(pet: Pet): FirebaseFirestoreResult =
        withContext(Dispatchers.IO) {
            return@withContext try {
                firebaseFirestoreInstance.collection("pets").document(pet.userId)
                    .set(mapOf(pet.petId to pet.toMap()), SetOptions.merge())
                    .await()
                FirebaseFirestoreResult.Success(true)
            } catch (exception: Exception) {
                error = exception.localizedMessage
                    ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
                FirebaseFirestoreResult.Failure(error)
            }
        }

    override suspend fun getPets(userId: String): FirebaseFirestoreResult =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val docSnapshot =
                    firebaseFirestoreInstance.collection("pets").document(userId).get().await()
                if (docSnapshot.exists()) {
                    val data = docSnapshot.data
                    val petList: List<Pet>? = data?.map { singleMap ->
                        Pet.fromMap(singleMap.value as Map<String, Any>)
                    }
                    FirebaseFirestoreResult.Success(petList )
                } else {
                    FirebaseFirestoreResult.Success(listOf())
                }
            } catch (exception: Exception) {
                error = exception.localizedMessage
                    ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
                FirebaseFirestoreResult.Failure(error)
            }
        }


    override suspend fun deletePet(
        userId: String,
        petId: String,
    ): FirebaseFirestoreResult = withContext(Dispatchers.IO) {
        return@withContext try {
            firebaseFirestoreInstance.collection("pets").document(userId)
                .update(mapOf(petId to FieldValue.delete())).await()
            FirebaseFirestoreResult.Success(true)
        } catch (e: Exception) {
            error = e.localizedMessage
                ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
            FirebaseFirestoreResult.Failure(error)
        }
    }

    override suspend fun insertFavorite(userId: String, yemek: Yemek): FirebaseFirestoreResult =
        withContext(Dispatchers.IO) {
            return@withContext try {
                firebaseFirestoreInstance.collection("favorites").document(userId)
                    .set(mapOf(yemek.yemekId.toString() to yemek.toMap()), SetOptions.merge())
                    .await()
                FirebaseFirestoreResult.Success(true)
            } catch (exception: Exception) {
                error = exception.localizedMessage
                    ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
                FirebaseFirestoreResult.Failure(error)
            }
        }

    override suspend fun checkFavorited(userId: String, favoriteId: Int): FirebaseFirestoreResult =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val documentSnapshot =
                    firebaseFirestoreInstance.collection("favorites").document(userId).get().await()
                val favoritesMap = documentSnapshot.data
                var yemekExists=false
                if (favoritesMap != null) {
                     yemekExists = favoritesMap.containsKey(favoriteId.toString())
                }
                FirebaseFirestoreResult.Success(yemekExists)
            } catch (exception: Exception) {
                error = exception.localizedMessage
                    ?: "Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz."
                Log.e("Error", error)
                FirebaseFirestoreResult.Failure(error)
            }
        }


}