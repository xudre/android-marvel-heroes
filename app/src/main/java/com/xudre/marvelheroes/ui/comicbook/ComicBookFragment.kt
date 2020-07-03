package com.xudre.marvelheroes.ui.comicbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.xudre.marvelheroes.config.ApiConfig
import com.xudre.marvelheroes.databinding.FragmentComicBookBinding
import com.xudre.marvelheroes.extension.currency
import com.xudre.marvelheroes.ui.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ComicBookFragment : BaseFragment() {

    override val viewModel: ComicBookViewModel by viewModel()

    private var viewBinding: FragmentComicBookBinding? = null

    private val args: ComicBookFragmentArgs by navArgs()

    private val picasso: Picasso by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentComicBookBinding.inflate(inflater, container, false)

        viewBinding = binding

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val connectivity = viewModel.connectivityState(requireContext())

        connectivity.observe(viewLifecycleOwner, Observer { connected ->
            if (connected) {
                requestComics()
            }
        })

        viewModel.expensiveComic.observe(viewLifecycleOwner, Observer { comic ->
            viewBinding?.apply {
                picasso.load(comic.imageUrl)
                    .into(ivCover)

                tvTitle.text = comic.title
                tvDescription.text = comic.description
                tvDescription.visibility =
                    if (comic.description.isNullOrBlank()) View.GONE else View.VISIBLE
                tvPrice.text = comic.price.currency()
            }
        })

        if (connectivity.value == true) {
            requestComics()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        viewBinding = null
    }

    private fun requestComics() {
        val limit =
            if (args.comicsTotal > 0 && args.comicsTotal <= ApiConfig.PAGING_LIMIT) args.comicsTotal else ApiConfig.PAGING_LIMIT

        viewModel.getComicsFromCharacter(args.characterId, 0, limit)
    }

}
