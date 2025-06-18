package com.example.scoopie.repository

import androidx.compose.runtime.referentialEqualityPolicy
import com.example.scoopie.model.UserModel
import com.example.scoopie.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class UserRepositoryImpl: UserRepository {
    val auth : FirebaseAuth = FirebaseAuth.getInstance()

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()

    val ref = database.reference.child("users")

    override fun login(
        email: String,
        password: String,
        callback: (Boolean, String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun register(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true,"Registered successfully",
                        "${auth.currentUser?.uid}")
                } else{
                    callback(false,"${it.exception?.message}",
                        "")

                }
            }
    }

    override fun addUserToDatabase(
        userId: String,
        model: UserModel,
        callback: (Boolean, String) -> Unit
    ) {

    }

    override fun updateProfile(
        userId: String,
        data: MutableMap<String, Any?>,
        callback: (Boolean, String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun forgetPassword(
        email: String,
        callback: (Boolean, String) -> Unit
    ) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true,"Reset email sent to the $email")
                } else{
                    callback(false,"${it.exception?.message}")

                }
            }
    }
    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun getUserById(
        userId: String,
        callback: (UserModel?, Boolean, String) -> Unit
    ) {
        ref.child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val users = snapshot.getValue(UserModel::class.java)
                    if (users != null) {
                        callback(users, true, "data fetched")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null,false,error.message)
            }
        })
    }

    override fun logout(callback: (Boolean, String) -> Unit) {
        try {
            auth.signOut()
            callback(true,"logout successfully")
        }catch (e: Exception){
            callback(false,"${e.message}")

        }
    }
}
