package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "Авторизация"
        }

        val btn: Button? = findViewById(R.id.btn_enter)

        btn?.setOnClickListener {
            var ti = findViewById<TextInputLayout>(R.id.input_login)
            val login = ti.editText!!.text.toString()
            ti = findViewById(R.id.input_password)
            val pass = ti.editText!!.text.toString()
            val intent = Intent()
            if (login == "user2" && pass == "123") {
                intent.putExtra("profile", "user2")
                setResult(RESULT_OK, intent)
                finish()
            } else if (login == "user1" && pass == "123") {
                intent.putExtra("profile", "user1")
                setResult(RESULT_OK, intent)
                finish()
            } else {
                val tv = findViewById<TextView>(R.id.login_warn)
                tv.text = "Неверный логин или пароль"
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}