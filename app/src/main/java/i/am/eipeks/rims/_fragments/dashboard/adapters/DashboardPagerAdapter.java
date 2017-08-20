package i.am.eipeks.rims._fragments.dashboard.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import i.am.eipeks.rims._fragments.dashboard.fragments.Daily;
import i.am.eipeks.rims._fragments.dashboard.fragments.Monthly;
import i.am.eipeks.rims._fragments.dashboard.fragments.Weekly;


public class DashboardPagerAdapter extends FragmentStatePagerAdapter {

    private int count;

    public DashboardPagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Daily();
            case 1:
                return new Weekly();
            case 2:
                return new Monthly();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Daily";
            case 1:
                return "Weekly";
            case 2:
                return "Monthly";
        }
        return null;
    }

    @Override
    public int getCount() {
        return this.count;
    }
}
