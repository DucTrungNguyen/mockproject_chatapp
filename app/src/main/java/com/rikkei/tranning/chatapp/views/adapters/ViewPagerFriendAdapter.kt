package com.rikkei.tranning.chatapp.views.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rikkei.tranning.chatapp.views.uis.friend.FriendsFragment
import com.rikkei.tranning.chatapp.views.uis.friend.allfriends.AllFriendsFragment
import com.rikkei.tranning.chatapp.views.uis.friend.myfriends.MyFriendsFragment
import com.rikkei.tranning.chatapp.views.uis.friend.requestfriends.RequestFriendsFragment
import com.rikkei.tranning.chatapp.views.uis.message.MessagesFragment
import com.rikkei.tranning.chatapp.views.uis.profile.ProfileFragment

class ViewPagerFriendAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                return MyFriendsFragment()
            }
            1 -> {
                return AllFriendsFragment()
            }
            else -> return RequestFriendsFragment()
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}