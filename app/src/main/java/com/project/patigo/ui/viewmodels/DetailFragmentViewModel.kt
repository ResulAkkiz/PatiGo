package com.project.patigo.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.project.patigo.data.firebase.FirebaseFirestoreResult
import com.project.patigo.data.repository.FirebaseAuthRepository
import com.project.patigo.data.repository.FirebaseFirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor(

    private var firebaseFirestoreRepository: FirebaseFirestoreRepository,
    private var firebaseAuthRepository: FirebaseAuthRepository,
) :
    ViewModel() {
    val resultPets = MutableLiveData<FirebaseFirestoreResult>()
    var user:FirebaseUser?=null

    init {
        getPets()
    }

    fun getPets() {
        CoroutineScope(Dispatchers.Main).launch {
            if (user==null){
                user = firebaseAuthRepository.currentUser()
            }

            if (user != null) {
                val result=firebaseFirestoreRepository.getPets(userId = user!!.uid)
                resultPets.value=result
            }

        }
    }



}