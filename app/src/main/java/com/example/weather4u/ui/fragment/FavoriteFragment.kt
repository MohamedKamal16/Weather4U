package com.example.weather4u.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather4u.databinding.FragmentFavoriteBinding
import com.example.weather4u.model.local.favorite.FavoriteEntity
import com.example.weather4u.ui.adabter.favorite.FavoriteAdapter
import com.example.weather4u.ui.viewModel.FavoriteFragmentViewModel
import com.example.weather4u.util.Constant.PLACE_API_KEY
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private var favoriteList = mutableListOf<FavoriteEntity>()

    private val viewModel: FavoriteFragmentViewModel by viewModels()

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback { activityResult ->
            val data: Intent? = activityResult.data
            when (activityResult.resultCode) {
                AutocompleteActivity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        if (place.latLng != null) {
                            val newFavorite = FavoriteEntity(
                                place.address,
                                place.latLng?.latitude,
                                place.latLng?.longitude
                            )
                            viewModel.insertUpdate(newFavorite)
                        }
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    Log.i("place", "RESULT_ERROR ")
                }
                AutocompleteActivity.RESULT_CANCELED -> {
                    Log.i("place", "RESULT_CANCELED")
                }
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        floatingButton()
        init()
        swapToDelete(view)
    }

    private fun floatingButton() {
        binding.floatingActionButton.setOnClickListener(View.OnClickListener {
            Places.initialize(requireContext(), PLACE_API_KEY)
            val fieldList = listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList)
                .build(requireContext())
            activityResultLauncher.launch(intent)
        })
    }
    private fun init() {
        favoriteAdapter = FavoriteAdapter(favoriteList)
        binding.RecycleView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = favoriteAdapter
        }
    }

    private fun observe() {
        viewModel.getAll().observe(viewLifecycleOwner, Observer {
            favoriteAdapter.updateList(it)
        })
    }


    private fun swapToDelete(view: View) {
        val itemTouchCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val favoriteEntity = favoriteAdapter.favoriteData[position]
                viewModel.delete(favoriteEntity)
                Snackbar.make(view, "Successfully deleted article", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        viewModel.insertUpdate(favoriteEntity)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchCallBack).apply {
            attachToRecyclerView(binding.RecycleView)
        }
        observe()
    }


}


