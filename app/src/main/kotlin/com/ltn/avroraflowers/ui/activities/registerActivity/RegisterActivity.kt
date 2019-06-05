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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


class RegisterActivity : BaseActivity(), RegisterActivityView, View.OnClickListener {

    override fun resultOk(message: String) {}

    @InjectPresenter
    lateinit var registerActivityPresenter: RegisterActivityPresenter

    @Inject
    lateinit var utils: Utils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        registerActivityPresenter.attach()

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
        textInputEmailLayoutRegister.error = resources.getString(R.string.user_exist_msg)
        textInputEmailLayoutRegister.requestFocus()
    }

    override fun showWrongInputErrorMessage(v: View) {
        when(v.id) {
            R.id.textInputNameLayoutRegister -> {
                textInputNameLayoutRegister.error = resources.getString(R.string.name_validation_msg)
                textInputNameLayoutRegister.requestFocus()
            }
            R.id.textInputEmailLayoutRegister -> {
                textInputEmailLayoutRegister.error = resources.getString(R.string.email_validation_msg)
                textInputEmailLayoutRegister.requestFocus()
            }
            R.id.textInputPasswordLayoutRegister -> {
                textInputPasswordLayoutRegister.error = resources.getString(R.string.password_validation_msg)
                textInputPasswordLayoutRegister.requestFocus()
            }
        }
    }

    override fun showConnectionProblem() {
        Toast.makeText(applicationContext, resources.getString(R.string.error_connection), Toast.LENGTH_LONG).show()
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