package com.xudre.marvelheroes.ui

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.xudre.marvelheroes.R

abstract class BaseFragment : Fragment() {

    private var loadingView: View? = null

    protected abstract val viewModel: BaseViewModel?

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadingView = view?.findViewById(R.id.v_loading)

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
        }
    }

}
