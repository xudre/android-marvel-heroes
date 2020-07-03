package com.xudre.marvelheroes.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xudre.marvelheroes.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private val delayDuration = 2000L

    private var viewBinding: FragmentSplashBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSplashBinding.inflate(inflater, container, false)

        viewBinding = binding

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        Handler().postDelayed({
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToCharacterListFragment())
        }, delayDuration)
    }

    override fun onDestroy() {
        super.onDestroy()

        viewBinding = null
    }
}
