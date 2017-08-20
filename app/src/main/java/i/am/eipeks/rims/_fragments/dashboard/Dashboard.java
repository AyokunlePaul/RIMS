package i.am.eipeks.rims._fragments.dashboard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;

import i.am.eipeks.rims.R;
import i.am.eipeks.rims._fragments.dashboard.adapters.DashboardPagerAdapter;


public class Dashboard extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dashboard = inflater.inflate(R.layout.fragment_dashboard, container, false);

        viewPager = (ViewPager) dashboard.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) dashboard.findViewById(R.id.tab_layout);

        DashboardPagerAdapter dashboardPagerAdapter = new DashboardPagerAdapter(getFragmentManager(), 3);
        viewPager.setAdapter(dashboardPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        return dashboard;
    }

}
