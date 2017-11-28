package i.am.eipeks.rims._fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.*;

import i.am.eipeks.rims.R;
import i.am.eipeks.rims._activities.HomeReview;
import i.am.eipeks.rims._classes._model_class.MiniTripInfo;
import i.am.eipeks.rims._adapters.HomeSection;
import i.am.eipeks.rims._database.CentralDBHelper;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;


public class Home extends Fragment {

    //<editor-fold desc="MiniTripInfo Arrays">
    private ArrayList<MiniTripInfo> miniTripInfos;
    private ArrayList<MiniTripInfo> today;
    private ArrayList<MiniTripInfo> yesterday;
    private ArrayList<MiniTripInfo> recent;
    //</editor-fold>

    //<editor-fold desc="Central Database">
    private CentralDBHelper centralDB;
    //</editor-fold>

    //<editor-fold desc="Views & Adapters">
    private RecyclerView recyclerView;

    private SectionedRecyclerViewAdapter adapter;

    private LinearLayout linearLayout;
    //</editor-fold>

    //<editor-fold desc="Others">
    private Intent intent;
    //</editor-fold>

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //<editor-fold desc="Initialize">
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        intent = new Intent(Home.this.getActivity(), HomeReview.class);

        FloatingActionButton fab = rootView.findViewById(R.id.add_new_trip);

        adapter = new SectionedRecyclerViewAdapter();

        recyclerView = rootView.findViewById(R.id.recycler_view_home);
        linearLayout = rootView.findViewById(R.id.no_trips_layout);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        //</editor-fold>

        //<editor-fold desc="Floating Point Button">
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GoToTrip) getActivity()).goToTrip(R.id.add_a_trip_menu_item);
            }
        });
        //</editor-fold>

        //<editor-fold desc="Execute AsyncTask">
        asyncTask.execute();
        //</editor-fold>

        return rootView;
    }

    public interface GoToTrip{
        void goToTrip(int itemId);
    }

    //<editor-fold desc="Initialize AsyncTask">
    private AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {

        @Override
        protected void onPreExecute() {
            centralDB = new CentralDBHelper(Home.this.getContext());
            today = new ArrayList<>();
            yesterday = new ArrayList<>();
            recent = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            miniTripInfos = centralDB.getMiniTripInfos();
            for (MiniTripInfo miniTripInfo: miniTripInfos){
                int tripDate = Integer.parseInt(miniTripInfo.getSubTrip().getTrip().getCalendarDate());
                int tripMonth = Integer.parseInt(miniTripInfo.getSubTrip().getTrip().getCalendarMonth());
                int tripYear = Integer.parseInt(miniTripInfo.getSubTrip().getTrip().getCalendarYear());

                int currentDate = Calendar.getInstance().get(Calendar.DATE);
                int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);

                if (currentYear > tripYear){
                    recent.add(miniTripInfo);
                } else {
                    if (currentMonth > tripMonth){
                        recent.add(miniTripInfo);
                    } else if (currentMonth == tripMonth){
                        if (Math.abs((tripDate - currentDate)) == 1){
                            yesterday.add(miniTripInfo);
                        } else if (Math.abs((tripDate - currentDate)) > 1){
                            recent.add(miniTripInfo);
                        } else {
                            today.add(miniTripInfo);
                        }
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (miniTripInfos.size() > 0){
                linearLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);


                if (!today.isEmpty()){
                    adapter.addSection(new HomeSection(Home.this.getContext(), intent, "Today", today));
                }
                if (!yesterday.isEmpty()){
                    adapter.addSection(new HomeSection(Home.this.getContext(), intent, "Yesterday", yesterday));
                }
                if (!recent.isEmpty()){
                    adapter.addSection(new HomeSection(Home.this.getContext(), intent, "Recent", recent));
                }
                adapter.notifyDataSetChanged();
            }
        }
    }; 
    //</editor-fold>

}
