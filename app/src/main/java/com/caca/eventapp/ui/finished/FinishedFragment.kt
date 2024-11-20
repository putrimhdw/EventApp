package com.caca.eventapp.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.caca.eventapp.databinding.FragmentFinishedBinding
import com.caca.eventapp.utils.adapter.ListEventAdapter

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val finishedViewModel =
            ViewModelProvider(this)[FinishedViewModel::class.java]

        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        finishedViewModel.getEventFinished()

        finishedViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        finishedViewModel.errorMessage.observe(viewLifecycleOwner){
            if (!it.isNullOrEmpty()) {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
            }
        }

        binding.rvFinishedEvent.layoutManager = LinearLayoutManager(requireActivity())

        finishedViewModel.event.observe(viewLifecycleOwner) {
            binding.rvFinishedEvent.adapter = ListEventAdapter(it.listEvents)
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