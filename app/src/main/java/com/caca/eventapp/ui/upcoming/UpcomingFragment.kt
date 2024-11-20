package com.caca.eventapp.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.caca.eventapp.data.response.ListEventsItem
import com.caca.eventapp.databinding.FragmentUpcomingBinding
import com.caca.eventapp.utils.adapter.ListEventAdapter

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val upcomingViewModel =
            ViewModelProvider(this)[UpcomingViewModel::class.java]

        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        upcomingViewModel.getEventUpcoming()


        upcomingViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        upcomingViewModel.errorMessage.observe(viewLifecycleOwner){
            if (!it.isNullOrEmpty()) {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
            }
        }

        binding.rvUpcomingEvent.layoutManager = LinearLayoutManager(requireActivity())

        upcomingViewModel.event.observe(viewLifecycleOwner) {
            binding.rvUpcomingEvent.adapter = ListEventAdapter(it.listEvents)

        }

        return root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}