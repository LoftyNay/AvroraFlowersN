package com.ltn.avroraflowers.ui.fragments.entryLoginFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.arellomobile.mvp.presenter.InjectPresenter
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.ui.activities.MainActivity
import com.ltn.avroraflowers.ui.base.BaseFragment
import com.ltn.avroraflowers.ui.base.BaseView
import com.ltn.avroraflowers.ui.fragments.entryLoginFragment.presenter.EntryLoginFragmentPresenter
import com.ltn.avroraflowers.ui.fragments.entryLoginFragment.view.EntryLoginFragmentView
import com.ltn.avroraflowers.utils.Constants
import com.ltn.avroraflowers.utils.Utils
import kotlinx.android.synthetic.main.fragment_entry_login.*
import javax.inject.Inject

class EntryLoginFragment : BaseFragment(), EntryLoginFragmentView, View.OnClickListener {

    @InjectPresenter
    lateinit var entryLoginFragmentPresenter: EntryLoginFragmentPresenter

    @Inject
    lateinit var utils: Utils

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_entry_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        entryLoginFragmentPresenter.attach(view.context)
        super.onViewCreated(view, savedInstanceState)

        emailEditLogin.requestFocus()
        utils.showSoftKeyboard(emailEditLogin)

        confLoginButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.confLoginButton -> {
                val email = emailEditLogin.text?.trim().toString()
                val password = passwordEditLogin.text?.trim().toString()
                entryLoginFragmentPresenter.validateUserData(email, password)
            }
        }
    }

    override fun onAttach(context: Context) {
        App.component.inject(this)
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        entryLoginFragmentPresenter.detach()
    }

    override fun onDestroy() {
        super.onDestroy()
        entryLoginFragmentPresenter.destroy()
    }

    override fun userDataValidationOk() {
        startActivity(Intent(activity, MainActivity::class.java))
        Log.d(Constants.GLOBAL_LOG, "OK")
    }

    override fun showEmailNotFound() {
        Log.d(Constants.GLOBAL_LOG, "EMAIL NOT FOUND")
        textInputEmailLayoutLogin.error = "Пользователя с таким Email не существует"
    }

    //FIXME
    override fun showWrongPassword() {
        Log.d(Constants.GLOBAL_LOG, "WRONG PASS")
        textInputPasswordLayoutLogin.error = "Неверный пароль"
    }


    override fun showProgress() {
    }

    override fun hideProgress() {
    }
}