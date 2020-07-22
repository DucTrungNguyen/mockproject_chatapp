package com.rikkei.tranning.chatapp.views.uis

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.rikkei.tranning.chatapp.R
import com.rikkei.tranning.chatapp.views.adapters.ViewPagerAdapter

class MainActivity : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        this.supportActionBar?.hide()

//        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//
//        val navController = findNavController(R.id.nav_host_fragment)
//
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(
//            R.id.navigation_message, R.id.navigation_friends, R.id.navigation_profile))
//
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

//        setupActionBarWithNavController(navController, appBarConfiguration)


//        actionBar?.setTitle("abc")
//        navView.setupWithNavController(navController)

        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager = findViewById<ViewPager>(R.id.viewPager)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Messages").setIcon(R.drawable.ic_chat))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Friends").setIcon(R.drawable.ic_friend))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Profile").setIcon(R.drawable.ic_user_bottom))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ViewPagerAdapter(this, supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
}