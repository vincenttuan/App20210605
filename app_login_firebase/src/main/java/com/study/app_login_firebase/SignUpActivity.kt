package com.study.app_login_firebase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        context = this;
        auth = Firebase.auth
    }

    fun signUp(view: View) {
        val email = et_signup_email.text.toString()
        val password = et_signup_password.text.toString()
        var message: String? = ""
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    message = "Sign up success"
                } else {
                    message = "Sign up fail: ${task.result}";
                }
                val intent = Intent(context, ResultActivity::class.java)
                intent.putExtra("message", message)
                startActivity(intent)
                finish()
            }

    }
}