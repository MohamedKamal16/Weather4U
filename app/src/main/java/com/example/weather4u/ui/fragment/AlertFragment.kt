package com.example.weather4u.ui.fragment
/*
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather4u.databinding.FragmentAlertBinding
import com.example.weather4u.model.local.alertRoom.AlertEntity
import com.example.weather4u.view.activity.AddAlert
import com.example.weather4u.view.adabter.alert.AlertAdapter
import com.example.weather4u.viewModel.AlertFragmentViewModel


class AlertFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAlertBinding.inflate(inflater, container, false)
        viewModel=  ViewModelProvider(this)[AlertFragmentViewModel::class.java]
        alertRecyclerViewAdapter= AlertAdapter(arrayListOf(),requireContext(),viewModel)

        val root: View = binding.root
        alertRecyclerView=binding.RecycleViewAlert

        binding.AlertFloatingButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireContext(), AddAlert::class.java)
            startActivity(intent)
        })
        this. viewModel.getAllAlert()

        this.viewModel.getAlertInfo().observe(viewLifecycleOwner) {
            alertRecyclerViewAdapter.updateList(it.reversed()) }

        initRecyclerViewList()

        return root
    }


    private fun initRecyclerViewList(){
        alertRecyclerView .layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        alertRecyclerView.adapter=alertRecyclerViewAdapter

  /*     // this lines like to above line but still not get on scope function
        alertRecyclerView.apply {
        layoutManager = LinearLayoutManager(context)
          adapter = alertRecyclerViewAdapter
           }
        */

    }

    private lateinit var binding: FragmentAlertBinding
    private lateinit var viewModel: AlertFragmentViewModel
    lateinit var alertRecyclerView: RecyclerView
    private lateinit var alertRecyclerViewAdapter :AlertAdapter

}*/