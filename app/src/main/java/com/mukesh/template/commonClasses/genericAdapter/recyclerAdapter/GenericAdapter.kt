package com.mukesh.template.commonClasses.genericAdapter.recyclerAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


/** <T> is used for Layout Type Binding */
/** <M> is used for Data Class for specific type */
/** Pass a <M> in DiffUtil Class */

abstract class GenericAdapter<T : ViewBinding, M : Any>(@AnimRes val animation: Int? = null) :
    PagingDataAdapter<M, RecyclerView.ViewHolder>(GenericDiffUtil()) {

    /**
     * On Create View
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(onCreateView(LayoutInflater.from(parent.context), parent, viewType))
    }


    /**
     * onBind View Holder
     * */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            holder as ViewHolder
            holder.setAnimation(animation)
            @Suppress("UNCHECKED_CAST")
            onBindHolder(holder.binding as T, getItem(position)!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * View Holder Class
     * */
    class ViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)


    /**
     * Get Resources Layout
     * */
    abstract fun onCreateView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewBinding


    /**
     * onBindHolder override
     * */
    abstract fun onBindHolder(holder: T, dataClass: M)


    /**
     *  Animation Function
     *  */
    private fun RecyclerView.ViewHolder.setAnimation(animation: Int?) = try {
        animation?.let {
            this.itemView.animation = AnimationUtils.loadAnimation(this.itemView.context, it)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }


    /**
     * When Adapter Attached to Recycler View
     * */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.setHasFixedSize(true)
        animation?.let { recyclerView.scheduleLayoutAnimation() }
        super.onAttachedToRecyclerView(recyclerView)
    }


    /**
     * When View Detached From Window
     * */
    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        animation?.let {
            (holder as ViewHolder).binding.root.clearAnimation()
        }
        super.onViewDetachedFromWindow(holder)
    }

}