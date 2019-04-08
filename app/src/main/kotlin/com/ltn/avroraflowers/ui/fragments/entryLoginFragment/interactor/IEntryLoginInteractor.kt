package com.ltn.avroraflowers.ui.fragments.entryLoginFragment.interactor

interface IEntryLoginInteractor {
    fun checkUserDataFromServer(email: String, password: String, onCheckUserDataFromServer: OnCheckUserDataFromServer)
}