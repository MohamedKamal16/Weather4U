package com.example.weather4u.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.example.weather4u.R
import com.example.weather4u.databinding.FragmentSplachScreenBinding
import com.example.weather4u.ui.activity.WeatherActivity
import com.example.weather4u.util.Constant


class SplachScreenFragment : Fragment() {
    private lateinit var binding: FragmentSplachScreenBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplachScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as WeatherActivity).supportActionBar?.hide()
        val topAnimation = AnimationUtils.loadAnimation(requireContext(),R.anim.top_animation)
        val middleAnimation = AnimationUtils.loadAnimation(requireContext(),R.anim.middle_animation)
        val bottomAnimation = AnimationUtils.loadAnimation(requireContext(),R.anim.bottom_animation)

        binding.mainTitle.startAnimation(topAnimation)
        binding.subTitle.startAnimation(bottomAnimation)
        binding.secounSubTitle.startAnimation(bottomAnimation)
        binding.mainImage.startAnimation(middleAnimation)

        val handler=Handler(Looper.getMainLooper())
        handler.postDelayed({
                findNavController().navigate(R.id.action_splachScreenFragment_to_navigation_weather)
        }, Constant.SplashTimeOut.toLong())

    }


}