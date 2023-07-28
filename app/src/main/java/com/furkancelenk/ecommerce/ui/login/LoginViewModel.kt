package com.furkancelenk.ecommerce.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furkancelenk.ecommerce.data.repository.LoginRepository

class LoginViewModel : ViewModel() {

    private val loginRepository = LoginRepository()

    private var _isLogin = MutableLiveData<Pair<Boolean, String>>()
    val isLogin: LiveData<Pair<Boolean, String>>
        get() = _isLogin

    init {
        _isLogin = loginRepository.isLogin
    }

    fun login(email: String, password: String) {
        loginRepository.loginWithEmailAndPassword(email, password)
    }
}

