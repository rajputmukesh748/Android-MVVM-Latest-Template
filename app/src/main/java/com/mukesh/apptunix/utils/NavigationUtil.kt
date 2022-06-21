package com.mukesh.apptunix.utils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


/**
 * Navigate between fragments using ID
 * */
fun View.navigateId(destinationId: Int, bundle: Bundle? = null){
    try {
        findNavController().navigate(destinationId, bundle)
    }catch (e:Exception){
        e.printStackTrace()
    }
}


/**
 * Navigate between fragments using Direction Classes
 * */
fun View.navigateDirection(direction: NavDirections){
    try {
        findNavController().navigate(direction)
    }catch (e:Exception){
        e.printStackTrace()
    }
}


/**
 * Navigate BottomSheetDialogFragment to fragments using Direction Classes
 * */
fun BottomSheetDialogFragment.navigateActionDialog(action: NavDirections) = try {
    requireParentFragment().findNavController().navigate(action)
} catch (e: Exception) {
    e.printStackTrace()
}


/**
 * Navigate DialogFragment to fragments using Direction Classes
 * */
fun DialogFragment.navigateActionDialogFragment(action: NavDirections) = try {
    requireParentFragment().findNavController().navigate(action)
} catch (e: Exception) {
    e.printStackTrace()
}


/**
 * Navigate DialogFragment to fragments using Direction Classes with Transitions
 * */
fun DialogFragment.navigateActionDialogTransition(
    action: NavDirections,
    extra: FragmentNavigator.Extras
) = try {
    requireParentFragment().findNavController().navigate(action, extra)
} catch (e: Exception) {
    e.printStackTrace()
}


/**
 * Navigate between fragments using ID with Transition
 * */
fun View.navigateIDTransition(id: Int, bundle: Bundle?, extra: FragmentNavigator.Extras) = try {
    findNavController().navigate(id, bundle, null, extra)
} catch (e: Exception) {
    e.printStackTrace()
}


/**
 * Navigate between fragments using Direction Classes with Transition
 * */
fun View.navigateActionTransition(action: NavDirections, extra: FragmentNavigator.Extras) = try {
    findNavController().navigate(action, extra)
} catch (e: Exception) {
    e.printStackTrace()
}


/**
 * Go to previous fragment
 * */
fun View.navigateBack(){
    try {
        findNavController().popBackStack()
    }catch (e:Exception){
        e.printStackTrace()
    }
}


/**
 * Go to previous fragment or need to pop anu fragment then pass those ID
 * */
fun View.navigateBack(navigationId: Int){
    try {
        findNavController().popBackStack(navigationId, true)
    }catch (e:Exception){
        e.printStackTrace()
    }
}
