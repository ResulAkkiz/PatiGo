package com.project.patigo.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.project.patigo.R
import com.project.patigo.databinding.FragmentHomeBinding
import com.project.patigo.databinding.FragmentInsertPetBinding
import com.project.patigo.databinding.FragmentPetBinding
import com.project.patigo.ui.viewmodels.InsertPetFragmentViewModel
import com.project.patigo.ui.viewmodels.PetFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsertPetFragment : Fragment() {

    private var _binding: FragmentInsertPetBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: InsertPetFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val tempViewModel: InsertPetFragmentViewModel by viewModels()
        viewModel = tempViewModel
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentInsertPetBinding.inflate(inflater, container, false)
        val view = binding.root

        val listType= listOf("Köpek","Kedi","Kuş","Kemirgen","Sürüngen","Diğer")
        val arrayAdapter=ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,listType)
        binding.petTypeAutoCompleteTextView.setAdapter(arrayAdapter)
        binding.petTypeAutoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            adapterView.getItemAtPosition(i).toString()
        }

        binding.petAgeEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // İşlem yapmanıza gerek yok
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // İşlem yapmanıza gerek yok
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.startsWith("0") && s.length > 1) {

                    binding.petAgeEditText.setText(s.substring(1))
                    binding.petAgeEditText.setSelection(binding.petAgeEditText.text.length) // İmleci metnin sonuna taşı
                }
            }
        })

        binding.petWeightEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // İşlem yapmanıza gerek yok
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // İşlem yapmanıza gerek yok
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.startsWith("0") && s.length > 1) {

                    binding.petWeightEditText.setText(s.substring(1))
                    binding.petWeightEditText.setSelection(binding.petWeightEditText.text.length) // İmleci metnin sonuna taşı
                }
            }
        })



        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}