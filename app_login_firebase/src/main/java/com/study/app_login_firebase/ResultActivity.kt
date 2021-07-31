package com.study.app_login_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        auth = Firebase.auth

        val message = intent.getStringExtra("message")
        val action = intent.getStringExtra("action")
        if(message != null) {
            tv_result.setText(message)
        }
        if(action != null && action.equals("emailVerify")) {
            bt_action.visibility = View.VISIBLE
        }
    }

    fun actionClick(view: View) {
        auth.currentUser?.sendEmailVerification()
        val intent = Intent(this, ResultActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun back(view: View) {
        finish()
    }

}