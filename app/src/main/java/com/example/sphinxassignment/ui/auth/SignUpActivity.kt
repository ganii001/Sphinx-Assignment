package com.example.sphinxassignment.ui.auth

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ridecellassignment.Validations
import com.example.sphinxassignment.R
import com.example.sphinxassignment.Utils
import com.example.sphinxassignment.databinding.ActivityLoginBinding
import com.example.sphinxassignment.databinding.ActivitySignupBinding
import com.example.sphinxassignment.network.responses.CityList
import com.example.sphinxassignment.ui.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignupBinding
    val viewModel: LoginViewModel by viewModels()
    lateinit var pDialog: Dialog
    val listname = ArrayList<String>()
    val listCity = ArrayList<CityList>()
    var lat = ""
    var lon = ""
    var city_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        binding.executePendingBindings()
        binding.viewModel = viewModel
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationIcon(R.drawable.back)
        toolbar.setNavigationOnClickListener { finish() }
        pDialog = Utils.generateProgressDialog(this)!!

        observeCityList()
        onSignUp()
        getCityList()

        binding.btnRegister.setOnClickListener {
            if (!Validations.edtValidation(edit_username, "Enter Username"))
                return@setOnClickListener
            else if (!Validations.emailValidation(edit_emailId))
                return@setOnClickListener
            else if (!Validations.mobileValidation(edit_mobile))
                return@setOnClickListener
            else if (!Validations.edtValidation(edit_password, "Enter Password"))
                return@setOnClickListener
            else if (sp_city.selectedItem.toString().equals("City", true)) {
                Utils.showToast(this, "Select City")
            } else
                signUpUser()
        }

        binding.spCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (sp_city.selectedItem.toString() != "City") {
                    for (list in listCity) {
                        if (sp_city.selectedItem.toString() == list.name) {
                            city_id = list.id!!
                            lat = list.latitude!!
                            lon = list.longitude!!
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun onSignUp() {
        viewModel.isSuccessful.observe(this, androidx.lifecycle.Observer { isSuccess ->
            if (isSuccess) {
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }
            pDialog.dismiss()
        })
    }


    private fun observeCityList() {
        viewModel.cityList.observe(this, androidx.lifecycle.Observer { list ->
            listname.clear()
            listCity.clear()
            listCity.addAll(list)
            for (cityList in list) {
                if (cityList.name != null) {
                    listname.add(cityList.name)
                }
            }

            if (listname.isNotEmpty()) {
                listname[0] = "City"
                binding.spCity.adapter = Utils.arrayAdpter(applicationContext, listname)
            }

            pDialog.dismiss()
        })
    }

    private fun getCityList() {
        if (Utils.isDataConnected(this)) {
            viewModel.getCityList()
            pDialog.show()
        } else {
            Utils.showSNACK_BAR_NO_INTERNET(this, localClassName)
        }
    }

    private fun signUpUser() {
        if (Utils.isDataConnected(this)) {
            viewModel.doSignUp(
                edit_emailId.text.toString(),
                edit_username.text.toString(),
                edit_mobile.text.toString(),
                "BIDDER",
                sp_city.selectedItem.toString(),
                lat,
                lon, edit_password.text.toString(), "", "", city_id
            )
            pDialog.show()
        } else {
            Utils.showSNACK_BAR_NO_INTERNET(this, localClassName)
        }
    }
}