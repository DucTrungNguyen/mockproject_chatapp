package com.rikkei.tranning.chatapp.views.uis.friend

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.rikkei.tranning.chatapp.R
import com.rikkei.tranning.chatapp.views.adapters.ViewPagerAdapter
import com.rikkei.tranning.chatapp.views.adapters.ViewPagerFriendAdapter

class FriendsFragment : Fragment() {

    var tabLayoutFriend: TabLayout? = null
    var viewPagerFriend: ViewPager? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_friend, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tabLayoutFriend = view?.findViewById(R.id.tabLayoutFriend)
        viewPagerFriend = view?.findViewById(R.id.viewPagerFriend)

        tabLayoutFriend!!.addTab(tabLayoutFriend!!.newTab().setText("Friends"))
        tabLayoutFriend!!.addTab(tabLayoutFriend!!.newTab().setText("All Friends"))
        tabLayoutFriend!!.addTab(tabLayoutFriend!!.newTab().setText("Request"))
        tabLayoutFriend!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ViewPagerFriendAdapter(requireContext(), childFragmentManager, tabLayoutFriend!!.tabCount)
        viewPagerFriend!!.adapter = adapter

        viewPagerFriend!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayoutFriend))

        tabLayoutFriend!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPagerFriend!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }





}