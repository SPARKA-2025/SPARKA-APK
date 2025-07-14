package com.example.sparka_v0.ui.splashscreen.order

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sparka_v0.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadOrderData()
    }

    private fun loadOrderData() {
        val sharedPref = requireContext().getSharedPreferences("ORDER_PREF", Context.MODE_PRIVATE)

        val seatId = sharedPref.getInt("SEAT_ID", -1)
        val plat = sharedPref.getString("PLAT", "-")
        val status = sharedPref.getString("STATUS", "-")

        if (seatId == -1 || plat == "-") {
            binding.tvSeat.text = "Belum ada pesanan"
            binding.tvPlat.text = ""
            binding.tvStatus.text = ""
        } else {
            binding.tvSeat.text = "Slot: $seatId"
            binding.tvPlat.text = "Plat: $plat"
            binding.tvStatus.text = "Status: $status"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}