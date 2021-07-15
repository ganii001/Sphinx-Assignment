package com.example.sphinxassignment.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.sphinxassignment.R
import com.example.sphinxassignment.network.responses.LoginResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.*


@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar)
        setSliderImages()

        setMostUsedRecycler()
    }

    private fun setMostUsedRecycler() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv1.layoutManager = linearLayoutManager
        rv1.itemAnimator = DefaultItemAnimator()
        rv1.setHasFixedSize(true)


        val linearLayoutManager1 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv2.layoutManager = linearLayoutManager1
        rv2.itemAnimator = DefaultItemAnimator()
        rv2.setHasFixedSize(true)

        val linearLayoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv3.layoutManager = linearLayoutManager2
        rv3.itemAnimator = DefaultItemAnimator()
        rv3.setHasFixedSize(true)

        val list = ArrayList<LoginResponse>()
        val list1 = ArrayList<LoginResponse>()
        val list2 = ArrayList<LoginResponse>()
        for (i in 0..2) {
            val data = LoginResponse().apply {
                foodname = "Veg-Food !!"
            }
            list.add(data)
        }
        for (i in 0..1) {
            val data = LoginResponse().apply {
                foodname = "Cleaning !!"
            }
            list1.add(data)
        }

        val data = LoginResponse().apply {
            foodname = "Resto !!"
        }
        list2.add(data)


        val adapterFood = AdapterFood(this)
        rv1.adapter = adapterFood
        adapterFood.refresh(list)

        val adapterFood1 = AdapterFood(this)
        rv2.adapter = adapterFood1
        adapterFood1.refresh(list1)

        val adapterFood2 = AdapterFood(this)
        rv3.adapter = adapterFood2
        adapterFood2.refresh(list2)
    }

    private fun setSliderImages() {
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.img1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.img2, ScaleTypes.FIT))
        banner_slider.setImageList(imageList)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }
}