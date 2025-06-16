package com.ui.listbooked

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ViewModelFactory
import com.data.Result
import com.data.response.DataItemz
import com.example.bottomnavyt.databinding.FragmentOrderBinding
import com.ui.detailbooked.DetailBookedActivity

class Order : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        val root = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory : ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        val bookViewModel = ViewModelProvider(this, viewModelFactory)[BookedViewModel::class.java]

        bookViewModel.getListBooked()

        bookViewModel.bookedResponse.observe(viewLifecycleOwner, Observer {result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val listBookResponse = result.data
                        getListBooked(listBookResponse.data)

                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }


        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getListBooked(listBooked : List<DataItemz>?) {

        if (listBooked != null) {
            val adapterList = BookedAdapter(listBooked)
            binding.listBooking.apply {
                adapter = adapterList
                layoutManager = LinearLayoutManager(requireContext())
            }

            adapterList.setOnItemClickCallback(object : BookedAdapter.OnItemClickCallback{
                override fun onItemClicked(data: DataItemz) {
                    selectedBooked(data)
                }

            })
        } else (
            Toast.makeText(requireContext(), "Tidak ada data", Toast.LENGTH_SHORT).show()
        )
    }

    private fun selectedBooked(data: DataItemz){
//        Toast.makeText(requireContext(), "Kamu memilih ${data.id}", Toast.LENGTH_SHORT).show()

        Log.d("SelectedBooked", "Selected ID: ${data.id}")
        val intent = Intent(requireContext(), DetailBookedActivity::class.java)
        intent.putExtra(DetailBookedActivity.EXTRA_ID, data.id)
        startActivity(intent)
    }

}