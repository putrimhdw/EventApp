package com.caca.eventapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.caca.eventapp.R
import com.caca.eventapp.data.local.room.FavoriteEvent
import com.caca.eventapp.databinding.ActivityDetailBinding
import com.caca.eventapp.utils.factory.ViewModelFactoryWithApplication

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var favoriteEventById: FavoriteEvent? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val detailViewModel = obtainViewModel(this)

        val id = intent.getIntExtra(EXTRA_ID, 0)

        detailViewModel.getEventDetail(id)

        detailViewModel.getFavoriteUserByUsername(id).observe(this) {
            favoriteEventById = it
            if (favoriteEventById == null) {
                binding.favoriteFAB.setImageResource(R.drawable.baseline_favorite_border_24)
                return@observe
            }
            binding.favoriteFAB.setImageResource(R.drawable.baseline_favorite_24)
        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }

        detailViewModel.errorMessage.observe(this){
            if (!it.isNullOrEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

        binding.favoriteFAB.setOnClickListener {
            val favoriteUser = FavoriteEvent(
                id = id,
                title = detailViewModel.event.value?.event?.name ?: "",
                description = detailViewModel.event.value?.event?.description ?: "",
                image = detailViewModel.event.value?.event?.imageLogo ?: ""

            )

            if (favoriteEventById == null) {
                detailViewModel.insertFavoriteUser(favoriteUser)
                return@setOnClickListener
            }
            detailViewModel.deleteFavoriteUser(favoriteUser)
        }

        detailViewModel.event.observe(this){ response ->
            Glide.with(this)
                .load(response.event?.mediaCover)
                .into(binding.detailImage)
            val owner = resources.getString(R.string.by) + " " +response.event?.ownerName
            binding.detailOwner.text = owner
            binding.detailTitle.text = response.event?.name
            val beginTime = resources.getString(R.string.begin_time) + " " + response.event?.beginTime
            binding.detailTime.text = beginTime
            binding.detailDescription.text = HtmlCompat.fromHtml(
                response.event?.description.toString(),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )

            val quotaLeft = (response.event?.quota ?: 0) - (response.event?.registrants ?: 0)
            val quotaLeftString = resources.getString(R.string.quota_left) + " " + quotaLeft
            binding.detailQuota.text = quotaLeftString

            binding.actionRegister.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(response.event?.link))
                startActivity(intent)
            }
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactoryWithApplication.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    companion object{
        const val EXTRA_ID = "id"
    }
}