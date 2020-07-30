package com.rikkei.tranning.chatapp.views.uis.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
//import com.rikkei.tranning.chatapp.BR
import com.rikkei.tranning.chatapp.Base.BaseFragment
import com.rikkei.tranning.chatapp.R
import com.rikkei.tranning.chatapp.ViewModelProviderFactory
import com.rikkei.tranning.chatapp.databinding.FragmentLoginBinding
import com.rikkei.tranning.chatapp.views.uis.MainActivity
import com.rikkei.tranning.chatapp.views.uis.signup.SignUpFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment<FragmentLoginBinding?, LoginViewModel?>() {
    var mFragmentLoginBinding: FragmentLoginBinding? = null
    var mLoginViewModel: LoginViewModel? = null
    override fun getBindingVariable(): Int {
        return 1
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun getViewModel(): LoginViewModel {
        mLoginViewModel =
            ViewModelProviders.of(this, ViewModelProviderFactory()).get(
                LoginViewModel::class.java
            )
        return mLoginViewModel as LoginViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        mFragmentLoginBinding = viewDataBinding
        val htmlcontentbutton =
            "Chưa có tài khoản? <font color=\"#4356B4\"> Đăng ký ngay</font>"
        mFragmentLoginBinding!!.ButtonMoveRegister.text = Html.fromHtml(
            htmlcontentbutton
        )
        eventEditText()
        mFragmentLoginBinding!!.ButtonMoveRegister.setOnClickListener { replaceFragment() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        mLoginViewModel.login
//        mLoginViewModel.

        mLoginViewModel!!.loginStatus.observe(viewLifecycleOwner, Observer {
            when(it){
                is LoginViewModel.LoginStatus.Loading ->{
                    progress_circular.visibility = View.VISIBLE
                }
                is LoginViewModel.LoginStatus.isOk ->{
                    progress_circular.visibility = View.GONE
                    Toast.makeText(context, "Login success!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)

                }
                is LoginViewModel.LoginStatus.Failure ->{
                    Toast.makeText(context, "Login Failed!", Toast.LENGTH_SHORT).show()
                    progress_circular.visibility = View.GONE

                }
                is LoginViewModel.LoginStatus.ErrorPassAndEmail ->{
                    Toast.makeText(context, "Invalid Email Or Password!", Toast.LENGTH_SHORT).show()
                    progress_circular.visibility = View.GONE

                }
            }
        })
//        mLoginViewModel!!.user.observe(
//            viewLifecycleOwner,
//            Observer { loginUser ->
//                if (!loginUser.validateEmailPassword()) {
//                    Toast.makeText(
//                        context,
//                        "Invalid Email Or Password!",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } else {
//                    mLoginViewModel!!.loginFirebase()
//                }
//            })
//        mLoginViewModel.isOk.observe(
//            viewLifecycleOwner,
//            Observer<Boolean> { aBoolean ->
//                if (aBoolean) {
//                    Toast.makeText(context, "Login success!", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(context, MainActivity::class.java)
//                    startActivity(intent)
//                } else {
//                    Toast.makeText(context, "Login Failed!", Toast.LENGTH_SHORT).show()
//                }
//            })
    }

    fun eventEditText() {
        val editTexts = arrayOf(
            mFragmentLoginBinding!!.editTextPassLogin,
            mFragmentLoginBinding!!.editTextEmailLogin
        )
        for (i in editTexts.indices) {
            editTexts[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    setEnableButton()
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }
    }

    fun replaceFragment() {
        val fragmentManager = fragmentManager
        val fragmentTransaction =
            requireFragmentManager().beginTransaction()
        val signUp = SignUpFragment()
        fragmentTransaction.replace(R.id.FrameLayout, signUp, null)
        fragmentTransaction.commit()
    }

    fun setEnableButton() {
        if (!TextUtils.isEmpty(mFragmentLoginBinding!!.editTextEmailLogin.text.toString())
            && !TextUtils.isEmpty(
                mFragmentLoginBinding!!.editTextPassLogin.text.toString()
            )
        ) {
            mFragmentLoginBinding!!.ButtonLogin.isEnabled = true
            mFragmentLoginBinding!!.ButtonLogin.setBackgroundResource(R.drawable.custom_button_enable)
        } else {
            mFragmentLoginBinding!!.ButtonLogin.isEnabled = false
            mFragmentLoginBinding!!.ButtonLogin.setBackgroundResource(R.drawable.custom_button_login)
        }
    }
}