//package com.example.uilerkz
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.navigation.NavHostController
//import com.google.firebase.auth.FirebaseAuth
//import kotlin.math.atanh
//
//class AuthManager(navController: NavHostController) {
//    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
//
//    private val _authState = MutableLiveData<AuthState>()
//    val authState: LiveData<AuthState> = _authState
//
//    init {
//        checkAuthStatus()
//    }
//
//    fun checkAuthStatus() {
//        if(auth.currentUser==null){
//            _authState.value = AuthState.Unauthenticated
//        }else{
//            _authState.value = AuthState.Authenticated
//        }
//    }
//
//    fun login(email: String, password: String,navController: NavHostController){
//        if (email.isEmpty() || password.isEmpty()){
//            _authState.value = AuthState.Error("Email or password cannot be empty")
//        }
//        _authState.value = AuthState.Loading
//        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{task ->
//            if (task.isSuccessful){
//                _authState.value = AuthState.Authenticated
//                navController.navigate("menu")
//            }else{
//                _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
//            }
//
//        }
//    }
//    fun signUp(email: String, password: String,firstName: String, lastName: String, navController: NavHostController){
//        if (email.isEmpty() || password.isEmpty()){
//            _authState.value = AuthState.Error("Email or password cannot be empty")
//        }
//        _authState.value = AuthState.Loading
//        auth.createUserWithEmailAndPassword(email,password)
//            .addOnCompleteListener{task ->
//            if (task.isSuccessful){
//                _authState.value = AuthState.Authenticated
//                navController.navigate("menu")
//            }else{
//                _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
//            }
//
//        }
//    }
//    fun signOut(navController: NavHostController){
//        auth.signOut()
//        _authState.value = AuthState.Unauthenticated
//        navController.navigate("login")
//    }
//}
//
//sealed class AuthState{
//    object Authenticated : AuthState()
//    object Unauthenticated : AuthState()
//    object Loading : AuthState()
//    data class Error(val message : String) : AuthState()
//}