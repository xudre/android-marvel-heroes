package com.xudre.marvelheroes.ui

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

abstract class BaseFragment : Fragment() {

    protected abstract val viewModel: BaseViewModel?

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val loadingView: View? = view?.findViewWithTag("loading")

        val connectivityView: View? = view?.findViewWithTag("connectivity")

        loadingView?.apply {
            visibility = View.GONE
        }

        viewModel?.let {
            it.loadingState.observe(viewLifecycleOwner, Observer { loading ->
                loadingView?.apply {
                    visibility = if (loading) View.VISIBLE else View.GONE
                }
            })

            it.errorState.observe(viewLifecycleOwner, Observer { trowed ->
                if (trowed != null) {
                    val toast = Toast.makeText(context, trowed.message, Toast.LENGTH_LONG)

                    toast.setGravity(Gravity.TOP, 0, 0)
                    toast.show()
                }
            })

            it.connectivityState(requireContext()).observe(viewLifecycleOwner, Observer { hasConnection ->
                connectivityView?.apply {
                    visibility = if (hasConnection) View.GONE else View.VISIBLE
                }
            })
        }
    }

}
