package com.project.patigo.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.patigo.data.entity.Pet
import com.project.patigo.data.firebase.FirebaseFirestoreResult
import com.project.patigo.data.repository.FirebaseAuthRepository
import com.project.patigo.data.repository.FirebaseFirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetFragmentViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository,
) :
    ViewModel() {
    val resultPets = MutableLiveData<FirebaseFirestoreResult>()

    fun getPets() {
        CoroutineScope(Dispatchers.Main).launch {
            val user = firebaseAuthRepository.currentUser()
            if (user != null) {
                val result=firebaseFirestoreRepository.getFavorites(userId = user.uid)
                resultPets.value=result
            }

            resultPets.value=FirebaseFirestoreResult.Success(data =  listOf(
                Pet("1", "Tarçın", false, 8.2, 1, "Köpek", "tarcin.png", "Sevecen ancak havlamayı seven bir köpek."),
                Pet("2", "Minnak", true, 3.5, 2, "Kedi", "minnak.png", "Uysal ve oyuncu, suyu çok sever."),
                Pet("3", "Paşa", false, 15.0, 3, "Köpek", "pasa.png", "Dost canlısı ama yabancılara karşı mesafeli."),
                Pet("4", "Boncuk", true, 2.0, 4, "Kedi", "boncuk.png", "Çok meraklı ve enerjik bir kedi."),
                Pet("5", "Karabas", false, 20.0, 5, "Köpek", "karabas.png", "Oyuncu ve korumacı, suyu sevmez."),
                Pet("6", "Limon", true, 1.2, 1, "Kuş", "limon.png", "Çok şen ve ötüşü güzel bir kuş.")
            ) )

        }
    }

//    fun deleteFavorite(favoriteId: Int) {
//        CoroutineScope(Dispatchers.Main).launch {
//            val user = firebaseAuthRepository.currentUser()
//            if (user != null) {
//                firebaseFirestoreRepository.deleteFavorite(user.uid,favoriteId)
//                getFavorites()
//            }
//
//        }
//    }

}