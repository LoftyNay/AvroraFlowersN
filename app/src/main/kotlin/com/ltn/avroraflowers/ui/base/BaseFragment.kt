package com.ltn.avroraflowers.ui.base

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.MvpAppCompatFragment
import com.ltn.avroraflowers.adapters.ViewPagerAdapter
import com.ltn.avroraflowers.ui.activities.mainActivity.MainActivity
import kotlinx.android.synthetic.main.empty_layout.*
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar_with_search.*
import java.util.*


abstract class BaseFragment : MvpAppCompatFragment() {

    var toolbarTitle: String? = null
    lateinit var context: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflateView(inflater, container, savedInstanceState)
    }

    abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context = view.context as MainActivity

        if (toolbarSearch != null) {
            toolbarSearch.title = toolbarTitle
        }
        if (toolbarSearch != null) {
            toolbarSearch.title = toolbarTitle
        }
    }

    fun showDialog(
        title: String,
        message: String,
        positiveTitle: String,
        negativeTitle: String,
        listener: DialogListener
    ) {
        val alertDialog = AlertDialog.Builder(activity!!)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveTitle) { dialog, which ->
                listener.onPositive()
                dialog.dismiss()
            }
            .setNegativeButton(negativeTitle) { dialog, which ->
                listener.onNegative()
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }

    fun showAlert(message: String, positiveTitle: String) {
        val alertDialog = AlertDialog.Builder(activity!!)
            .setMessage(message)
            .setPositiveButton(positiveTitle) { dialog, which -> dialog?.dismiss() }
            .create()
        alertDialog.show()
    }

    fun showDatePicker(listener: DatePickerDialog.OnDateSetListener) {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val datePickerDialog = DatePickerDialog(context, listener, year, month, day)
        calendar.add(Calendar.DAY_OF_MONTH, 5)  //дата отгрузки минимум через 5 дней
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    fun showEmptyBlock(text: String, listener: View.OnClickListener) {
        if (emptyBlock != null) {
            tvEmpty.text = text
            emptyBlock.visibility = View.VISIBLE
            buttonEmpty.setOnClickListener(listener)
        }
    }

    fun hideEmptyBlock() {
        if (emptyBlock != null) {
            emptyBlock.visibility = View.GONE
        }
    }

    interface DialogListener {
        fun onPositive()
        fun onNegative()
    }

    interface EmptyListener {
        fun onShowEmpty()
        fun onHideEmpty()
    }
}