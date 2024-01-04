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
import com.project.patigo.data.entity.Comment
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
                carerReviewList = listOf(
                    Comment(
                        "19.02.2020",
                        "Mehmet",
                        "Kaya",
                        5.0,
                        "Çok sabırlı ve anlayışlı bir bakıcıydı, evcil hayvanıma çok iyi baktı."
                    ),
                    Comment(
                        "22.06.2021",
                        "Ali",
                        "Yılmaz",
                        5.0,
                        "Beklentilerimi aştı, evcil hayvanım için harika bir bakım sağladı."
                    ),
                    Comment(
                        "15.07.2023",
                        "Fatma",
                        "Kaya",
                        4.0,
                        "Hayvan bakıcısı olarak harika bir iş çıkardı. Evcil hayvanım onunla çok mutlu!"
                    )
                ),
                carerProfilePict = "https://randomuser.me/api/portraits/women/75.jpg",
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
                carerServices = listOf(
                    "Evcil Hayvan Oteli",
                    "Pet Taksi",
                    "Pet Taksi",
                    "Pet Taksi",
                    "Pet Taksi",
                    "Pet Taksi",
                    "Pet Taksi",
                    "Pet Taksi"
                ),
                carerProvince = "Şişli",
                carerReviewList = listOf(
                    Comment(
                        "19.04.2022",
                        "Fatma",
                        "Kara",
                        5.0,
                        "Beklentilerimi aştı, evcil hayvanım için harika bir bakım sağladı."
                    ),
                    Comment(
                        "08.06.2020",
                        "Ali",
                        "Kara",
                        4.0,
                        "Beklentilerimi aştı, evcil hayvanım için harika bir bakım sağladı."
                    ),
                    Comment(
                        "21.02.2021",
                        "Fatma",
                        "Çelik",
                        4.0,
                        "Beklentilerimi aştı, evcil hayvanım için harika bir bakım sağladı."
                    )
                ),
                carerProfilePict = "https://randomuser.me/api/portraits/men/1.jpg",
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
                carerReviewList = listOf(
                    Comment(
                        "28.01.2023",
                        "Zeynep",
                        "Çelik",
                        4.5,
                        "Beklentilerimi aştı, evcil hayvanım için harika bir bakım sağladı."
                    ),
                    Comment(
                        "05.06.2022",
                        "Ayşe",
                        "Kaya",
                        4.5,
                        "Hayvan bakıcısı olarak harika bir iş çıkardı. Evcil hayvanım onunla çok mutlu!"
                    ),
                    Comment(
                        "04.06.2021",
                        "Fatma",
                        "Yılmaz",
                        3.5,
                        "Hayvan bakıcısı olarak harika bir iş çıkardı. Evcil hayvanım onunla çok mutlu!"
                    )
                ),
                carerProfilePict = "https://randomuser.me/api/portraits/women/33.jpg",
                carerInfo = "Beyoğlu'nda yaşayan Elif, freelance grafik tasarımcı ve sertifikalı köpek eğitmeni. Evcil hayvanlara karşı özel bir ilgisi var.",
                carerPhoneNumber = "05001122334"
            )
        )
        binding.advertRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.advertRecyclerView.adapter =
            AdvertRecyclerViewAdapter(requireContext(), dummyCarers)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}