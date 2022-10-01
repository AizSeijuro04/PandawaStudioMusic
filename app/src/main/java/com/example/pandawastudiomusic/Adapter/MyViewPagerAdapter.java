package com.example.pandawastudiomusic.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.pandawastudiomusic.BookingActivity;
import com.example.pandawastudiomusic.Fragments.BookingStep1Fragment;
import com.example.pandawastudiomusic.Fragments.BookingStep2Fragment;
import com.example.pandawastudiomusic.Fragments.BookingStep3Fragment;
import com.example.pandawastudiomusic.Fragments.BookingStep4Fragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {

    public MyViewPagerAdapter(BookingActivity fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment createFragment(int i) {
        switch (i)
        {
            case 0:
                return BookingStep1Fragment.getInstance();
            case 1:
                return BookingStep2Fragment.getInstance();
            case 2:
                return BookingStep3Fragment.getInstance();
            case 3:
                return BookingStep4Fragment.getInstance();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
