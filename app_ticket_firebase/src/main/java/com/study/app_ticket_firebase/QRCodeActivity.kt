package com.study.app_ticket_firebase

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class QRCodeActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CODE = 200
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)
        context = this

        // check permission
        if(checkPermission()) {
            // 執行ORCode程式
            runProgram()
        } else {
            // 啟動動態核准對話框
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA),
                PERMISSION_REQUEST_CODE)
        }
    }

    // 取得使用者在動態核准對話框的決定
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_REQUEST_CODE) {
            // 執行ORCode程式
            runProgram()
        }
    }

    // 執行ORCode程式
    fun runProgram() {
        title = "執行ORCode程式..."
    }

    // 使用者是否有同意使用權限(Ex:Camera)
    private fun checkPermission(): Boolean {
        val check = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        val result = (check == PackageManager.PERMISSION_GRANTED)
        if(result) {
            Toast.makeText(context, "Permission is OK", Toast.LENGTH_SHORT).show()
            return true;
        } else {
            Toast.makeText(context, "Permission is not granted", Toast.LENGTH_SHORT).show()
            return false;
        }
    }
}