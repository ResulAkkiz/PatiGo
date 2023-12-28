package com.project.patigo.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.project.patigo.data.repository.FirebaseFirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PetFragmentViewModel @Inject constructor(private var firebaseFirestoreRepository: FirebaseFirestoreRepository):
    ViewModel(){

}