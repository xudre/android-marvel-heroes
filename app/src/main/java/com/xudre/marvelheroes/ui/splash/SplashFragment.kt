package com.xudre.marvelheroes.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xudre.marvelheroes.R

class SplashFragment : Fragment() {

    private val delayDuration = 2000L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onStart() {
        super.onStart()

        Handler().postDelayed({
            findNavController().navigate(R.id.characterListFragment)
        }, delayDuration)
    }
}
