package com.example.uilerkz

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavHostController
import androidx.navigation.Navigation.findNavController
import com.example.uilerkz.ui.theme.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AuthViewModel : ViewModel() {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus(): Boolean{
        if(auth.currentUser==null){
            _authState.value = AuthState.Unauthenticated
            return false

        }else{
            _authState.value = AuthState.Authenticated
            return true
        }
    }

    fun getUserEmail(): String? {
        return auth.currentUser?.email
    }

    fun login(email: String, password: String, navController: NavHostController){
        if (email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or password cannot be empty")
        }
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{task ->
            if (task.isSuccessful){
                _authState.value = AuthState.Authenticated
                createFavoritesDocumentIfNeeded()
                navController.navigate(Screen.Menu.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                    launchSingleTop = true
                }
            }else{
                _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
            }

        }
    }
    fun signUp(email: String, password: String, navController: NavHostController){
        if (email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or password cannot be empty")
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                    createFavoritesDocumentIfNeeded()
                    navController.navigate(Screen.Menu.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }else{
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }

            }
    }
    fun signOut(){
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }
    fun createFavoritesDocumentIfNeeded() {
        // Get the current user from Firebase Auth
        val currentUser = FirebaseAuth.getInstance().currentUser

        // Check if the user is authenticated
        if (currentUser != null) {
            // Get the reference to the Firestore user document
            val userDocRef = FirebaseFirestore.getInstance().collection("users").document(currentUser.uid)

            // Check if the user's document exists
            userDocRef.get()
                .addOnSuccessListener { document ->
                    if (!document.exists()) {
                        // If the document doesn't exist, create it with an empty 'favorites' list
                        userDocRef.set(
                            mapOf(
                                "favorites" to emptyList<Map<String, String>>()  // Initialize 'favorites' as an empty list
                            )
                        )
                            .addOnSuccessListener {
                                // Successfully created the document with the 'favorites' field
                                Log.d("pricing", "User document created with empty 'favorites'.")
                            }
                            .addOnFailureListener { e ->
                                // Handle failure to create the document
                                Log.e("pricing", "Error creating user document: ${e.message}")
                            }
                    } else {
                        // Document already exists, so no need to create it
                        Log.d("pricing", "User document already exists.")
                    }
                }
                .addOnFailureListener { e ->
                    // Handle failure to check if the document exists
                    Log.e("pricing", "Error checking user document: ${e.message}")
                }
        } else {
            // Handle the case where the user is not logged in
            Log.e("pricing", "User is not authenticated.")
        }
    }

}

sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message : String) : AuthState()
}

