package com.furkancelenk.ecommerce.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furkancelenk.ecommerce.data.model.User
import com.furkancelenk.ecommerce.data.repository.LoginRepository

class ProfileViewModel : ViewModel() {

    private val loginRepository = LoginRepository()

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        loginRepository.getCurrentUser()
        _user = loginRepository.user
    }

    fun signOut() = loginRepository.signOut()
}