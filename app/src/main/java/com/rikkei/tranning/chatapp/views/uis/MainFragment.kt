package com.rikkei.tranning.chatapp.views.uis

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.rikkei.tranning.chatapp.R
import com.rikkei.tranning.chatapp.base.BaseFragment
import com.rikkei.tranning.chatapp.databinding.FragmentMainBinding
import com.rikkei.tranning.chatapp.views.adapters.ViewPagerAdapter
import com.rikkei.tranning.chatapp.views.uis.friend.SharedFriendViewModel


class MainFragment :  BaseFragment<FragmentMainBinding, SharedFriendViewModel>(){

    private var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val view: View? = inflater.inflate(R.layout.fragment_main, container, false)
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)

        tabLayout!!.addTab(tabLayout!!.newTab().setCustomView(R.layout.custom_tablayout_main_message))
        tabLayout!!.addTab(tabLayout!!.newTab().setCustomView(R.layout.custom_tablayout_main_friend))
        tabLayout!!.addTab(tabLayout!!.newTab().setCustomView(R.layout.custom_tablayout_main_profile))

//        tabLayout!!.addTab(tabLayout!!.newTab().setText("Messages").setIcon(R.drawable.ic_chat))
//        tabLayout!!.addTab(tabLayout!!.newTab().setText("Friends").setIcon(R.drawable.ic_friend))
//        tabLayout!!.addTab(
//            tabLayout!!.newTab().setText("Profile").setIcon(R.drawable.ic_user_bottom)
//        )
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = parentFragmentManager.let {
            context?.let { it1 ->
                ViewPagerAdapter(it1, it, tabLayout!!.tabCount)
            }
        }
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {


            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
                var tabView = tab.customView
                var notifiMainBlue = tabView?.findViewById<ImageView>(R.id.selectedTab)
                notifiMainBlue?.visibility = View.VISIBLE

                tabView?.findViewById<ImageView>(R.id.iconTabMain)?.colorFilter =
                    PorterDuffColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        ), PorterDuff.Mode.SRC_ATOP
                    )

                tabView?.findViewById<TextView>(R.id.textTabMain)?.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))


//                tab.icon!!.colorFilter = PorterDuffColorFilter(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.blue
//                    ), PorterDuff.Mode.SRC_ATOP
//                )
            }


            override fun onTabUnselected(tab: TabLayout.Tab) {
                var tabView = tab.customView
                var notifiMainBlue = tabView?.findViewById<ImageView>(R.id.selectedTab)
                notifiMainBlue?.visibility = View.GONE

                tabView?.findViewById<ImageView>(R.id.iconTabMain)?.colorFilter =
                    PorterDuffColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.iconMain
                        ), PorterDuff.Mode.SRC_ATOP
                    )

                tabView?.findViewById<TextView>(R.id.textTabMain)?.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
//                tabView?.findViewById<TextView>(R.id.textTabMain)?.colorFilter =
//                    PorterDuffColorFilter(
//                        ContextCompat.getColor(
//                            requireContext(),
//                            R.color.iconMain
//                        ), PorterDuff.Mode.SRC_ATOP
//                    )

//                tab.icon!!.colorFilter = PorterDuffColorFilter(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.iconMain
//                    ), PorterDuff.Mode.SRC_ATOP
//                )
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    override fun getBindingVariable(): Int {
        return  BR.mainViewModel
    }

    override fun getLayoutId(): Int {
        return  R.layout.fragment_main
    }

    override fun getViewModel(): SharedFriendViewModel {
        return ViewModelProviders.of(requireActivity()).get(SharedFriendViewModel::class.java)

    }
}