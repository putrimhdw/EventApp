package com.caca.eventapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.caca.eventapp.databinding.FragmentHomeBinding
import com.caca.eventapp.utils.adapter.HorizontalListEventAdapter
import com.caca.eventapp.utils.adapter.ListEventAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rvUpcomingEvent.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFinishedEvent.layoutManager = LinearLayoutManager(requireActivity())

        homeViewModel.isLoadingUpcoming.observe(viewLifecycleOwner){
            showLoadingUpcoming(it)
        }

        homeViewModel.isLoadingFinished.observe(viewLifecycleOwner){
            showLoadingFinished(it)
        }

        homeViewModel.errorMessage.observe(viewLifecycleOwner){
            if (!it.isNullOrEmpty()) {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
            }
        }

        homeViewModel.eventUpcoming.observe(viewLifecycleOwner){
            binding.rvUpcomingEvent.adapter = HorizontalListEventAdapter(it.listEvents)
        }

        homeViewModel.eventFinished.observe(viewLifecycleOwner){
            binding.rvFinishedEvent.adapter = ListEventAdapter(it.listEvents)
        }


        return root
    }

    private fun showLoadingUpcoming(isLoading: Boolean) {
        binding.progressUpcoming.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showLoadingFinished(isLoading: Boolean) {
        binding.progressFinished.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}