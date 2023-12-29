package com.project.patigo.ui.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

import com.project.patigo.R
import com.project.patigo.databinding.FragmentErrorBottomSheetBinding
import com.project.patigo.MainActivity
import com.project.patigo.data.entity.User
import com.project.patigo.data.firebase.FirebaseFirestoreResult
import com.project.patigo.databinding.BottomSheetDialogBinding
import com.project.patigo.databinding.FragmentProfileBinding
import com.project.patigo.ui.viewmodels.ProfileFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var user: User? = null
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileFragmentViewModel
    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2
    private var profilePict: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: ProfileFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUser()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.scrollView2.visibility = View.GONE
        binding.progressBar3.visibility = View.VISIBLE
        binding.profilePictureImageView.setOnClickListener {
            showBottomSheetDialog()
        }
        binding.profileUpdateButton.setOnClickListener {
            Log.e("TAG", "set onclick listener")
            checkValidation(
                R.drawable.outline_info_24,
                if (user?.userPicture != null || profilePict != null) "Lütfen gerekli yerleri doldurunuz." else "Lütfen profil fotoğrafını boş bırakmayınız"
            ) {

                user.let {
                    if (profilePict != null){
                        viewModel.uploadPicture(profilePict!!){url->
                            viewModel.updateUser(
                                user!!.userId,
                                hashMapOf(
                                    "userName" to binding.profileNameEditText.text.toString().trim(),
                                    "userSurname" to binding.profileSurnameEditText.text.toString()
                                        .trim(),
                                    "userPhoneNumber" to binding.profilePhoneNumberEditText.text.toString()
                                        .trim(),
                                    "userAddress" to binding.profileAdressEditText.text.toString()
                                        .trim(),
                                    "userPicture" to url!!
                                )
                            ) { result ->
                                when (result) {
                                    is FirebaseFirestoreResult.Success<*> -> {
                                        showErrorBottomSheetDialog(
                                            R.drawable.success_gif_im,
                                            "Güncelleme işlemi başarıyla gerçekleşti."
                                        )
                                    }

                                    is FirebaseFirestoreResult.Failure -> {
                                        showErrorBottomSheetDialog(
                                            R.drawable.failure_gif_im,
                                            "Bir hata meydana geldi: ${result.error}"
                                        )
                                    }

                                }
                                Log.e("TAG", result.toString())

                            }
                        }

                    }else{
                        viewModel.updateUser(
                            user!!.userId,
                            hashMapOf(
                                "userName" to binding.profileNameEditText.text.toString().trim(),
                                "userSurname" to binding.profileSurnameEditText.text.toString()
                                    .trim(),
                                "userPhoneNumber" to binding.profilePhoneNumberEditText.text.toString()
                                    .trim(),
                                "userAddress" to binding.profileAdressEditText.text.toString()
                                    .trim(),
                            )
                        ) { result ->
                            when (result) {
                                is FirebaseFirestoreResult.Success<*> -> {
                                    showErrorBottomSheetDialog(
                                        R.drawable.success_gif_im,
                                        "Güncelleme işlemi başarıyla gerçekleşti."
                                    )
                                }

                                is FirebaseFirestoreResult.Failure -> {
                                    showErrorBottomSheetDialog(
                                        R.drawable.failure_gif_im,
                                        "Bir hata meydana geldi: ${result.error}"
                                    )
                                }

                            }
                            Log.e("TAG", result.toString())

                        }
                    }

                }


            }


        }

        binding.signOutButton.setOnClickListener {
            viewModel.signOut { result ->
                if (result) {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }
            }
        }



        viewModel.getUserResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is FirebaseFirestoreResult.Success<*> -> {
                    if (result.data is User)
                        user = result.data
                    with(binding) {
                        profileAdressEditText.setText(user?.userAddress)
                        profileNameEditText.setText(user?.userName)
                        profileSurnameEditText.setText(user?.userSurname)
                        profilePhoneNumberEditText.setText(user?.userPhoneNumber)
                        if (!(user?.userPicture.isNullOrEmpty())) {
                            Glide.with(requireContext()).load(user?.userPicture)
                                .into(binding.profilePictureImageView)
                        }

                    }
                }

                is FirebaseFirestoreResult.Failure -> {
                    showErrorBottomSheetDialog(
                        R.drawable.outline_info_24,
                        "Bir hata meydana geldi: ${result.error}"
                    )
                }

            }
            binding.scrollView2.visibility = View.VISIBLE
            binding.progressBar3.visibility = View.GONE


        }


        return binding.root
    }


    private fun checkValidation(resInt: Int, info: String, onSuccessful: () -> Unit) {
        if (binding.profileAdressEditText.text.isNullOrBlank() || binding.profileNameEditText.text.isNullOrBlank() || binding.profileSurnameEditText.text.isNullOrBlank() || binding.profilePhoneNumberEditText.text.isNullOrBlank()) {

            showErrorBottomSheetDialog(resInt, info)

            with(binding) {
                if (profileSurnameEditText.text.isNullOrBlank()) profileSurnameEditText.error =
                    "Soyadınızı lütfen boş bırakmayınız."
                if (profileNameEditText.text.isNullOrBlank()) profileNameEditText.error =
                    "Adınızı lütfen boş bırakmayınız."
                if (profileAdressEditText.text.isNullOrBlank()) profileAdressEditText.error =
                    "Adres bilginizi lütfen boş bırakmayınız."
                if (profilePhoneNumberEditText.text.isNullOrBlank()) profilePhoneNumberEditText.error =
                    "Telefon numarınızı lütfen boş bırakmayınız."
            }
        } else {
            onSuccessful()

        }

    }

    private fun showBottomSheetDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = BottomSheetDialogBinding.inflate(
            LayoutInflater.from(requireContext()),
            null,
            false
        )
        bottomSheetBinding.cameraButton.setOnClickListener {
            dialog.dismiss()
            cameraCheckPermission()
        }
        bottomSheetBinding.galleryButton.setOnClickListener {
            dialog.dismiss()
            galleryCheckPermission()
        }

        dialog.setCancelable(true)
        dialog.setContentView(bottomSheetBinding.root)
        dialog.show()

    }

    private fun galleryCheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Dexter.withContext(requireContext()).withPermissions(
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_IMAGES
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            gallery()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?,
                ) {
                    showRationalDialogForPermission()
                }

            }).onSameThread().check()
        } else {
            Dexter.withContext(requireContext()).withPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE,

                ).withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    gallery()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(
                        requireContext(),
                        "Fotoğraf seçmek için gerekli olan izini redettiniz",
                        Toast.LENGTH_SHORT
                    ).show()

                    showRationalDialogForPermission()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?,
                ) {
                    showRationalDialogForPermission()
                }

            }
            ).onSameThread().check()
        }
    }

    private fun cameraCheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Dexter.withContext(requireContext()).withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_IMAGES
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            camera()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?,
                ) {
                    showRationalDialogForPermission()
                }

            }).onSameThread().check()
        } else {
            Dexter.withContext(requireContext()).withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
            ).withListener(
                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        camera()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?,
                    ) {
                        showRationalDialogForPermission()
                    }

                }
            ).onSameThread().check()
        }

    }

    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    profilePict = bitmap
                    binding.profilePictureImageView.load(bitmap) {
                        crossfade(true)
                        crossfade(1000)
                        transformations(CircleCropTransformation())
                    }
                }

                GALLERY_REQUEST_CODE -> {

                    binding.profilePictureImageView.load(data?.data) {
                        crossfade(true)
                        crossfade(1000)
                        transformations(CircleCropTransformation())
                    }
                    profilePict = getBitmapFromUri(data?.data)
                }
            }
        }
    }


    private fun getBitmapFromUri(uri: Uri?): Bitmap? {
        uri ?: return null
        return context?.contentResolver?.openInputStream(uri)?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream)
        }
    }

    private fun showRationalDialogForPermission() {
        AlertDialog.Builder(requireContext()).setMessage("Kamera için izin vermeniz gerekmektedir")
            .setPositiveButton("Ayarlara Git") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context?.packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("İptal") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun showErrorBottomSheetDialog(resInt: Int, info: String) {
        val dialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = FragmentErrorBottomSheetBinding.inflate(
            LayoutInflater.from(requireContext()),
            null,
            false
        )

        Glide.with(requireContext()).load(resInt)
            .into(bottomSheetBinding.errorImage);
        bottomSheetBinding.errorDescription.text = info
        dialog.setCancelable(true)
        dialog.setContentView(bottomSheetBinding.root)
        dialog.show()
    }


}