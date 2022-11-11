package com.mukesh.template.utils

import android.app.AlertDialog
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*


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