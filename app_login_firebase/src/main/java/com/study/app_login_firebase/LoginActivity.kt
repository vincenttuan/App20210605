package com.study.app_login_firebase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context = this
        auth = Firebase.auth

    }

    fun signIn(view: View) {
        val email = et_email.text.toString()
        val password = et_password.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                //task.result
                if (task.isSuccessful) {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(context, "Login fail !", Toast.LENGTH_SHORT).show()
                }
            }
    }
}