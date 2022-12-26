package com.mukesh.template.commonClasses.genericAdapter.recyclerAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mukesh.template.databinding.ProgressLoaderBinding


/**
 * Generic Loader Adapter
 * For handle loading state of adapter.
 * If adapter load or call api then it will
 * show loader otherwise hide the loader.
 * */
class GenericLoadAdapter : LoadStateAdapter<GenericLoadAdapter.ViewHolder>() {

    /**
     * On Create View Holder
     * */
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        ViewHolder(
            ProgressLoaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )


    /**
     * On Bind View Holder
     * */
    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        Log.e("dsfsdfsdfsd", "fdsfsdf  $loadState")
        holder.bind(loadState)
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

    }

    /**
     * View Holder
     * */
    inner class ViewHolder(private val binding: ProgressLoaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Loading) binding.root.visibility = View.VISIBLE
        }
    }


    /**
     * Display Load State As Item
     * */
    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        Log.e("dsfsdfsdfsd", "fdsfsdf 1111111111111 $loadState")
        return (loadState is LoadState.Loading) || (loadState is LoadState.Error)
    }


}