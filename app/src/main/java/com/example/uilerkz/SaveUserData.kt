package com.example.uilerkz

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column

@Composable
fun SaveUserData() {
    val firestore = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    val userData = hashMapOf(
        "email" to currentUser?.email,
        "sub_type" to "premium"
    )

    Button(onClick = {
        if (currentUser != null) {
            firestore.collection("users")
                .document(currentUser.uid)
                .set(userData)
                .addOnSuccessListener {
                    println("User data saved!")
                }
                .addOnFailureListener { e ->
                    println("Error storing user data: ${e.message}")
                }
        }
    }) {
        Text(text = "Save User Data")
    }
}
