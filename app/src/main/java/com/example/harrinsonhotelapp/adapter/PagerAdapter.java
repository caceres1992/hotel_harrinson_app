package com.example.harrinsonhotelapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.harrinsonhotelapp.ui.habitacion.Lista_Habitacion_Fragment;
import com.example.harrinsonhotelapp.ui.promocion.NotificationsFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    int numoftabs;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numoftabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return  new Lista_Habitacion_Fragment();

            case 1:
                return  new NotificationsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numoftabs;
    }
}
