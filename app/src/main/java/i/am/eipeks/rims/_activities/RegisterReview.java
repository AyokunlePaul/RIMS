package i.am.eipeks.rims._activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

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
import i.am.eipeks.rims._network.Auth;
import i.am.eipeks.rims._utils.APIUtils;
import i.am.eipeks.rims._utils.NetworkUtils;
import i.am.eipeks.rims._utils.SessionUtils;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterReview extends AppCompatActivity{

    private CentralDBHelper centralDB;

    private String uuid, registrationNumber, totalNumberOfPassengers, vehicleInformation, tripInformation, driverInformation;

    private ArrayList<Passenger> passengers;

    private Auth auth;

    private SectionedRecyclerViewAdapter passengerAdapter, vehicleAdapter, tripAdapter;

    private RecyclerView vehicleRecycleView, passengersRecycleView, tripRecycleView;

    private RelativeLayout loadingLayout;

    private Vehicle vehicle;
    private Trip trip;
    private Driver driver;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);

        auth = APIUtils.getAuth();

        loadingLayout = (RelativeLayout) findViewById(R.id.loading_layout);

        getSupportActionBar().setSubtitle("Current Trip Information");
        getSupportActionBar().setTitle("RegisterReview Trip");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        vehicleRecycleView.setLayoutManager(new LinearLayoutManager(RegisterReview.this));
        tripRecycleView.setLayoutManager(new LinearLayoutManager(RegisterReview.this));
        passengersRecycleView.setLayoutManager(new LinearLayoutManager(RegisterReview.this));
        passengersRecycleView.addItemDecoration(new DividerItemDecoration(RegisterReview.this, DividerItemDecoration.VERTICAL));

        passengerAdapter.addSection(new PassengerInformationSection(passengers, "Passengers"));
        vehicleAdapter.addSection(new VehicleInformationSection(vehicle, "Vehicle Information"));
        tripAdapter.addSection(new TripInformationSection(RegisterReview.this, new SubTrip(trip, driver), "Trip Information"));

        passengersRecycleView.setAdapter(passengerAdapter);
        vehicleRecycleView.setAdapter(vehicleAdapter);
        tripRecycleView.setAdapter(tripAdapter);
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
                                deleteTrip(view);
                            }
                        });
                    }
                });
                alertDialog.show();
                return true;

            case R.id.send:
                if (!(Integer.valueOf(totalNumberOfPassengers) > 0)){
                    Toast.makeText(this, "Enter at least one passenger", Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(this)
                            .setMessage(String.format(Locale.ENGLISH, "%s", "Are you sure you wanna add trip?"))
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        new AsyncTask<Void, Void, Void>() {
//                                            @Override
//                                            protected void onPreExecute() {
//
//                                            }
//
//                                            @Override
//                                            protected Void doInBackground(Void... voids) {
//                                                centralDB.addTrip(trip, uuid);
//                                                centralDB.addVehicle(vehicle, uuid);
//                                                centralDB.addDriver(driver, uuid);
//                                                centralDB.modifyTrip(totalNumberOfPassengers, uuid);
//                                                return null;
//                                            }
//
//                                            @Override
//                                            protected void onPostExecute(Void aVoid) {
//                                                Toast.makeText(RegisterReview.this, "Done", Toast.LENGTH_SHORT).show();
//                                                startActivity(new Intent(RegisterReview.this, Main.class));
//                                            }
//                                        }.execute();
                                    }
                            }).create().show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteTrip(final View view){
        auth.deleteTrip(uuid, "Bearer " + SessionUtils.getAppToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                switch (response.code()){
                    case 200:
                        deleteDriver(view);
                        break;
                    default:
                        Snackbar.make(view, "Couldn't add trip to database. ", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                if (NetworkUtils.isPhoneConnected(RegisterReview.this)){
                    Snackbar.make(view, "Phone is not connected. ", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                } else {
                    Snackbar.make(view, "Trip deletion: Unknown error. ", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                }
            }
        });
    }

    private void deleteDriver(final View view){
        auth.deleteDriver(uuid, "Bearer " + SessionUtils.getAppToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                switch (response.code()){
                    case 200:
                        break;
                    default:
                        Snackbar.make(view, "Couldn't add trip to database. ", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                if (NetworkUtils.isPhoneConnected(RegisterReview.this)){
                    Snackbar.make(view, "Phone is not connected. ", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                } else {
                    Snackbar.make(view, "Trip deletion: Unknown error. ", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                }
            }
        });
    }

}
