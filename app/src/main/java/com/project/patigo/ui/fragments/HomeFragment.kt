package com.project.patigo.ui.fragments

import AdvertRecyclerViewAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.project.patigo.data.entity.Carer
import com.project.patigo.databinding.FragmentHomeBinding
import com.project.patigo.ui.viewmodels.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: HomeFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.advertRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val dummyCarers = listOf(
            Carer(
                carerId = "1",
                carerName = "Ayşe",
                carerSurname = "Yılmaz",
                carerAge = 32,
                carerAvailableDay = listOf("Pazartesi", "Çarşamba", "Cuma"),
                carerIban = "TR00123456789",
                carerServices = listOf("Köpek Yürüyüşü", "Kedi Bakımı"),
                carerProvince = "Kadıköy",
                carerReviewList = listOf(),
                carerProfilePict = "https://randomuser.me/api/portraits/med/women/75.jpg",
                carerInfo = "Kadıköy'de yaşayan, veteriner hekim Ayşe. Cana yakın ve hayvanları çok seven bir kişilik.",
                carerPhoneNumber = "05001234567"
            ),
            Carer(
                carerId = "2",
                carerName = "Mehmet",
                carerSurname = "Özcan",
                carerAge = 28,
                carerAvailableDay = listOf("Salı", "Perşembe"),
                carerIban = "TR00987654321",
                carerServices = listOf("Evcil Hayvan Oteli", "Pet Taksi","Pet Taksi","Pet Taksi","Pet Taksi","Pet Taksi","Pet Taksi","Pet Taksi"),
                carerProvince = "Şişli",
                carerReviewList = listOf(),
                carerProfilePict = "https://randomuser.me/api/portraits/med/men/5.jpg",
                carerInfo = "Şişli'de profesyonel pet oteli işletmecisi Mehmet. Hayvanlarla ilgilenmek onun için bir tutku.",
                carerPhoneNumber = "05007654321"
            ),
            Carer(
                carerId = "3",
                carerName = "Elif",
                carerSurname = "Demir",
                carerAge = 26,
                carerAvailableDay = listOf("Pazartesi", "Perşembe", "Cumartesi"),
                carerIban = "TR001122334455",
                carerServices = listOf("Köpek Eğitimi", "Kedi Bakımı"),
                carerProvince = "Beyoğlu",
                carerReviewList = listOf(),
                carerProfilePict = "https://randomuser.me/api/portraits/med/women/33.jpg",
                carerInfo = "Beyoğlu'nda yaşayan Elif, freelance grafik tasarımcı ve sertifikalı köpek eğitmeni. Evcil hayvanlara karşı özel bir ilgisi var.",
                carerPhoneNumber = "05001122334"
            )
        )
        binding.advertRecyclerView.adapter=AdvertRecyclerViewAdapter(requireContext(), dummyCarers)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}