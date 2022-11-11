package com.mukesh.template.commonClasses.genericAdapter.recyclerAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


/** <T> is used for Layout Type Binding */
/** <M> is used for Data Class for specific type */
/** Pass a <M> in DiffUtil Class */

abstract class GenericAdapter<T : ViewBinding, M>(@AnimRes val animation: Int? = null) :
    ListAdapter<M, RecyclerView.ViewHolder>(GenericDiffUtil<M>()) {

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
            onBindHolder(holder.binding as T, getItem(position))
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

}