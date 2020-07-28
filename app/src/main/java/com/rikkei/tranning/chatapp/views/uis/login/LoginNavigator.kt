package com.rikkei.tranning.chatapp.views.uis.login

interface LoginNavigator {
    fun replaceFragment()
    fun login()
    fun setEnableButton()
    fun showMessageLogin(message: String?)
    fun moveIntent()
}