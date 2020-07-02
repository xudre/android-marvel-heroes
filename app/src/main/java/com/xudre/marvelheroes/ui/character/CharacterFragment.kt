package com.xudre.marvelheroes.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.xudre.marvelheroes.databinding.FragmentCharacterBinding
import com.xudre.marvelheroes.ui.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterFragment : BaseFragment() {

    override val viewModel: CharacterViewModel by viewModel()

    private val picasso: Picasso by inject()

    private var viewBinding: FragmentCharacterBinding? = null

    private val args: CharacterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCharacterBinding.inflate(inflater, container, false)

        viewBinding = binding

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.character.observe(viewLifecycleOwner, Observer { character ->
            viewBinding?.apply {
                picasso.load(character.imageUrl)
                    .into(ivAvatar)

                tvName.text = character.name
                tvBio.text = character.description
            }
        })

        viewModel.character.value = args.character
    }

    override fun onDestroy() {
        super.onDestroy()

        viewBinding = null
    }

}
