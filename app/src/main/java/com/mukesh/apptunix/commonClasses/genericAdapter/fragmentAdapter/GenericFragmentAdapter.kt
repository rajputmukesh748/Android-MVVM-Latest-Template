package com.mukesh.apptunix.commonClasses.genericAdapter.fragmentAdapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Generic Fragment Adapter
 * */
class GenericFragmentAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    private val fragmentList by lazy { ArrayList<Fragment>() }

    /**
     * Get Item Count
     * */
    override fun getItemCount() = fragmentList.size


    /**
     * Create Fragment
     * */
    override fun createFragment(position: Int): Fragment {
        return if (fragmentList.size > 0){
            fragmentList[position]
        }else
            null!!
    }


    /**
     * Submit List
     * */
    fun submitList(list: List<Fragment>){
        fragmentList.clear()
        fragmentList.addAll(list)
        if (list.isNotEmpty())
            notifyItemRangeInserted(0, list.size - 1)
    }

}
