package com.study.app_ticket_firebase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_console.*
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.nio.charset.Charset

class ConsoleActivity : AppCompatActivity() {
    private val database = Firebase.database
    private val myRef = database.getReference("ticketsStock")
    private lateinit var userName: String
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_console)
        // 在上方 ActionBar 會顯示 <- 返回箭頭
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        context = this
        // Get userName
        userName = intent.getStringExtra("userName").toString()

        // Set activity title
        title = String.format(title.toString(), userName)

        // Read from the database
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                // 統計資料累計
                var sumAllTickets = 0
                var sumOneWay = 0
                var sumRoundTrip = 0
                var sumTotal = 0
                // -- forEach begin ---------------
                children.forEach {
                    when(it.key.toString()) {
                        resources.getString(R.string.fb_discount) -> et_discount.setText(it.value.toString())
                        resources.getString(R.string.fb_price) -> et_price.setText(it.value.toString())
                        resources.getString(R.string.fb_totalAmount) -> et_totalAmount.setText(it.value.toString())
                        "transaction" -> {
                            it.children.forEach {  // 訂購人, ex: Helen
                                it.children.forEach {  // 訂票日期, ex: 20210717093440753
                                    it.children.forEach {  // 訂票內容
                                        when(it.key.toString()) {
                                            "allTickets" -> sumAllTickets += it.value.toString().toInt()
                                            "oneWay" -> sumOneWay += it.value.toString().toInt()
                                            "roundTrip" -> sumRoundTrip += it.value.toString().toInt()
                                            "total" -> sumTotal += it.value.toString().toInt()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                // -- forEach end ---------------
                // 顯示統計資料
                tv_stat.text = "總賣票數：${ String.format("%,d", sumAllTickets) } 張\n" +
                                "總單程票：${ String.format("%,d", sumOneWay) } 張\n" +
                                "總來回票：${ String.format("%,d", sumRoundTrip) } 張\n" +
                                "總銷售額：${ String.format("%,d", sumTotal) } 元"
                // 顯示統計圖
                loadChart()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun loadChart() {
        var webSettings = web_view.settings
        webSettings.javaScriptEnabled = true
        webSettings.builtInZoomControls = true
        var asset_path = "file:///android_asset/"
        var html = getHTML("chart.html")
        html = String.format(html!!, "['AAA', 10],['bbb', 20],")
        web_view.loadDataWithBaseURL(asset_path, html!!, "text/html", "UTF-8", null)
        web_view.requestFocusFromTouch()
    }

    private fun getHTML(filename: String): String? {
        var html: String? = null
        try {
            val inputStream: InputStream = assets.open(filename)
            val out = ByteArrayOutputStream()
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer) // 資料讀出
            out.write(buffer) // 資料寫入
            html = String(out.toByteArray(), Charset.forName("UTF-8"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return html
    }

    fun update(view: View) {
        val tagName = view.tag.toString()
        var value = 0.0
        when(tagName) {
            resources.getString(R.string.fb_discount) -> value = et_discount.text.toString().toDouble()
            resources.getString(R.string.fb_price) -> value = et_price.text.toString().toDouble()
            resources.getString(R.string.fb_totalAmount) -> value = et_totalAmount.text.toString().toDouble()
        }
        // update firebase
        myRef.child(tagName).setValue(value)
        val msg = String.format(resources.getString(R.string.update_ok), tagName)
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    }
}
