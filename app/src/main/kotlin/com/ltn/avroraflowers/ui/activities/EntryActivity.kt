package com.ltn.avroraflowers.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.network.ApiAvrora
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EntryActivity : AppCompatActivity() {

    @Inject
    lateinit var apiAvrora: ApiAvrora


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        App.component.inject(this)

        val disposable = apiAvrora.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    for (category in result.listCategories) {
                        Log.d("CAT", category.title)
                        //Toast.makeText(applicationContext, category.title, Toast.LENGTH_SHORT).show()
                    }
                },
                { error -> Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show() }
            )
    }
}