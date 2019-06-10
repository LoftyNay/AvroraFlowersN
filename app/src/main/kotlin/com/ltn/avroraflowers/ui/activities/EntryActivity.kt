package com.ltn.avroraflowers.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.network.ApiAvrora
import com.ltn.avroraflowers.ui.dialogs.InfoDialog
import com.ltn.avroraflowers.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.contacts_about_buttons_layout.*
import javax.inject.Inject

class EntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        contactsMainButton.setOnClickListener {
            InfoDialog.newInstanceContacts("Контакты").show(supportFragmentManager, InfoDialog.TAG)
        }
        aboutMainButton.setOnClickListener {
            InfoDialog.newInstanceAbout("О компании").show(supportFragmentManager, InfoDialog.TAG)
        }
    }
}