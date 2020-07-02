package com.xudre.marvelheroes.ui

import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.xudre.marvelheroes.R

abstract class BaseFragment : Fragment() {

    private val loadingView: View? by lazy {
        view?.findViewById<View>(R.id.v_loading)
    }

    abstract val viewModel: BaseViewModel?

    override fun onStart() {
        super.onStart()

        loadingView?.let {
            it.visibility = View.GONE

            viewModel?.let { vm ->
                vm.loadingState.observe(this, Observer { loading ->
                    it.visibility = if (loading) View.VISIBLE else View.GONE
                })
            }
        }

        viewModel?.let {
            it.errorState.observe(this, Observer { throwed ->
                if (throwed != null) {
                    val toast = Toast.makeText(context, throwed.message, Toast.LENGTH_LONG)

                    toast.setGravity(Gravity.TOP, 0, 0)
                    toast.show()
                }
            })
        }
    }

}
