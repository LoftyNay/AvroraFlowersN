package com.ltn.avroraflowers.ui.activities.registerActivity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.ui.activities.registerActivity.presenter.RegisterActivityPresenter
import com.ltn.avroraflowers.ui.activities.registerActivity.view.RegisterActivityView
import com.ltn.avroraflowers.ui.base.BaseActivity
import com.ltn.avroraflowers.utils.TextWatch
import com.ltn.avroraflowers.utils.Utils
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


class RegisterActivity : BaseActivity(), RegisterActivityView, View.OnClickListener {

    @InjectPresenter
    lateinit var registerActivityPresenter: RegisterActivityPresenter

    @Inject
    lateinit var utils: Utils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        registerActivityPresenter.attach(this)

        setContentView(R.layout.activity_register)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        textInputNameLayoutRegister.requestFocus()
        confRegisterButton.setOnClickListener(this)

        nameEditRegister.addTextChangedListener(TextWatch(textInputNameLayoutRegister))
        passwordEditRegister.addTextChangedListener(TextWatch(textInputPasswordLayoutRegister))
        emailEditRegister.addTextChangedListener(TextWatch(textInputEmailLayoutRegister))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.confRegisterButton -> {
                registerActivityPresenter.toRegisterUser(
                    nameEditRegister,
                    emailEditRegister,
                    passwordEditRegister
                )
            }
        }
    }

    override fun showUserRegisteredOk() {
        Toast.makeText(applicationContext, "Успешная регистрация", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun showUserExist() {
        textInputEmailLayoutRegister.requestFocus()
        textInputEmailLayoutRegister.error = resources.getString(R.string.user_exist_msg)
    }

    override fun showConnectionProblem() {
        Toast.makeText(applicationContext, "Проблемы с соединением, попробуйте позже", Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        utils.viewToFront(confRegisterButton)
        progressBarInRegisterButton.visibility = View.VISIBLE
        utils.hideTextAndDisableButton(confRegisterButton)
    }

    override fun hideProgress() {
        progressBarInRegisterButton.visibility = View.INVISIBLE
        utils.showTextAndEnableButton(confRegisterButton, resources.getString(R.string.to_register_b))
    }
}