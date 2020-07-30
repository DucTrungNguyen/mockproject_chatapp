package com.rikkei.tranning.chatapp.views.uis.signup

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
import com.rikkei.tranning.chatapp.databinding.FragmentSignupBinding
import com.rikkei.tranning.chatapp.views.uis.login.LoginFragment
import kotlinx.android.synthetic.main.fragment_signup.*

class SignUpFragment : BaseFragment<FragmentSignupBinding?, SignUpViewModel?>() {
    private var mFragmentSignUpBinding: FragmentSignupBinding? = null
    private var mSignUpViewModel: SignUpViewModel? = null
    override fun getBindingVariable(): Int {
        return 1
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_signup
    }

    override fun getViewModel(): SignUpViewModel {
        mSignUpViewModel =
            ViewModelProviders.of(this, ViewModelProviderFactory()).get(
                SignUpViewModel::class.java
            )
        return mSignUpViewModel as SignUpViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        mFragmentSignUpBinding = viewDataBinding
        val htmlcontentcheckbox =
            "Tôi đồng ý với các<font color=\"#4356B4\"> chính sách</font> và <font color=\"#4356B4\"> điều khoản</font>"
        mFragmentSignUpBinding!!.checkboxRegister.text = Html.fromHtml(
            htmlcontentcheckbox
        )
        val htmlcontentbutton =
            "Đã có tài khoản? <font color=\"#4356B4\"> Đăng nhập ngay</font>"
        mFragmentSignUpBinding!!.ButtonMoveLogin.text = Html.fromHtml(htmlcontentbutton)
        eventEnableButton()
        mFragmentSignUpBinding!!.ButtonBackRegister.setOnClickListener { replaceFragment() }
        mFragmentSignUpBinding!!.ButtonMoveLogin.setOnClickListener { replaceFragment() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        mSignUpViewModel!!.signUpStatus.observe(viewLifecycleOwner, Observer {
            when(it) {
                is SignUpViewModel.SignUpStatus.Loading ->{
                    progress_circular_signUp.visibility = View.VISIBLE
                }
                is SignUpViewModel.SignUpStatus.isOk ->{
                    progress_circular_signUp.visibility = View.GONE
                    Toast.makeText(context, "Sign Up Success!", Toast.LENGTH_SHORT).show()
                    replaceFragment()

                }
                is SignUpViewModel.SignUpStatus.Failure ->{

                }
                is SignUpViewModel.SignUpStatus.ErrorPassAndEmail ->{
                    Toast.makeText(context, "Sign Up Failed", Toast.LENGTH_SHORT).show()
                    progress_circular_signUp.visibility = View.GONE

            }
            }

        })


        mSignUpViewModel!!.signUpUserMutableLiveData.observe(
            viewLifecycleOwner,
            Observer { loginUser ->
                if (loginUser.validateEmailPassword() == false) {
                    Toast.makeText(
                        context,
                        "Invalid Email Or Password!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    mSignUpViewModel!!.createUserFireBase()
                }
            })

//        mSignUpViewModel!!.signUpSuccess.observe(
//            viewLifecycleOwner,
//            Observer { aBoolean ->
//                if (aBoolean) {
//                    Toast.makeText(context, "Sign Up Success!", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(context, "Sign Up Failed", Toast.LENGTH_SHORT).show()
//                }
//            })
        mSignUpViewModel!!.userName.observe(
            viewLifecycleOwner,
            Observer { s ->
                if (TextUtils.isEmpty(s)) {
                    mFragmentSignUpBinding!!.editTextNameRegister.error = "REQUIRED"
                }
            })
    }

    fun eventEnableButton() {
        val editTexts = arrayOf(
            mFragmentSignUpBinding!!.editTextNameRegister,
            mFragmentSignUpBinding!!.editTextEmailRegister,
            mFragmentSignUpBinding!!.editTextPassRegister
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
        mFragmentSignUpBinding!!.checkboxRegister.setOnClickListener { setEnableButton() }
    }

    fun replaceFragment() {
        val fragmentManager = fragmentManager
        val fragmentTransaction =
            requireFragmentManager().beginTransaction()
        val login = LoginFragment()
        fragmentTransaction.replace(R.id.FrameLayout, login, null)
        fragmentTransaction.commit()
    }

    fun setEnableButton() {
        if (mFragmentSignUpBinding!!.checkboxRegister.isChecked
            && !TextUtils.isEmpty(
                mFragmentSignUpBinding!!.editTextNameRegister.text.toString()
            )
            && !TextUtils.isEmpty(
                mFragmentSignUpBinding!!.editTextEmailRegister.text.toString()
            )
            && !TextUtils.isEmpty(
                mFragmentSignUpBinding!!.editTextPassRegister.text.toString()
            )
        ) {
            mFragmentSignUpBinding!!.ButtonRegister.isEnabled = true
            mFragmentSignUpBinding!!.ButtonRegister.setBackgroundResource(R.drawable.custom_button_enable)
        } else {
            mFragmentSignUpBinding!!.ButtonRegister.isEnabled = false
            mFragmentSignUpBinding!!.ButtonRegister.setBackgroundResource(R.drawable.custom_button_login)
        }
    }

    companion object {
        fun newInstance(): SignUpFragment {
            val args = Bundle()
            val fragment =
                SignUpFragment()
            fragment.arguments = args
            return fragment
        }
    }
}