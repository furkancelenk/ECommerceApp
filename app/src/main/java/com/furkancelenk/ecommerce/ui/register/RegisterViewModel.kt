package com.furkancelenk.ecommerce.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furkancelenk.ecommerce.data.model.User
import com.furkancelenk.ecommerce.data.repository.LoginRepository

class RegisterViewModel : ViewModel() {

    private val loginRepository = LoginRepository()

    private var _isRegister = MutableLiveData<Pair<Boolean, String>>()
    val isRegister: LiveData<Pair<Boolean, String>>
        get() = _isRegister

    init {
        _isRegister = loginRepository.isRegister
    }

    fun register(user: User, password: String) {
        loginRepository.registerWithEmailAndPassword(user, password)
    }
}