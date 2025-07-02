package com.example.sparka_v0.ui.splashscreen.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sparka_v0.R
import com.example.sparka_v0.ui.splashscreen.login.LoginActivity
import androidx.core.content.edit
import com.example.sparka_v0.ui.splashscreen.parkiranmoment.DetailParkirActivity

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var rvFakultas: RecyclerView
    private lateinit var adapter: FakultasAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFakultas = view.findViewById(R.id.rv_fakultas)
        val logoutBtn = view.findViewById<ImageView>(R.id.btn_logout)

        adapter = FakultasAdapter { data ->
            val intent = Intent(requireContext(), DetailParkirActivity::class.java).apply {
                putExtra("fakultas_id", data.id)
                putExtra("nama", data.nama)
                putExtra("deskripsi", data.deskripsi)
            }
            startActivity(intent)
        }

        rvFakultas.layoutManager = LinearLayoutManager(requireContext())
        rvFakultas.adapter = adapter

        val token = requireContext()
            .getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            .getString("access_token", null)

        if (!token.isNullOrEmpty()) {
            viewModel.getFakultas(token)
        }

        viewModel.fakultasList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        logoutBtn.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Yakin mau logout?")
                .setPositiveButton("Ya") { _, _ ->
                    val sharedPref = requireContext().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                    sharedPref.edit { remove("access_token") }

                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finishAffinity()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }
}
