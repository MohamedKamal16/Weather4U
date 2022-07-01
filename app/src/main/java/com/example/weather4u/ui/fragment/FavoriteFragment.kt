package com.example.weather4u.ui.fragment
/*
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather4u.databinding.FragmentFavoriteBinding
import com.example.weather4u.model.local.favorite.FavoriteEntity
import com.example.weather4u.util.PLACE_API_KEY
import com.example.weather4u.util.REQUEST_CODE
import com.example.weather4u.view.adabter.favorite.FavoriteAdapter
import com.example.weather4u.viewModel.FavoriteFragmentViewModel
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

class FavoriteFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[FavoriteFragmentViewModel::class.java]
        favouriteAdapter=FavoriteAdapter(arrayListOf(),viewModel,requireContext())
        val root: View = binding.root
        favoriteRecyclerView =binding.RecycleView
        binding.floatingActionButton.setOnClickListener(View.OnClickListener {
            Places.initialize(requireContext(), PLACE_API_KEY)
            val fieldList = listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(requireContext())
            startActivityForResult(intent, REQUEST_CODE)

        })
        return root
    }

    override fun onResume() {
        super.onResume()
      //use get data method
     this. viewModel.getAll()


      this.  viewModel.getFavoriteInfo().observe(viewLifecycleOwner){
           favouriteAdapter.updateList(it.reversed())

       }

       init()
  }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        val newFavorite = FavoriteEntity()
                        if (place.latLng!=null){
                        newFavorite.lat = place.latLng.latitude
                        newFavorite.lon = place.latLng.longitude
                        newFavorite.title = place.address!!
                        }
                        viewModel.insertUpdate(newFavorite)

                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {

                }
                Activity.RESULT_CANCELED -> {

                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun init(){
        favoriteRecyclerView .layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        favoriteRecyclerView.adapter=favouriteAdapter
        }


    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteFragmentViewModel
    private lateinit var favouriteAdapter: FavoriteAdapter
    private lateinit var favoriteRecyclerView: RecyclerView


}



*/
