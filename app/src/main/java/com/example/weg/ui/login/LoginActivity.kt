package com.example.weg.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weg.R
import com.example.weg.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var keyHash = Utility.getKeyHash(this)
        Log.d("kakao", "This is key hash : $keyHash")
        KakaoSdk.init(this, this.getString(R.string.kakao_app_key))


        /** Click_listener */
        binding.kakaoLogin!!.setOnClickListener {
            kakaoLogin() //로그인
        }
        val username = binding.username;
        val password = binding.password;
        val login = binding.login;
        val loading = binding.loading;
        val signUp = binding.signUp;

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid
            if (signUp != null) {
                signUp.isEnabled = loginState.isDataValid
            }
            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
//                updateUiWithUser(loginResult.success)
                // User name 보내기
                val intent = Intent();
                intent.putExtra("userId", loginResult.success.userId);
                setResult(Activity.RESULT_OK, intent)
                //Complete and destroy login activity once successful
                finish()
            }
        })

        loginViewModel.signUpResult.observe(this@LoginActivity, Observer {
            val signUpResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (signUpResult.error != null) {
                showSignUpFailed(signUpResult.error)
            }
            if (signUpResult.success != null) {
                showSignUpSuccess(signUpResult.success)
            }
        })

        loginViewModel.kakaoResult.observe(this@LoginActivity, Observer {
            val kakaoResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (kakaoResult.isExist) {
                Log.d("KAKAO", "제발 로그인 시작하면 로그인하게 해줘")
                loginViewModel.login(kakaoResult.id, kakaoResult.pwd);
            }else{
                loginViewModel.signUp(kakaoResult.id, kakaoResult.pwd);
            }
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }


        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }
        }
//            setOnEditorActionListener { _, actionId, _ ->
//                when (actionId) {
//                    EditorInfo.IME_ACTION_DONE ->
//                        loginViewModel.login(
//                            username.text.toString(),
//                            password.text.toString()
//                        )
//                }
//                false
//            }

        login.setOnClickListener {
            loading.visibility = View.VISIBLE
            loginViewModel.login(username.text.toString(), password.text.toString())
        }

        if (signUp != null) {
            signUp.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.signUp(username.text.toString(), password.text.toString())
            }
        }
    }

    private fun kakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("KAKAO", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i("KAKAO", "카카오계정으로 로그인 성공 ${token.accessToken}")
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e("KAKAO", "사용자 정보 요청 실패", error)
                    }
                    else if (user != null) {
                        Log.i("KAKAO", "사용자 정보 요청 성공" +
                                "\n회원번호: ${user.id}" +
                                "\n이메일: ${user.kakaoAccount?.email}" +
                                "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                        Log.d("KAKAO", "kakao login try!!!")
                        val index = user.kakaoAccount?.email.toString().indexOf("@")
                        val id = user.kakaoAccount?.email.toString().substring(0, index)
                        loginViewModel.tryKakaoLogin(id);
                    }
                }
            }
        }
        //  카카오계정으로 로그인
        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
    }



    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
//        val displayName = model.displayName
        // TODO : initiate successful logged in experience

    }

    private fun showSignUpSuccess(model: LoggedInUserView) {
        Toast.makeText(applicationContext, "Sign Up 성공", Toast.LENGTH_SHORT).show()
        Toast.makeText(applicationContext, "새로 만든 계정으로 로그인을 시작합니다.", Toast.LENGTH_SHORT).show()
        binding.loading.visibility = View.VISIBLE;
        loginViewModel.login(binding.username.text.toString(), binding.password.text.toString())
    }

    private fun showLoginFailed(errorString: String) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    private fun showSignUpFailed(errorString: String) {
        Toast.makeText(applicationContext, "This is Error String : $errorString", Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}