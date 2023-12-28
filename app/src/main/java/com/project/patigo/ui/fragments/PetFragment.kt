package com.project.patigo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.project.patigo.data.entity.Yemek
import com.project.patigo.databinding.FragmentPetBinding
import com.project.patigo.ui.viewmodels.PetFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PetFragment : Fragment() {

    private var _binding: FragmentPetBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PetFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: PetFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentPetBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}