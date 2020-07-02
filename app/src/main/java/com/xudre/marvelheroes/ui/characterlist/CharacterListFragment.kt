package com.xudre.marvelheroes.ui.characterlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xudre.marvelheroes.R
import com.xudre.marvelheroes.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_character_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    override val viewModel: CharacterListViewModel by viewModel()

    private val listAdapter: CharacterListAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()

        listAdapter.characters.clear()

        viewModel.characters.observe(this, Observer { paged ->
            listAdapter.characters.addAll(paged.items)

            listAdapter.notifyDataSetChanged()
        })

        viewModel.getCharacters()
    }

    private fun setupRecyclerView() {
        rv_characters.adapter = listAdapter
        rv_characters.layoutManager = LinearLayoutManager(context)
        rv_characters.setHasFixedSize(true)
    }

}
