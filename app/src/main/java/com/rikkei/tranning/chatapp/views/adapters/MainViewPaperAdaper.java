package com.rikkei.tranning.chatapp.views.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

//adapter của tab tìm kiềm, trang chủ
public class MainViewPaperAdaper extends FragmentPagerAdapter {
    private ArrayList<Fragment> arrayFragment=new ArrayList<>();//chua cac fragment(tìm kiếm, trang chủ, mv, luu)
    private ArrayList<String> arrayTitle=new ArrayList<>();//chua cac title cua fragment

    public MainViewPaperAdaper(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return arrayFragment.get(position);//tra ve vi ri cua fragment lay ra
    }

    @Override
    public int getCount() {
        return arrayFragment.size();//so luong fragment
    }
    //add fragment va title cua no
    public void AddFragment(Fragment fragment, String title){
        arrayFragment.add(fragment);
        arrayTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arrayTitle.get(position);//tra ve title cua fragment
    }
}
