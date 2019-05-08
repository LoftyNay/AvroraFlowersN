package com.ltn.avroraflowers.ui.fragments.entryLoginFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.ui.activities.mainActivity.MainActivity
import com.ltn.avroraflowers.ui.base.BaseFragment
import com.ltn.avroraflowers.ui.fragments.entryLoginFragment.presenter.EntryLoginFragmentPresenter
import com.ltn.avroraflowers.ui.fragments.entryLoginFragment.view.EntryLoginFragmentView
import com.ltn.avroraflowers.utils.TextWatch
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
        entryLoginFragmentPresenter.attach()
        super.onViewCreated(view, savedInstanceState)

        emailEditLogin.requestFocus()
        utils.showSoftKeyboard(emailEditLogin)

        confLoginButton.setOnClickListener(this)

        emailEditLogin.addTextChangedListener(TextWatch(textInputEmailLayoutLogin))
        passwordEditLogin.addTextChangedListener(TextWatch(textInputPasswordLayoutLogin))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.confLoginButton -> {
                val email = emailEditLogin.text?.trim().toString()
                val password = passwordEditLogin.text?.trim().toString()
                entryLoginFragmentPresenter.validateUserData(email, password)
            }
        }
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
        startActivity(Intent(activity, MainActivity::class.java), null)
        activity?.finish()
    }

    override fun showEmailNotFound() {
        textInputEmailLayoutLogin.error = "Пользователя с таким Email не существует"
        textInputPasswordLayoutLogin.error = null
    }

    override fun showWrongPassword() {
        passwordEditLogin.requestFocus()
        textInputPasswordLayoutLogin.error = "Неверный пароль"
        textInputEmailLayoutLogin.error = null
    }

    override fun showConnectionProblem() {
        Toast.makeText(context?.applicationContext, "Проблемы с соединением, попробуйте позже", Toast.LENGTH_LONG)
            .show()
    }

    override fun showProgress() {
        utils.viewToFront(progressBarInLoginButton)
        progressBarInLoginButton.visibility = View.VISIBLE
        utils.hideTextAndDisableButton(confLoginButton)
    }

    override fun hideProgress() {
        progressBarInLoginButton.visibility = View.INVISIBLE
        utils.showTextAndEnableButton(confLoginButton, resources.getString(R.string.to_login_b))
    }
}