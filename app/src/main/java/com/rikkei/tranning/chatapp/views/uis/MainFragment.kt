package com.rikkei.tranning.chatapp.views.uis

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.rikkei.tranning.chatapp.R
import com.rikkei.tranning.chatapp.views.adapters.ViewPagerAdapter


class MainFragment : Fragment() {
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = inflater.inflate(R.layout.fragment_main, container, false)
        if (view != null) {
            tabLayout = view.findViewById(R.id.tabLayout)
            viewPager = view.findViewById(R.id.viewPager)
        }

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Messages").setIcon(R.drawable.ic_chat))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Friends").setIcon(R.drawable.ic_friend))
        tabLayout!!.addTab(
            tabLayout!!.newTab().setText("Profile").setIcon(R.drawable.ic_user_bottom)
        )
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = fragmentManager?.let {
            context?.let { it1 ->
                ViewPagerAdapter(it1, it, tabLayout!!.tabCount)
            }
        }
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
                tab.icon!!.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blue
                    ), PorterDuff.Mode.SRC_ATOP
                )
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.icon!!.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.iconMain
                    ), PorterDuff.Mode.SRC_ATOP
                )
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        return view
    }
}