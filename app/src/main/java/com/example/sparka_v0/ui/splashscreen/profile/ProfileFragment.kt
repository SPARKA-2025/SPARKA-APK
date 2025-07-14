package com.example.sparka_v0.ui.splashscreen.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sparka_v0.databinding.FragmentProfileBinding
import androidx.core.net.toUri
import androidx.core.content.edit

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var userPref: SharedPreferences
    private lateinit var viewModel: ProfileViewModel
    private var selectedImageUri: Uri? = null

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri = result.data?.data
            binding.ivProfilePicture.setImageURI(selectedImageUri)
            selectedImageUri?.let {
                userPref.edit { putString("PHOTO_URI", it.toString()) }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        userPref = requireContext().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        loadUserData()
        setupListeners()

        return binding.root
    }

    private fun loadUserData() {
        val nama = userPref.getString("NAMA", "Guest")
        val noHp = userPref.getString("NO_HP", "-")
        val plat = userPref.getString("PLAT_NOMOR", "")
        val photoUri = userPref.getString("PHOTO_URI", null)

        binding.tvName.text = nama
        binding.PhoneEditText.setText(noHp)
        binding.PhoneEditText.isEnabled = false
        binding.editPlat.setText(plat)

        photoUri?.let {
            binding.ivProfilePicture.setImageURI(it.toUri())
        }
    }

    private fun setupListeners() {
        binding.ivProfilePicture.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(intent)
        }

        binding.editPlat.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val newPlat = binding.editPlat.text.toString()
                updatePlatAPI(newPlat)
            }
        }

        binding.tvLogout.setOnClickListener {
            userPref.edit { clear() }
            Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show()
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(requireContext(), "Dark mode ${if (isChecked) "on" else "off"}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updatePlatAPI(newPlat: String) {
        viewModel.updatePlatNomor(newPlat).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {  }
                is Result.Success -> {
                    userPref.edit { putString("PLAT_NOMOR", newPlat) }
                    Toast.makeText(requireContext(), "Plat updated", Toast.LENGTH_SHORT).show()
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), "Failed: ${result.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}