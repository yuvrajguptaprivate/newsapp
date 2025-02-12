package com.example.mynewsapiclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapiclient.databinding.FragmentSavedBinding
import com.example.mynewsapiclient.presentation.adapters.NewsAdapter
import com.example.mynewsapiclient.presentation.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint

class SavedFragment : Fragment() {
    val viewModel: NewsViewModel by viewModels()
    @Inject
    lateinit var newsAdapter: NewsAdapter
private lateinit var binding: FragmentSavedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)
        newsAdapter.setOnItemClickedListner {
            val bundle = Bundle().apply {
                putSerializable("selected_article",it)
            }
            findNavController().navigate(
                R.id.action_savedFragment_to_infoFragment,
                bundle
            )
        }
        initRecyclerview()
        viewModel.getSavedNews().observe(viewLifecycleOwner){ articles ->
            newsAdapter.differ.submitList(articles)
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            (ItemTouchHelper.UP or ItemTouchHelper.DOWN) ,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (position in newsAdapter.differ.currentList.indices) {
                    val article = newsAdapter.differ.currentList[position]
                    viewModel.deleteArticle(article)


                    val updatedList = newsAdapter.differ.currentList.toMutableList()
                    updatedList.removeAt(position)

                    viewLifecycleOwner.lifecycleScope.launch {
                        newsAdapter.differ.submitList(updatedList)
                    }

                    Snackbar.make(view, " Deleted Successfully", Snackbar.LENGTH_LONG)
                        .apply {
                            setAction("Undo") {
                                viewModel.saveArticle(article)
                                updatedList.add(position, article)
                                newsAdapter.differ.submitList(updatedList)
                            }
                            show()
                        }
                }
            }

            }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.savedrv)
        }

    }

    private fun initRecyclerview() {
        binding.savedrv.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}