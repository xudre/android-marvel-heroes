package com.xudre.marvelheroes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.xudre.marvelheroes.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layoutInflater = LayoutInflater.from(this)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val connectivityView = binding.root.findViewWithTag("connectivity") as View?

        viewModel.connectivityState(this).observe(this, Observer { connected ->
            connectivityView?.visibility = if (connected) View.GONE else View.VISIBLE
        })
    }
}
