package com.ltn.avroraflowers.ui.base

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.ui.activities.mainActivity.MainActivity
import com.ltn.avroraflowers.ui.fragments.searchFragment.HostSearch
import com.ltn.avroraflowers.ui.fragments.searchFragment.SearchFragment
import kotlinx.android.synthetic.main.empty_layout.*
import kotlinx.android.synthetic.main.fragment_products_inner.*
import kotlinx.android.synthetic.main.need_autorization_layout.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar_with_search.*
import java.util.*


abstract class BaseFragment : BaseLoginFragment(), BaseView, IConnectionProblem {

    var toolbarTitle: String? = null
    lateinit var mContext: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflateView(inflater, container, savedInstanceState)
    }

    abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity is MainActivity) {
            mContext = activity as MainActivity
        }

        if (toolbarSearch != null) {
            toolbarSearch.title = toolbarTitle
            cardSearchEdit.setOnClickListener {
                HostSearch.getInstance().show(fragmentManager!!, SearchFragment.TAG)
            }
        }

        if (toolbar != null) {
            toolbar.title = toolbarTitle
        }
    }

    override fun resultOk(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showConnectionProblem() {
        Toast.makeText(activity, "Ошибка соединения, попробуйте позже", Toast.LENGTH_LONG).show()
    }

    fun setNavigationIcon(toolbar: View, title: String) {
        (toolbar as Toolbar).title = title
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        toolbar.setNavigationOnClickListener {
            parentFragment?.childFragmentManager?.popBackStack()
        }
    }

//TODO TO MESSAGE BASE FRAGMENT!!!

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

    fun showNeedAutorizationBlock(message: Int, listener: View.OnClickListener) {
        if (needAutorizationBlock != null) {
            needAutorizationBlock.visibility = View.VISIBLE
            tvNeedAutorization.text = getString(message)
            buttonNeedAutorization.setOnClickListener(listener)
        }
    }

    fun hideNeedAutorizationBlock() {
        refreshUserStatus(false)
        if (needAutorizationBlock != null) {
            needAutorizationBlock.visibility = View.GONE
        }
    }

    fun showEmptyBlock(text: String, textButton: String, listener: View.OnClickListener) {
        if (emptyBlock != null) {
            tvEmpty.text = text
            emptyBlock.visibility = View.VISIBLE
            buttonEmpty.text = textButton
            buttonEmpty.setOnClickListener(listener)
        }
    }

    fun hideEmptyBlock() {
        if (emptyBlock != null) {
            emptyBlock.visibility = View.GONE
        }
    }

    override fun userLogin(status: Boolean) {}

    override fun connectionProblem() {
        Toast.makeText(activity, "Ошибка соединения, попробуйте позже", Toast.LENGTH_LONG).show()
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