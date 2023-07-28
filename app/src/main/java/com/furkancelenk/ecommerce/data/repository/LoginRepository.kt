package com.furkancelenk.ecommerce.data.repository

import androidx.lifecycle.MutableLiveData
import com.furkancelenk.ecommerce.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    val isCurrentUserExist = MutableLiveData<Boolean>()
    val isLogin = MutableLiveData<Pair<Boolean, String>>()
    val isRegister = MutableLiveData<Pair<Boolean, String>>()
    val user = MutableLiveData<User>()

    private fun getFirebaseUserUid(): String = firebaseAuth.currentUser?.uid.orEmpty()

    fun registerWithEmailAndPassword(
        user: User,
        password: String,
    ) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, password).addOnSuccessListener {
            val userModel = hashMapOf(
                "id" to getFirebaseUserUid(),
                "email" to user.email,
                "nickname" to user.nickname,
                "phone_number" to user.phoneNumber
            )

            firebaseFirestore.collection("users").document(getFirebaseUserUid()).set(userModel)
                .addOnSuccessListener {
                    isRegister.value = Pair(true, "Registration successful")
                }.addOnFailureListener {
                    isRegister.value = Pair(false, it.message.orEmpty())
                }
        }.addOnFailureListener {
            isRegister.value = Pair(false, it.message.orEmpty())
        }
    }

    fun loginWithEmailAndPassword(
        email: String,
        password: String,
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            isLogin.value = Pair(true, "Login successful")
        }.addOnFailureListener {
            isLogin.value = Pair(false, it.message.orEmpty())
        }
    }

    fun getCurrentUser() {
        firebaseFirestore.collection("users").document(getFirebaseUserUid()).get()
            .addOnSuccessListener {
                user.value = User(
                    it["email"] as String,
                    it["nickname"] as String,
                    it["phone_number"] as String,
                )
            }.addOnFailureListener {

        }
    }

    fun checkCurrentUser() {
        isCurrentUserExist.value = firebaseAuth.currentUser != null
    }

    fun signOut() = firebaseAuth.signOut()

}