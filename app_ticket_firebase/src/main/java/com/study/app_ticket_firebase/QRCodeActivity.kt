package com.study.app_ticket_firebase

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.study.app_ticket_firebase.models.Order
import kotlinx.android.synthetic.main.activity_qrcode.*
import java.text.SimpleDateFormat
import java.util.*

class QRCodeActivity : AppCompatActivity() {
    private val database = Firebase.database
    private val myRef = database.getReference("ticketsStock")
    private val PERMISSION_REQUEST_CODE = 200
    private lateinit var context: Context
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)
        context = this

        // check permission
        if (checkPermission()) {
            // 執行ORCode程式
            runProgram()
        } else {
            // 啟動動態核准對話框
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    // 取得使用者在動態核准對話框的決定
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            Toast.makeText(context, grantResults[0].toString(), Toast.LENGTH_SHORT).show()
            // 執行ORCode程式
            // grantResults[0] = 0 -> Accept
            // grantResults[0] = -1 -> Deny
            if (grantResults[0] == 0) {
                runProgram()
            } else if (grantResults[0] == -1) {
                finish()
            }

        }
    }

    // 執行ORCode程式
    fun runProgram() {
        title = "執行ORCode程式..."
        codeScanner = CodeScanner(context, scanner_view)
        // 設定 codescanner 參數
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        // 解碼 Callback
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                val result_text = it.text // 解碼內容
                AlertDialog.Builder(context)
                    .setTitle("QRCode 內容")
                    .setMessage("$result_text")
                    .setPositiveButton("使用", { dialogInterface, i ->
                        val order = Gson().fromJson<Order>(result_text, Order::class.java)
                        // 取得該票的路徑
                        val path = order.ticket.userName + "/" + order.key
                        val transPath = "transaction/$path"
                        // 刪除該票
                        myRef.child(transPath).removeValue()
                        // 建立 transaction_history
                        val ts = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()).toString()
                        myRef.child("transaction_history/$path/ts").setValue(ts)
                        myRef.child("transaction_history/$path/key").setValue(order.key)
                        myRef.child("transaction_history/$path/userName").setValue(order.ticket.userName)
                        myRef.child("transaction_history/$path/allTickets").setValue(order.ticket.allTickets)
                        myRef.child("transaction_history/$path/oneWay").setValue(order.ticket.oneWay)
                        myRef.child("transaction_history/$path/roundTrip").setValue(order.ticket.roundTrip)
                        myRef.child("transaction_history/$path/total").setValue(order.ticket.total)
                        myRef.child("transaction_history/$path/json").setValue(result_text)
                        // 結束
                        finish()
                    })
                    .setNegativeButton("取消", { dialogInterface, i ->
                        // 結束
                        finish()
                    })
                    .create()
                    .show()
            }
        }

        // 解碼錯誤
        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
                val message_text = it.message // 解碼內容
                AlertDialog.Builder(context)
                    .setTitle("錯誤內容")
                    .setMessage("$message_text")
                    .setNegativeButton("取消", { dialogInterface, i ->
                        finish()
                    })
                    .create()
                    .show()
            }
        }

        // 執行
        codeScanner.startPreview()
    }

    override fun onStop() {
        super.onStop()
        try {
            codeScanner.releaseResources()
        } catch (e: Exception) {

        }
    }

    // 使用者是否有同意使用權限(Ex:Camera)
    private fun checkPermission(): Boolean {
        val check = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        val result = (check == PackageManager.PERMISSION_GRANTED)
        if (result) {
            Toast.makeText(context, "Permission is OK", Toast.LENGTH_SHORT).show()
            return true;
        } else {
            Toast.makeText(context, "Permission is not granted", Toast.LENGTH_SHORT).show()
            return false;
        }
    }
}