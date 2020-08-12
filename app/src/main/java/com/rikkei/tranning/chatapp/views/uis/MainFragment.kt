package com.rikkei.tranning.chatapp.views.uis

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
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
    var currentTab = 0;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val view: View? = inflater.inflate(R.layout.fragment_main, container, false)
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)

        tabLayout?.addTab(tabLayout!!.newTab().setCustomView(R.layout.custom_tablayout_main_message))
        tabLayout?.addTab(tabLayout!!.newTab().setCustomView(R.layout.custom_tablayout_main_friend))
        tabLayout?.addTab(tabLayout!!.newTab().setCustomView(R.layout.custom_tablayout_main_profile))

//        tabLayout?.get(0)?.alpha = 0.2f
//        tabLayout?.get(0)?.animate()?.apply {
//            interpolator = LinearInterpolator()
//            duration = 1500
//            alpha(1f)
//            startDelay = 2000
//            start()
//        }

        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = parentFragmentManager.let {
            context?.let { it1 ->
                ViewPagerAdapter(it1, it, tabLayout!!.tabCount)
            }
        }
        viewPager?.adapter = adapter

        viewPager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {


            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
                currentTab = tab.position
                val tabView = tab.customView
                val notifiMainBlue = tabView?.findViewById<ImageView>(R.id.selectedTab)
                notifiMainBlue?.visibility = View.VISIBLE

                tabView?.findViewById<ImageView>(R.id.iconTabMain)?.colorFilter =
                    PorterDuffColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        ), PorterDuff.Mode.SRC_ATOP
                    )

                tabView?.findViewById<TextView>(R.id.textTabMain)?.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))

            }


            override fun onTabUnselected(tab: TabLayout.Tab) {
                val tabView = tab.customView
                val notifiMainBlue = tabView?.findViewById<ImageView>(R.id.selectedTab)
                notifiMainBlue?.visibility = View.GONE

                tabView?.findViewById<ImageView>(R.id.iconTabMain)?.colorFilter =
                    PorterDuffColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.iconMain
                        ), PorterDuff.Mode.SRC_ATOP
                    )

                tabView?.findViewById<TextView>(R.id.textTabMain)?.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mViewModel.countNotifiRequest.observe(
            viewLifecycleOwner,
            Observer { s: String ->
                val viewRequest: View? =
                    mViewDataBinding.tabLayout.getTabAt(1)?.customView
                val count = viewRequest?.findViewById<View>(R.id.notifiMain) as TextView
                if (s == "0") count.visibility = View.GONE
                else  {
                    count.visibility = View.VISIBLE
                    count.text = s
                }
            }
        )
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