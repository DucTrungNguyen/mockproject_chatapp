package com.rikkei.tranning.chatapp.views.uis.signup

import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rikkei.tranning.chatapp.R
import com.rikkei.tranning.chatapp.base.BaseFragment
import com.rikkei.tranning.chatapp.databinding.FragmentSignupBinding
import com.rikkei.tranning.chatapp.views.uis.login.LoginFragment
import kotlinx.android.synthetic.main.fragment_signup.*

class SignUpFragment : BaseFragment<FragmentSignupBinding, SignUpViewModel>() {
    override fun getBindingVariable(): Int {
        return BR.viewModels
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_signup
    }

    override fun getViewModel(): SignUpViewModel {
        return ViewModelProviders.of(requireActivity()).get<SignUpViewModel>(
            SignUpViewModel::class.java
        )

    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        val htmlContentCheckBox =
            "Tôi đồng ý với các<font color=\"#4356B4\"> chính sách</font> và <font color=\"#4356B4\"> điều khoản</font>"
        mViewDataBinding.checkboxRegister.text = Html.fromHtml(
            htmlContentCheckBox
        )
        val htmlContentButton =
            "Đã có tài khoản? <font color=\"#4356B4\"> Đăng nhập ngay</font>"
        mViewDataBinding.ButtonMoveLogin.text = Html.fromHtml(htmlContentButton)
        eventEnableButton()
        mViewDataBinding.ButtonBackRegister.setOnClickListener { replaceFragment() }
        mViewDataBinding.ButtonMoveLogin.setOnClickListener { replaceFragment() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel!!.signUpStatus.observe(viewLifecycleOwner, Observer {
            when (it) {
                is SignUpViewModel.SignUpStatus.Loading -> {
                    if (it.loading)
                        progress_circular_signUp.visibility = View.VISIBLE
                    else
                        progress_circular_signUp.visibility = View.GONE
                }
                is SignUpViewModel.SignUpStatus.IsOk -> {
                    progress_circular_signUp.visibility = View.GONE
                    Toast.makeText(context, "Sign Up Success!", Toast.LENGTH_SHORT).show()
                    replaceFragment()
                }
                is SignUpViewModel.SignUpStatus.Failure -> {
                    Toast.makeText(context, "Sign up fail", Toast.LENGTH_SHORT).show()
                    progress_circular_signUp.visibility = View.GONE
                }
                is SignUpViewModel.SignUpStatus.ErrorPassAndEmail -> {
                    Toast.makeText(context, "Invalid Email Or Password!", Toast.LENGTH_SHORT).show()
                    progress_circular_signUp.visibility = View.GONE
                }
            }
        })
//        mViewModel?.signUpUserMutableLiveData?.observe(
//            viewLifecycleOwner,
//            Observer { loginUser ->
//                if (!loginUser.validateEmailPassword()) {
//                    Toast.makeText(
//                        context,
//                        "Invalid Email Or Password!",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } else {
//                    mViewModel?.createUserFireBase()
//                }
//            })

        mViewModel?.userName?.observe(
            viewLifecycleOwner,
            Observer { s ->
                if (TextUtils.isEmpty(s)) {
                    mViewDataBinding!!.editTextNameRegister.error = "REQUIRED"
                }
            })
    }

    private fun eventEnableButton() {
        val editTexts = arrayOf(
            mViewDataBinding.editTextNameRegister,
            mViewDataBinding.editTextEmailRegister,
            mViewDataBinding.editTextPassRegister
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
        mViewDataBinding.checkboxRegister.setOnClickListener { setEnableButton() }
    }

    private fun replaceFragment() {
        mViewDataBinding.editTextNameRegister.setText("")
        mViewDataBinding.editTextPassRegister.setText("")
        mViewDataBinding.editTextEmailRegister.setText("")
        mViewDataBinding.checkboxRegister.isChecked=false
        mViewModel.resetStatus()
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.FrameLayout, LoginFragment(), null).commit()
    }

    fun setEnableButton() {
        if (mViewDataBinding.checkboxRegister.isChecked
            && !TextUtils.isEmpty(
                mViewDataBinding.editTextNameRegister.text.toString()
            )
            && !TextUtils.isEmpty(
                mViewDataBinding.editTextEmailRegister.text.toString()
            )
            && !TextUtils.isEmpty(
                mViewDataBinding.editTextPassRegister.text.toString()
            )
        ) {
            mViewDataBinding.ButtonRegister.isEnabled = true
            mViewDataBinding.ButtonRegister.setBackgroundResource(R.drawable.custom_button_enable)
        } else {
            mViewDataBinding.ButtonRegister.isEnabled = false
            mViewDataBinding.ButtonRegister.setBackgroundResource(R.drawable.custom_button_login)
        }
    }
}