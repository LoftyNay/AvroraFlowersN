package com.ltn.avroraflowers.ui.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.ltn.avroraflowers.ui.activities.mainActivity.MainActivity

abstract class BaseDialogFragment: DialogFragment() {

    lateinit var mContext: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity is MainActivity) {
            mContext = activity as MainActivity
        }
    }
}