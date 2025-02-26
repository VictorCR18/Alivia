package com.example.alivia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _user = MutableLiveData<FirebaseUser?>(auth.currentUser)
    val user: LiveData<FirebaseUser?> = _user

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String) {
        _isLoading.value = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    _user.value = auth.currentUser
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = task.exception?.message
                }
            }
    }

    fun signUp(email: String, password: String, name: String) {
        _isLoading.value = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    // Atualiza o perfil com o nome do usuário
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()
                    user?.updateProfile(profileUpdates)
                    _user.value = user
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = task.exception?.message
                }
            }
    }
}
