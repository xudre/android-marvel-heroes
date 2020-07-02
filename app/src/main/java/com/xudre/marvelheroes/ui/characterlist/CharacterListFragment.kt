package com.xudre.marvelheroes.ui.characterlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xudre.marvelheroes.databinding.FragmentCharacterListBinding
import com.xudre.marvelheroes.model.CharacterModel
import com.xudre.marvelheroes.ui.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterListFragment : BaseFragment() {

    override val viewModel: CharacterListViewModel by viewModel()

    private var viewBinding: FragmentCharacterListBinding? = null

    private val listAdapter: CharacterListAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listAdapter.onCharacterTouched = {
            selectedCharacter(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCharacterListBinding.inflate(inflater, container, false)

        viewBinding = binding

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecyclerView()

        listAdapter.characters.clear()

        val connectivity = viewModel.connectivityState(requireContext())

        connectivity.observe(viewLifecycleOwner, Observer { connected ->
            if (connected && viewModel.characters.value == null) {
                viewModel.getCharacters()
            }
        })

        viewModel.characters.observe(viewLifecycleOwner, Observer { paged ->
            listAdapter.characters.addAll(paged.items)

            listAdapter.notifyDataSetChanged()
        })

        if (connectivity.value == true) {
            viewModel.getCharacters()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        viewBinding = null
    }

    private fun setupRecyclerView() {
        viewBinding?.apply {
            rvCharacters.adapter = listAdapter
            rvCharacters.layoutManager = LinearLayoutManager(context)
            rvCharacters.setHasFixedSize(true)
        }
    }

    private fun selectedCharacter(character: CharacterModel) {
        findNavController()
            .navigate(
                CharacterListFragmentDirections.actionCharacterListFragmentToCharacterFragment(
                    character
                )
            )
    }

}
