package com.agilezhu.login.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.agilezhu.common.util.QToast
import com.agilezhu.login.data.LoginResult
import com.agilezhu.login.databinding.ActivityLoginBinding
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_login.*

@Route(path = "/login/LoginActivity")
class LoginActivity : AppCompatActivity() {

    private lateinit var mLoginBinding: ActivityLoginBinding
    private lateinit var mLoginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mLoginBinding.root)
        mLoginViewModel =
            ViewModelProvider(this, LoginViewModelFactory()).get(LoginViewModel::class.java)


        mUserName.addTextChangedListener {
            mLoginViewModel.checkFormData(mUserName.text.toString(), mPassword.text.toString())
        }

        mPassword.addTextChangedListener {
            mLoginViewModel.checkFormData(mUserName.text.toString(), mPassword.text.toString())
        }

        mPassword.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mLoadingView.visibility = View.VISIBLE
                mLoginViewModel.login(mUserName.text.toString(), mPassword.text.toString())
            }
            false
        }

        mLoginViewModel.mLoginFormStatus.observe(this, Observer() { loginFormStatus ->
            mLoginButton.isEnabled = loginFormStatus.isDataValid
            loginFormStatus.mUserNameErrorHint?.let {
                mUserName.error = getString(loginFormStatus.mUserNameErrorHint!!)
            }
            loginFormStatus.mPasswordErrorHint?.let {
                mPassword.error = getString(loginFormStatus.mPasswordErrorHint!!)
            }
        })

        mLoginViewModel.mLoginResult.observe(this, Observer { loginResult ->
            mLoadingView.visibility = View.GONE
            if (!loginResult.isSuccess()) {
                loginResult.mErrorInfoRes?.let { QToast.show(getString(it)) }
                return@Observer
            }
            if (loginResult.isSuccess()) {
                if (loginResult.action == LoginResult.ACTION_REGISTER) {
                    QToast.show("注册成功 " + loginResult.mUserInfo!!.name)
                } else {
                    QToast.show("登录成功 " + loginResult.mUserInfo!!.name)
                }
            }
//            setResult(Activity.RESULT_OK)
//            finish()
        })

        mLoginButton.setOnClickListener {
            mLoadingView.visibility = View.VISIBLE
            mLoginViewModel.login(mUserName.text.toString(), mPassword.text.toString())
        }
        mRegisterButton.setOnClickListener {
            mLoadingView.visibility = View.VISIBLE
            mLoginViewModel.register(mUserName.text.toString(), mPassword.text.toString())
        }
    }
}