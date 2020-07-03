package com.xudre.marvelheroes.ui.characterlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xudre.marvelheroes.databinding.FragmentCharacterListBinding
import com.xudre.marvelheroes.model.CharacterModel
import com.xudre.marvelheroes.ui.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterListFragment : BaseFragment() {

    override val viewModel: CharacterListViewModel by viewModel()

    private var viewBinding: FragmentCharacterListBinding? = null

    private val listAdapter: CharacterListAdapter by inject()

    private var isLastPage = false

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

        val connectivity = viewModel.connectivityState(requireContext())

        connectivity.observe(viewLifecycleOwner, Observer { connected ->
            if (connected && viewModel.characters.value?.items.isNullOrEmpty() && !isLastPage) {
                requestCharacters()
            }
        })

        viewModel.loadingState.observe(viewLifecycleOwner, Observer { loading ->
            if (!loading && isLastPage) {
                listAdapter.removeLoading()
            }
        })

        viewModel.characters.observe(viewLifecycleOwner, Observer { paged ->
            viewBinding?.apply {
                flLoading.visibility = if (paged.page.toInt() >= 0) View.GONE else View.VISIBLE
            }

            isLastPage = paged.items.isNullOrEmpty()

            listAdapter.removeLoading()

            listAdapter.addItems(paged.items)

            listAdapter.notifyDataSetChanged()
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        viewBinding = null
    }

    private fun setupRecyclerView() {
        viewBinding?.apply {
            val layoutManager = LinearLayoutManager(context)

            rvCharacters.adapter = listAdapter
            rvCharacters.layoutManager = layoutManager
            rvCharacters.setHasFixedSize(false)
            rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val verticalThreshold = 5

                    if (viewModel.loadingState.value != true &&
                        !isLastPage &&
                        !recyclerView.canScrollVertically(verticalThreshold)
                    ) {
                        requestNextCharacters()
                    }
                }
            })
        }
    }

    private fun requestCharacters() {
        listAdapter.clearItems()

        viewModel.getCharacters()
    }

    private fun requestNextCharacters() {
        viewModel.characters.value?.let { paged ->
            val onPage = paged.page.toInt()

            viewModel.getCharacters(onPage + 1)
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
