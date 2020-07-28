package com.rikkei.tranning.chatapp.views.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rikkei.tranning.chatapp.views.uis.FriendsFragment
import com.rikkei.tranning.chatapp.views.uis.MessagesFragment
import com.rikkei.tranning.chatapp.views.uis.ProfileFragment

class ViewPagerAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                return MessagesFragment()
            }
            1 -> {
                return FriendsFragment()
            }
            else -> return ProfileFragment()
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}