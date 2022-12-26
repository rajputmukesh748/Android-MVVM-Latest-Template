package com.mukesh.template.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.R
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*


/**
 * Show Alert Dialog
 * */
fun Activity.showDialog(
    title: String,
    message: String,
    positiveString: String = "",
    negativeString: String = "",
    callBack: () -> Unit
) {
    MaterialAlertDialogBuilder(this).apply {
        setCancelable(false)
        setFinishOnTouchOutside(false)
        setTitle(title)
        setMessage(message)
        setPositiveButton(positiveString) { dialog, _ ->
            dialog.dismiss()
            callBack()
        }
        setNegativeButton(negativeString) { dialog, _ ->
            dialog.dismiss()
        }
        create()
        show()
    }
}


/**
 * Show Error Handler
 * */
fun Activity.showExitSnackBar() {
    try {
        Snackbar.make(
            findViewById(android.R.id.content),
            getString(STRING_ALIAS.are_you_sure_want_to_exit),
            Snackbar.LENGTH_LONG
        ).apply {
            setBackgroundTint(ContextCompat.getColor(this@showExitSnackBar, COLOR_ALIAS.black))
            animationMode = Snackbar.ANIMATION_MODE_SLIDE
            setTextColor(ContextCompat.getColor(this@showExitSnackBar, COLOR_ALIAS.white))
            view.findViewById<TextView>(R.id.snackbar_text).maxLines = 5
            setAction(STRING_ALIAS.yes) { finishAffinity() }
            show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


/**
 * Generic Alert Dialog with View Binding
 * */
fun <T : ViewBinding> Context.genericAlertDialog(
    viewBinding: ViewBinding,
    returnBinding: (T, AlertDialog) -> Unit
) {
    try {
        AlertDialog.Builder(this).apply {
            setView(viewBinding.root)
            val dialog = create()
            @Suppress("UNCHECKED_CAST")
            returnBinding(viewBinding as T, dialog)
            dialog.show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


/**
 * Date Picker
 * */
fun Fragment.datePicker(
    startDate: Boolean = false,
    endDate: Boolean = false,
    callBack: (date: String) -> Unit
) = try {
    MaterialDatePicker
        .Builder
        .datePicker()
        .setTitleText(getString(STRING_ALIAS.select_date))
        .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
        .setCalendarConstraints(
            CalendarConstraints.Builder().apply {
                setOpenAt(Calendar.getInstance().timeInMillis)
                if (startDate) {
                    setValidator(DateValidatorPointForward.now())
                    setStart(Calendar.getInstance().timeInMillis)
                }
                if (endDate) {
                    setValidator(DateValidatorPointBackward.now())
                    setEnd(Calendar.getInstance().timeInMillis)
                }
            }.build()
        ).build().apply {
            addOnPositiveButtonClickListener {
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                callBack(sdf.format(it))
            }
        }.show(childFragmentManager, getString(STRING_ALIAS.select_date))
} catch (e: Exception) {
    e.printStackTrace()
}


/**
 * Time Picker
 * */
fun Fragment.timePicker(callBack: (hours: Int, minute: Int) -> Unit) = try {
    MaterialTimePicker
        .Builder().apply {
            setTitleText(getString(STRING_ALIAS.select_time))
            setTimeFormat(TimeFormat.CLOCK_12H)
            setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
        }.build().apply {
            addOnPositiveButtonClickListener {
                callBack(hour, minute)
            }
        }.show(childFragmentManager, getString(STRING_ALIAS.select_time))
} catch (e: Exception) {
    e.printStackTrace()
}