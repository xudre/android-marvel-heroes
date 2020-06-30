package com.xudre.marvelheroes.ui.comicbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xudre.marvelheroes.R

class ComicBookFragment : Fragment() {

    companion object {
        fun newInstance() = ComicBookFragment()
    }

    private lateinit var viewModel: ComicBookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comic_book, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
