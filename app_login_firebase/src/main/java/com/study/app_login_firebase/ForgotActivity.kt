package com.study.app_login_firebase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot.*

class ForgotActivity : AppCompatActivity() {
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)
        context = this
    }

    fun forgot(view: View) {
        val email = et_forgot_email.text.toString()
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Toast.makeText(context, "Emial send OK", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, ResultActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(context, "Emial send FAIL", Toast.LENGTH_SHORT).show()
                }
            }

    }
}