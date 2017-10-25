package i.am.eipeks.rims._activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import i.am.eipeks.rims.Constants;
import i.am.eipeks.rims.R;
import i.am.eipeks.rims._adapters.PassengerInformationSection;
import i.am.eipeks.rims._adapters.TripInformationSection;
import i.am.eipeks.rims._adapters.VehicleInformationSection;
import i.am.eipeks.rims._classes.Driver;
import i.am.eipeks.rims._classes.Passenger;
import i.am.eipeks.rims._classes.SubTrip;
import i.am.eipeks.rims._classes.Trip;
import i.am.eipeks.rims._classes.Vehicle;
import i.am.eipeks.rims._database.CentralDBHelper;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class RegisterReview extends AppCompatActivity{

    private CentralDBHelper centralDB;

    private String uuid, registrationNumber, totalNumberOfPassengers, vehicleInformation, tripInformation, driverInformation;

    private ArrayList<Passenger> passengers;

    private SectionedRecyclerViewAdapter passengerAdapter, vehicleAdapter, tripAdapter;

    private RecyclerView vehicleRecycleView, passengersRecycleView, tripRecycleView;

    private Vehicle vehicle;
    private Trip trip;
    private Driver driver;

    @SuppressWarnings("NullPointerException")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);

        getSupportActionBar().setSubtitle("Current Trip Information");
        getSupportActionBar().setTitle("RegisterReview Trip");

        vehicleInformation = getIntent().getStringExtra(Constants.INTENT_VEHICLE_INFORMATION_JOURNEY);
        tripInformation = getIntent().getStringExtra(Constants.INTENT_TRIP_INFORMATION_JOURNEY);
        driverInformation = getIntent().getStringExtra(Constants.INTENT_DRIVER_INFORMATION_JOURNEY);
        uuid = getIntent().getStringExtra(RegisterPassenger.INTENT_UUID);
        totalNumberOfPassengers = getIntent().getStringExtra(RegisterPassenger.INTENT_TOTAL_NUMBER_OF_PASSENGERS);
        registrationNumber = getIntent().getStringExtra(RegisterPassenger.INTENT_REGISTRATION_NUMBER);

        vehicleRecycleView = (RecyclerView) findViewById(R.id.vehicle_information_recycler_view);
        tripRecycleView = (RecyclerView) findViewById(R.id.trip_information_recycler_view);
        passengersRecycleView = (RecyclerView) findViewById(R.id.passengers_information_recycler_view);

        passengerAdapter = new SectionedRecyclerViewAdapter();
        vehicleAdapter = new SectionedRecyclerViewAdapter();
        tripAdapter = new SectionedRecyclerViewAdapter();

        centralDB = new CentralDBHelper(this);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                vehicleRecycleView.setLayoutManager(new LinearLayoutManager(RegisterReview.this));
                tripRecycleView.setLayoutManager(new LinearLayoutManager(RegisterReview.this));
                passengersRecycleView.setLayoutManager(new LinearLayoutManager(RegisterReview.this));
                passengersRecycleView.addItemDecoration(new DividerItemDecoration(RegisterReview.this, DividerItemDecoration.VERTICAL));
            }

            @Override
            protected Void doInBackground(Void... voids) {
                passengers = centralDB.getPassengers(uuid);
                vehicle = new Vehicle(vehicleInformation);
                trip = new Trip(tripInformation);
                driver = new Driver(driverInformation);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                passengerAdapter.addSection(new PassengerInformationSection(passengers, "Passengers"));
                vehicleAdapter.addSection(new VehicleInformationSection(vehicle, "AuthVehicle Information"));
                tripAdapter.addSection(new TripInformationSection(RegisterReview.this, new SubTrip(trip, driver), "Trip Information"));

                passengersRecycleView.setAdapter(passengerAdapter);
                vehicleRecycleView.setAdapter(vehicleAdapter);
                tripRecycleView.setAdapter(tripAdapter);
            }
        }.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.review_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.delete:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("Delete this trip")
                        .setMessage("Are you sure you want to delete this trip? \nAll trip information would be lost")
                        .setPositiveButton("Delete", null)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button deleteButton = ((AlertDialog) dialogInterface).getButton(AlertDialog.BUTTON_POSITIVE);
                        deleteButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(final View view) {
                                new AsyncTask<Void, Void, Void>() {

                                    @Override
                                    protected void onPreExecute() {
                                        Toast.makeText(RegisterReview.this, "Deleting...", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    protected Void doInBackground(Void... voids) {
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Void aVoid) {
                                        Toast.makeText(RegisterReview.this, "Deleted.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterReview.this, Main.class));
                                    }
                                }.execute();
                            }
                        });
                    }
                });
                alertDialog.show();
                break;

            case R.id.send:
                if (!(Integer.valueOf(totalNumberOfPassengers) > 0)){
                    Toast.makeText(this, "Enter at least one passenger", Toast.LENGTH_SHORT).show();
                } else {
                    new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected void onPreExecute() {

                        }

                        @Override
                        protected Void doInBackground(Void... voids) {
                            centralDB.addTrip(trip, uuid);
                            centralDB.addVehicle(vehicle, uuid);
                            centralDB.addDriver(driver, uuid);
                            centralDB.modifyTrip(totalNumberOfPassengers, uuid);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            Toast.makeText(RegisterReview.this, "Done", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterReview.this, Main.class));
                        }
                    }.execute();
                }
                break;
        }

        return true;
    }
}
