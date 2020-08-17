package com.rikkei.tranning.chatapp.views.uis.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rikkei.tranning.chatapp.R
import com.rikkei.tranning.chatapp.base.BaseFragment
import com.rikkei.tranning.chatapp.databinding.FragmentLoginBinding
import com.rikkei.tranning.chatapp.services.models.AllUserModel
import com.rikkei.tranning.chatapp.views.adapters.AllFriendAdapter
import com.rikkei.tranning.chatapp.views.uis.MainActivity
import com.rikkei.tranning.chatapp.views.uis.friend.DialogFriendFragment
import com.rikkei.tranning.chatapp.views.uis.message.ChatFragment
import com.rikkei.tranning.chatapp.views.uis.signup.SignUpFragment
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : BaseFragment<FragmentLoginBinding?, LoginViewModel?>() {
    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun getViewModel(): LoginViewModel {
        return ViewModelProviders.of(requireActivity()).get<LoginViewModel>(
            LoginViewModel::class.java
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
//        val htmlcontentbutton =
//            "Chưa có tài khoản? <font color=\"#4356B4\"> Đăng ký ngay</font>"
//        mViewDataBinding!!.ButtonMoveRegister.text = Html.fromHtml(
//            htmlcontentbutton
//        )
        val textSpan = mViewDataBinding!!.ButtonMoveRegister.text
        var index = textSpan.indexOf('?')
        val spannable = SpannableString(textSpan)
        spannable.setSpan(ForegroundColorSpan(Color.BLUE), ++index , textSpan.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        mViewDataBinding!!.ButtonMoveRegister.text = spannable
        eventEditText()
        mViewDataBinding!!.ButtonMoveRegister.setOnClickListener { replaceFragment() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel!!.loginStatus.observe(viewLifecycleOwner, Observer {
            when (it) {
                is LoginViewModel.LoginStatus.Loading -> {
                    progress_circular.visibility = View.VISIBLE
                }
                is LoginViewModel.LoginStatus.IsOk -> {
                    progress_circular.visibility = View.GONE
//                    Toast.makeText(context, "Login Success!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
                is LoginViewModel.LoginStatus.Failure -> {
                    Toast.makeText(context, "Login Failed!", Toast.LENGTH_SHORT).show()
                    mViewDataBinding!!.editTextEmailLogin.text = null
                    mViewDataBinding!!.editTextPassLogin.text = null
                    progress_circular.visibility = View.GONE

                }
                is LoginViewModel.LoginStatus.ErrorPassAndEmail -> {
                    if (it.isError) {
                        Toast.makeText(
                            context,
                            "Invalid Email Or Password! Login",
                            Toast.LENGTH_SHORT
                        ).show()
                        mViewDataBinding!!.editTextEmailLogin.text = null
                        mViewDataBinding!!.editTextPassLogin.text = null
                        progress_circular.visibility = View.GONE

                    }


                }
                is LoginViewModel.LoginStatus.Register -> {
//                    progress_circular.visibility = View.GONE
                    replaceFragment()

                }
            }
        })
    }

    private fun eventEditText() {
        val editTexts = arrayOf(
            mViewDataBinding!!.editTextPassLogin,
            mViewDataBinding!!.editTextEmailLogin
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

    private fun replaceFragment() {
        mViewDataBinding!!.editTextEmailLogin.setText("")
        mViewDataBinding!!.editTextPassLogin.setText("")
        mViewModel?.resetStatus()
        val fragmentTransaction = parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.exit, R.anim.pop_exit)
        fragmentTransaction.replace(R.id.FrameLayout, SignUpFragment(), null).commit()
    }

    fun setEnableButton() {
        if (!TextUtils.isEmpty(mViewDataBinding!!.editTextEmailLogin.text.toString())
            && !TextUtils.isEmpty(
                mViewDataBinding!!.editTextPassLogin.text.toString()
            )
        ) {
            mViewDataBinding!!.ButtonLogin.isEnabled = true
            mViewDataBinding!!.ButtonLogin.setBackgroundResource(R.drawable.custom_button_enable)
        } else {
            mViewDataBinding!!.ButtonLogin.isEnabled = false
            mViewDataBinding!!.ButtonLogin.setBackgroundResource(R.drawable.custom_button_login)
        }
    }
}