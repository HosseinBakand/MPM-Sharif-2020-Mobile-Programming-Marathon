package com.marathon.ktm.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marathon.ktm.R
import com.marathon.ktm.banner.BannerActivity
import kotlinx.android.synthetic.main.activity_home_page.*


class HomePageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        btn_fine_near_places.setOnClickListener {
            val myIntent = Intent(this@HomePageActivity, AugmentedRealityLocationActivity::class.java)
            this@HomePageActivity.startActivity(myIntent)
        }
        btn_advertisement_banner.setOnClickListener {
            val myIntent = Intent(this@HomePageActivity, BannerActivity::class.java)
            this@HomePageActivity.startActivity(myIntent)
        }
        btn_show_products.setOnClickListener {

        }
    }
}