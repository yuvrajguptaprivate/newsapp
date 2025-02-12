package com.example.mynewsapiclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.mynewsapiclient.databinding.FragmentInfoBinding
import com.example.mynewsapiclient.presentation.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class InfoFragment : Fragment() {
    private lateinit var fragmentInfoBinding: FragmentInfoBinding
    val viewModel: NewsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentInfoBinding = FragmentInfoBinding.bind(view)
        val args : InfoFragmentArgs by navArgs()
        val article = args.selectedArticle
        fragmentInfoBinding.mywebview.apply {
            webViewClient = WebViewClient()
            if (article.url!=null){
                loadUrl(article.url)
            }
        }

        fragmentInfoBinding.fabSave.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view,"Saved Successfully!",Snackbar.LENGTH_LONG).show()
        }

    }






    }


