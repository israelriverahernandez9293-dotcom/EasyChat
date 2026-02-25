package com.easychat.data

import com.easychat.domain.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun register(email: String, phone: String, password: String): AuthResult {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user
                ?: return AuthResult.Error("No fue posible crear el usuario")

            user.sendEmailVerification().await()

            val profile = UserProfile(
                uid = user.uid,
                email = email,
                phone = phone,
                emailVerified = user.isEmailVerified
            )

            firestore.collection("users").document(user.uid).set(profile).await()
            AuthResult.Success
        } catch (error: Exception) {
            AuthResult.Error(error.message ?: "Ocurrió un error inesperado")
        }
    }

    suspend fun login(email: String, password: String): AuthResult {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val user = authResult.user
                ?: return AuthResult.Error("Usuario no encontrado")

            user.reload().await()
            if (!user.isEmailVerified) {
                user.sendEmailVerification().await()
                auth.signOut()
                return AuthResult.Error("Verifica tu correo antes de ingresar. Enviamos un nuevo correo de confirmación.")
            }

            firestore.collection("users").document(user.uid)
                .update("emailVerified", true)
                .await()

            AuthResult.Success
        } catch (error: Exception) {
            AuthResult.Error(error.message ?: "No se pudo iniciar sesión")
        }
    }
}
