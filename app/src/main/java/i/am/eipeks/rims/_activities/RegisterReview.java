package i.am.eipeks.rims._activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import i.am.eipeks.rims.Constants;
import i.am.eipeks.rims.R;
import i.am.eipeks.rims._adapters.PassengerInformationSection;
import i.am.eipeks.rims._adapters.TripInformationSection;
import i.am.eipeks.rims._adapters.VehicleInformationSection;
import i.am.eipeks.rims._classes._auth_class._auth_pojo.AuthPassenger;
import i.am.eipeks.rims._classes._auth_class._json_response.JSONResponseTrip;
import i.am.eipeks.rims._classes._model_class.Driver;
import i.am.eipeks.rims._classes._model_class.Passenger;
import i.am.eipeks.rims._classes._model_class.SubTrip;
import i.am.eipeks.rims._classes._model_class.Trip;
import i.am.eipeks.rims._classes._model_class.Vehicle;
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
    private Auth auth;

    private RelativeLayout loadingLayout;

    private List<Passenger> passengers = new ArrayList<>();

    private boolean backPressedOnce = false;

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

        Vehicle vehicle = new Vehicle(getIntent().getStringExtra(Constants.INTENT_VEHICLE_INFORMATION_JOURNEY));
        Trip trip = new Trip(getIntent().getStringExtra(Constants.INTENT_TRIP_INFORMATION_JOURNEY));
        Driver driver = new Driver(getIntent().getStringExtra(Constants.INTENT_DRIVER_INFORMATION_JOURNEY));

        getTrip();

        RecyclerView vehicleRecycleView = (RecyclerView) findViewById(R.id.vehicle_information_recycler_view);
        RecyclerView tripRecycleView = (RecyclerView) findViewById(R.id.trip_information_recycler_view);
        RecyclerView passengersRecycleView = (RecyclerView) findViewById(R.id.passengers_information_recycler_view);

        SectionedRecyclerViewAdapter passengerAdapter = new SectionedRecyclerViewAdapter();
        SectionedRecyclerViewAdapter vehicleAdapter = new SectionedRecyclerViewAdapter();
        SectionedRecyclerViewAdapter tripAdapter = new SectionedRecyclerViewAdapter();

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

    @SuppressWarnings("ConstantConditions")
    private void getTrip(){
        loadingLayout.setVisibility(View.VISIBLE);
        auth.getTrip(SessionUtils.getCurrentTripId(),
                "Bearer " + SessionUtils.getAppToken()).enqueue(new Callback<JSONResponseTrip>() {
            @Override
            public void onResponse(@NonNull Call<JSONResponseTrip> call, @NonNull Response<JSONResponseTrip> response) {
                loadingLayout.setVisibility(View.GONE);
                switch (response.code()){
                    case 200:
                        for (AuthPassenger passenger: response.body().getTrip().getPassengers()){
                            passengers.add(passenger.getPassenger());
                        }
                        break;
                    default:
                        if (response.body().getMessage() != null){
                            Snackbar.make(loadingLayout, response.body().getMessage(), Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Retry", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getTrip();
                                        }
                                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                        } else {
                            Snackbar.make(loadingLayout, response.message(), Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Retry", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getTrip();
                                        }
                                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                        }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JSONResponseTrip> call, @NonNull Throwable t) {
                loadingLayout.setVisibility(View.GONE);
                if (NetworkUtils.isPhoneConnected(RegisterReview.this)){
                    Snackbar.make(loadingLayout, "Phone not connected.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getTrip();
                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                } else {
                    Snackbar.make(loadingLayout, "Unknown error.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getTrip();
                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                }
            }
        });
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
                                makeToast("Trip added.");
                            }
                        }).create().show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!backPressedOnce){
            backPressedOnce = true;
            new AlertDialog.Builder(this)
                    .setTitle("Cancel trip")
                    .setMessage("All trip information would be lost")
                    .setPositiveButton("Cancel trip", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteTrip(loadingLayout);
                        }
                    })
                    .setNegativeButton("Mistake, Go back", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            backPressedOnce = false;
                        }
                    }).create().show();
        }
    }

    private void deleteTrip(final View view){
        auth.deleteTrip(SessionUtils.getCurrentTripId(),"Bearer " + SessionUtils.getAppToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                switch (response.code()){
                    case 200:
                        break;
                    default:
                        Snackbar.make(view, "Couldn't cancel trip. ", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        deleteTrip(view);
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
                                    deleteTrip(view);
                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                } else {
                    Snackbar.make(view, "Trip deletion: Unknown error. ", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    deleteTrip(view);
                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                }
            }
        });
    }

    @SuppressWarnings("SameParameterValue")
    private void makeToast(String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
