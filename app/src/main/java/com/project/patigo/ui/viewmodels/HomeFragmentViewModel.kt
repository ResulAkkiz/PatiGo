package com.project.patigo.ui.viewmodels

class HomeFragmentViewModel {

}
//
//import android.util.Log
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.project.patigo.data.entity.Yemek
//import com.project.patigo.data.repo.FoodieRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import java.lang.Exception
//import javax.inject.Inject
//
//@HiltViewModel
//class HomeFragmentViewModel @Inject constructor(private var foodieRepository: FoodieRepository) :ViewModel() {
//    val yemekList = MutableLiveData<List<Yemek>>()
//
//    init {
//        getYemekler()
//    }
//
//    private fun getYemekler() {
//        CoroutineScope(Dispatchers.Main).launch {
//            try {
//                yemekList.value = foodieRepository.getYemekler()
//            }catch (e: Exception){
//                Log.e("HATA",e.message.toString())
//            }
//
//        }
//    }
//
//}