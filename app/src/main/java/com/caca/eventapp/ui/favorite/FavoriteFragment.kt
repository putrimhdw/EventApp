package com.caca.eventapp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.caca.eventapp.data.local.room.FavoriteEvent
import com.caca.eventapp.data.response.ListEventsItem
import com.caca.eventapp.databinding.FragmentFavoriteBinding
import com.caca.eventapp.utils.adapter.ListEventAdapter
import com.caca.eventapp.utils.factory.ViewModelFactoryWithApplication

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root : View = binding.root

        val favoriteViewModel = obtainViewModel(requireActivity())

        binding.favoriteEventListRv.layoutManager = LinearLayoutManager(requireActivity())



        favoriteViewModel.gettingUserFavorite().observe(viewLifecycleOwner) {
            val temp = mutableListOf<ListEventsItem>()
            for (item in it) {
                temp.add(ListEventsItem(
                    id = item.id,
                    name = item.title,
                    summary = item.description,
                    imageLogo = item.image
                ))
            }

            val favoriteList: List<ListEventsItem> = temp
            binding.favoriteEventListRv.adapter = ListEventAdapter(favoriteList)

        }


        return root
    }

    private fun obtainViewModel(activity: FragmentActivity): FavoriteViewModel {
        val factory = ViewModelFactoryWithApplication.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }
}