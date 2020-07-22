package com.rikkei.tranning.chatapp.Activity
import com.rikkei.tranning.chatapp.Activity.SignUp
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.rikkei.tranning.chatapp.R
import java.util.regex.Pattern

class Login : AppCompatActivity() {
    var btnMoveRegister: Button? = null
    var btnLogin: Button?=null
    var edtEmail: EditText?=null
    var edtPass: EditText?=null
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        mAuth = FirebaseAuth.getInstance()
        enableButton()
        btnMoveRegister!!.setOnClickListener {
            val intent = Intent(this@Login, SignUp::class.java)
            startActivity(intent)
        }
        btnLogin!!.setOnClickListener{
            if(checkEmail())return@setOnClickListener
            if(checkPass())return@setOnClickListener
            mAuth!!.signInWithEmailAndPassword(
                edtEmail!!.getText().toString(),
                edtPass!!.getText().toString()
            )
                .addOnSuccessListener {
                    Toast.makeText(this@Login, "User sign in success", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(this@Login, "Sign-in is failed!", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }
    private fun init() {
        edtPass=findViewById(R.id.editTextPassLogin)
        edtEmail=findViewById(R.id.editTextEmailLogin)
        btnLogin=findViewById(R.id.ButtonLogin)
        btnMoveRegister = findViewById(R.id.ButtonMoveRegister)
        val htmlcontent =
            "Chưa có tài khoản?<font color=\"#4356B4\"> Đăng kí ngay</font>"
        btnMoveRegister!!.setText(Html.fromHtml(htmlcontent))
        btnLogin!!.isEnabled=false
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
    private fun enableButton(){
        val editTexts = listOf(edtEmail, edtPass)
        for (editText in editTexts) {
            editText!!.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (!TextUtils.isEmpty(edtEmail!!.text.toString()) &&
                        !TextUtils.isEmpty(edtPass!!.text.toString())
                    ) {
                        btnLogin!!.isEnabled = true
                        btnLogin!!.setBackgroundResource(R.drawable.custom_button_enable)
                    }
                    else{
                        btnLogin!!.isEnabled=false
                        btnLogin!!.setBackgroundResource(R.drawable.custom_button_login)
                    }
                }
                override fun beforeTextChanged(
                    s: CharSequence, start: Int, count: Int, after: Int) {
                }
                override fun afterTextChanged(
                    s: Editable
                ) {
                }
            })
        }
    }
}