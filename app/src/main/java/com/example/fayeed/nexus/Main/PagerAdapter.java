package com.example.fayeed.nexus.Main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.fayeed.nexus.Main.BHHHOOO.BHHOOO_Main;
import com.example.fayeed.nexus.Main.Chat.Chat_Main;

/**
 * Created by fayeed on 12/19/2016.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new BHHOOO_Main();
            case 1:
                return new Chat_Main();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 0;
    }
}
