package i.am.eipeks.rims._activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import i.am.eipeks.rims.R;
import i.am.eipeks.rims._adapters.HomeSection;
import i.am.eipeks.rims._adapters.PassengerInformationSection;
import i.am.eipeks.rims._adapters.TripInformationSection;
import i.am.eipeks.rims._adapters.VehicleInformationSection;
import i.am.eipeks.rims._classes.Driver;
import i.am.eipeks.rims._classes.Passenger;
import i.am.eipeks.rims._classes.SubTrip;
import i.am.eipeks.rims._classes.Trip;
import i.am.eipeks.rims._classes.Vehicle;
import i.am.eipeks.rims._database.CentralDBHelper;
import i.am.eipeks.rims._database.VehicleDatabaseHelper;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;


public class HomeReview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_review);

//        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();

        String UUID = getIntent().getStringExtra(HomeSection.INTENT_UUID);
        String registrationNumber = getIntent().getStringExtra(HomeSection.INTENT_REGISTRATION_NUMBER);

        CentralDBHelper centralDBHelper = new CentralDBHelper(this);

        Vehicle vehicle = new VehicleDatabaseHelper(this).getVehicle(registrationNumber);
        ArrayList<Passenger> passengers = centralDBHelper.getPassengers(UUID);
        Driver driver = centralDBHelper.getDriver(UUID);
        Trip trip = centralDBHelper.getTrip(UUID);

        RecyclerView vehicleRecyclerView = (RecyclerView) findViewById(R.id.vehicle_information_recycler_view_home_review);
        RecyclerView tripRecyclerView = (RecyclerView) findViewById(R.id.trip_information_recycler_view_home_review);
        RecyclerView passengersRecyclerView = (RecyclerView) findViewById(R.id.passengers_information_recycler_view_home_review);

        vehicleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tripRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        passengersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        passengersRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        SectionedRecyclerViewAdapter passengerAdapter = new SectionedRecyclerViewAdapter();
        SectionedRecyclerViewAdapter vehicleAdapter = new SectionedRecyclerViewAdapter();
        SectionedRecyclerViewAdapter tripAdapter = new SectionedRecyclerViewAdapter();

        passengerAdapter.addSection(new PassengerInformationSection(passengers, "Passenger Information"));
        vehicleAdapter.addSection(new VehicleInformationSection(vehicle, "AuthVehicle Information"));
        tripAdapter.addSection(new TripInformationSection(this, new SubTrip(trip, driver), "Trip Information"));

        vehicleRecyclerView.setAdapter(vehicleAdapter);
        tripRecyclerView.setAdapter(tripAdapter);
        passengersRecyclerView.setAdapter(passengerAdapter);
    }
}
