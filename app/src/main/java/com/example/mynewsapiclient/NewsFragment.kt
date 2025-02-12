package com.example.mynewsapiclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapiclient.data.util.Resource
import com.example.mynewsapiclient.databinding.FragmentNewsBinding
import com.example.mynewsapiclient.presentation.adapters.NewsAdapter
import com.example.mynewsapiclient.presentation.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint

class NewsFragment : Fragment() {
     val viewModel: NewsViewModel by viewModels()
    @Inject
     lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: FragmentNewsBinding
    private var country = "us"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var islastlastpage = false
    private var pages = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)
        newsAdapter.setOnItemClickedListner {
            val bundle = Bundle().apply {
                putSerializable("selected_article",it)
            }
            findNavController().navigate(
                R.id.action_newsFragment_to_infoFragment,
                bundle
            )
        }
        initRecyclerview()
        viewNewsList()
        setSearchview()
    }

    private fun viewNewsList() {
        viewModel.getNewsheadlines(country, page)
        viewModel.newsheadlines.observe(viewLifecycleOwner,{
            response ->
            when(response){
                is Resource.Success->{
                    hideprogressbar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        if (it.totalResults % 20 == 0) {
                            pages = it.totalResults / 20
                        } else {
                            val page = it.totalResults / 20+1
                        }
                        islastlastpage = page == pages
                    }
                }
                is Resource.Error->{
                    hideprogressbar()
                    response.message?.let {
                        Toast.makeText(activity, " An error occurred : $it", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading->{
                    showprogressbar()
                }
            }
        })
    }

    private fun initRecyclerview() {
      //  newsAdapter = NewsAdapter()
        binding.myrecyclerinnewsfrag.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewsFragment.onScrollingLISTNER)
        }
    }

    private fun showprogressbar(){
        isLoading = true
        binding.myprogressbarinnewsfrag.visibility = View.VISIBLE
    }

    private fun hideprogressbar(){
        isLoading = false
        binding.myprogressbarinnewsfrag.visibility = View.INVISIBLE
    }

    private val onScrollingLISTNER = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if( newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager  = binding.myrecyclerinnewsfrag.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItem = layoutManager.childCount
            val topPosition = layoutManager.findLastVisibleItemPosition()

            val hasReachedToend = topPosition+visibleItem >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !islastlastpage && hasReachedToend && isScrolling
            if(shouldPaginate){
                page++
                viewModel.getNewsheadlines(country,page)
                isScrolling = false
            }
        }
    }


    //search
    private fun setSearchview(){
        binding.searchview.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    viewModel.seachNews("us",page,p0.toString())
                    viewsearched()
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    MainScope().launch {
                        delay(2000)
                    }
                    viewModel.seachNews("us",page,p0.toString())
                    viewsearched()
                    return false
                }
            }
        )
        binding.searchview.setOnCloseListener(
           object : SearchView.OnCloseListener{
               override fun onClose(): Boolean {
                   initRecyclerview()
                   viewNewsList()
                   return false
               }

           }
        )
    }

     fun viewsearched(){
        viewModel.searchedNews.observe(viewLifecycleOwner,{
                response ->
            when(response){
                is Resource.Success->{
                    hideprogressbar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        if (it.totalResults % 20 == 0) {
                            pages = it.totalResults / 20
                        } else {
                            val page = it.totalResults / 20+1
                        }
                        islastlastpage = page == pages
                    }
                }
                is Resource.Error->{
                    hideprogressbar()
                    response.message?.let {
                        Toast.makeText(activity, " An error occurred : $it", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading->{
                    showprogressbar()
                }
            }
        })
    }
}