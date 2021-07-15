package com.example.sphinxassignment.ui.auth

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ridecellassignment.Validations
import com.example.sphinxassignment.R
import com.example.sphinxassignment.Utils
import com.example.sphinxassignment.databinding.ActivityLoginBinding
import com.example.sphinxassignment.ui.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    val viewModel: LoginViewModel by viewModels()
    lateinit var pDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.executePendingBindings()
        binding.viewModel = viewModel
        pDialog = Utils.generateProgressDialog(this)!!

        onSignIn()

        binding.textSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            if (!Validations.emailValidation(edit_email))
                return@setOnClickListener
            else if (!Validations.edtValidation(edit_pass, "Enter Password"))
                return@setOnClickListener
            else
                signInUser()
        }
    }

    private fun signInUser() {
        if (Utils.isDataConnected(this)) {
            viewModel.doSignIn(edit_email.text.toString(), edit_pass.text.toString())
            pDialog.show()
        } else {
            Utils.showSNACK_BAR_NO_INTERNET(this, localClassName)
        }
    }

    private fun onSignIn() {
        viewModel.isSuccessful.observe(this, androidx.lifecycle.Observer { isSuccess ->
            if (isSuccess) {
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }
            pDialog.dismiss()
        })
    }
}