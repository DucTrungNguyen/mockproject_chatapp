package com.rikkei.tranning.chatapp.views.uis

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.rikkei.tranning.chatapp.R
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    var toolbar: Toolbar? = null
    var btnMoveLogin: Button? = null
    var checkBox: CheckBox? = null
    var edtName: EditText? = null
    var edtEmail:EditText? = null
    var edtPass:EditText? = null
    var btnSignUp: Button? = null

    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        init()
        mAuth = FirebaseAuth.getInstance()
        EnableButton()
        btnMoveLogin!!.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        btnSignUp!!.setOnClickListener {
            if(checkEmail())return@setOnClickListener
            if(checkPass())return@setOnClickListener
                mAuth!!.createUserWithEmailAndPassword(
                    edtEmail!!.getText().toString(),
                    edtPass!!.getText().toString()
                )
                    .addOnSuccessListener {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Sign-up success",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Sign-up failed", Toast.LENGTH_SHORT
                        ).show()
                    }
        }
    }
    private fun init() {
        edtName = findViewById(R.id.editTextNameRegister)
        edtEmail = findViewById(R.id.editTextEmailRegister)
        edtPass = findViewById(R.id.editTextPassRegister)
        btnSignUp = findViewById(R.id.ButtonRegister)
        toolbar = findViewById(R.id.toolbarregister)
        btnMoveLogin = findViewById(R.id.ButtonMoveLogin)
        checkBox = findViewById<CheckBox>(R.id.checkboxRegister)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        toolbar!!.setNavigationIcon(R.drawable.back)
        toolbar!!.setTitle("");
        toolbar!!.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })
        val htmlcontent =
            "Đã có tài khoản?<font color=\"#4356B4\"> Đăng nhập ngay</font>"
        btnMoveLogin!!.setText(Html.fromHtml(htmlcontent))
        val content =
            " Tôi đồng ý với các<font color=\"#4356B4\"> chính sách </font>và<font color=\"#4356B4\"> điều khoản</font>"
        checkBox!!.setText(Html.fromHtml(content))
        btnSignUp!!.setEnabled(false);
    }
    private fun checkName(): Boolean {
        if (TextUtils.isEmpty(edtName!!.text.toString())) {
            edtName!!.error = "REQUIRED"
            return true
        }
        return false
    }
    private fun checkEmail(): Boolean {
        val email = edtEmail!!.text.toString().trim { it <= ' ' }
        if(Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()){
            return false
        }
        else if (TextUtils.isEmpty(email)){
            edtEmail!!.setError("Empty Email!")
            return true
        }
        else{
            edtEmail!!.setError("Invalidate Email")
            return true
        }
    }
    private fun checkPass():Boolean{
        val password=edtPass!!.text.toString().trim()
        if(Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"
            ).matcher(password).matches()){
            return false
        }
        else if (TextUtils.isEmpty(password)){
            edtPass!!.setError("Empty Password!")
            return true
        }
        else{
            edtPass!!.setError("Invalidate Password");
            return true
        }
    }
    private fun setEnabledButton() {
        if (checkBox!!.isChecked && !TextUtils.isEmpty(edtEmail!!.text.toString()) &&
            !TextUtils.isEmpty(edtName!!.text.toString()) &&
            !TextUtils.isEmpty(edtPass!!.text.toString())
        ) {
            btnSignUp!!.isEnabled = true
            btnSignUp!!.setBackgroundResource(R.drawable.custom_button_enable)
        }
        else{
            btnSignUp!!.isEnabled=false
            btnSignUp!!.setBackgroundResource(R.drawable.custom_button_login)
        }
    }
    private fun EnableButton(){
        edtName!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
                if (checkName())return
            }

            override fun afterTextChanged(editable: Editable) {
            }
        })

        val editTexts = listOf(edtEmail, edtName, edtPass)
        for (editText in editTexts) {
            editText!!.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    setEnabledButton()
                }
                override fun beforeTextChanged(
                    s: CharSequence, start: Int, count: Int, after: Int) {
                }
                override fun afterTextChanged(
                    s: Editable) {
                }
            })
        }
        checkBox!!.setOnClickListener{
            setEnabledButton()
        }
    }
}