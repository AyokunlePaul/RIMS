package i.am.eipeks.rims._fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import i.am.eipeks.rims.Constants;
import i.am.eipeks.rims.R;
import i.am.eipeks.rims._activities.RegisterPassenger;
import i.am.eipeks.rims._classes._auth_class._json_response._register.JSONResponseTripRegister;
import i.am.eipeks.rims._utils.APIUtils;
import i.am.eipeks.rims._classes._auth_class._auth_pojo.AuthVehicle;
import i.am.eipeks.rims._classes._auth_class._json_response.JSONResponseVehicle;
import i.am.eipeks.rims._network.Auth;


import i.am.eipeks.rims._utils.NetworkUtils;
import i.am.eipeks.rims._utils.SessionUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Journey extends Fragment implements
        AdapterView.OnItemSelectedListener {

    private String driverIntent, vehicleIntent, tripIntent, displacement;

    private Spinner departureState, departurePark, routeFrom, routeTo;
    private Button continueToLoad;

    private RelativeLayout loadingLayout;
    private TextInputLayout driver_sNameTextInputLayout, driver_sPhoneTextInputLayout, vehicleNumberTextInputLayout;

    private EditText driver_sName, driver_sPhone, vehicleNumber;

    private AuthVehicle authVehicle;
    private AlertDialog dialog;

    private Auth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_journey, container, false);

        loadingLayout = rootView.findViewById(R.id.loading_layout);

        auth = APIUtils.getAuth();

        continueToLoad = rootView.findViewById(R.id.continue_to_trip);

        driver_sNameTextInputLayout = rootView.findViewById(R.id.driver_s_name_input_layout);
        driver_sPhoneTextInputLayout = rootView.findViewById(R.id.driver_s_phone_number_input_layout);
        vehicleNumberTextInputLayout = rootView.findViewById(R.id.vehicle_registration_input_layout);

        driver_sName = rootView.findViewById(R.id.driver_s_name);
        driver_sPhone = rootView.findViewById(R.id.driver_s_phone_number);
        vehicleNumber = rootView.findViewById(R.id.vehicle_registration);

        departureState = rootView.findViewById(R.id.departure_state);
        departurePark = rootView.findViewById(R.id.departure_park);
        routeFrom = rootView.findViewById(R.id.route_from);
        routeTo = rootView.findViewById(R.id.route_to);

        initializeSpinners();

        continueToLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getVehicleInformation(view);
            }
        });
        return rootView;
    }

    public void initializeSpinners(){
        ArrayAdapter stateAdapter = ArrayAdapter.createFromResource(getContext(), R.array.departure_states, R.layout.spinner_item);
        stateAdapter.setDropDownViewResource(R.layout.spinner_checked_text);
        departureState.setAdapter(stateAdapter);

        ArrayAdapter parkAdapter = ArrayAdapter.createFromResource(getContext(), R.array.lagos_parks, R.layout.spinner_item);
        parkAdapter.setDropDownViewResource(R.layout.spinner_checked_text);
        departurePark.setAdapter(parkAdapter);

        ArrayAdapter routeToAdapter = ArrayAdapter.createFromResource(getContext(), R.array.departure_states, R.layout.spinner_item);
        routeToAdapter.setDropDownViewResource(R.layout.spinner_checked_text);
        routeTo.setAdapter(routeToAdapter);

        ArrayAdapter routeFromAdapter = ArrayAdapter.createFromResource(getContext(), R.array.departure_states, R.layout.spinner_item);
        routeFromAdapter.setDropDownViewResource(R.layout.spinner_checked_text);
        routeFrom.setAdapter(routeFromAdapter);

        departureState.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        switch (adapterView.getId()){
            case R.id.departure_state:
                switch ((String)departureState.getItemAtPosition(position)){
                    case "Ondo":
                        ArrayAdapter ondoDepartureParkAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.ondo_parks,
                                R.layout.spinner_item);
                        ondoDepartureParkAdapter.setDropDownViewResource(R.layout.spinner_checked_text);
                        departurePark.setAdapter(ondoDepartureParkAdapter);
                        break;
                    case "Lagos":
                        ArrayAdapter lagosDepartureParkAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.lagos_parks,
                                R.layout.spinner_item);
                        lagosDepartureParkAdapter.setDropDownViewResource(R.layout.spinner_checked_text);
                        departurePark.setAdapter(lagosDepartureParkAdapter);
                        break;
                    case "Osun":
                        ArrayAdapter osunDepartureParkAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.osun_parks,
                                R.layout.spinner_item);
                        osunDepartureParkAdapter.setDropDownViewResource(R.layout.spinner_checked_text);
                        departurePark.setAdapter(osunDepartureParkAdapter);
                        break;
                }
                break;
        }
    }

    @SuppressWarnings("InflateParams")
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @SuppressWarnings("ConstantConditions")
    private void getVehicle(final View view, final String vehicleId){
        continueToLoad.setEnabled(false);
        continueToLoad.setClickable(false);
        auth.getVehicle(Integer.valueOf(vehicleId), "Bearer " + SessionUtils.getAppToken()).enqueue(new Callback<JSONResponseVehicle>() {
            @Override
            public void onResponse(@NonNull Call<JSONResponseVehicle> call, @NonNull Response<JSONResponseVehicle> response) {
                if (response.isSuccessful() && response.body().getStatus() == 200){
                    loadingLayout.setVisibility(View.GONE);
                    authVehicle = response.body().getAuthVehicle();
                    SessionUtils.setCurrentVehicleId(authVehicle.getId());
                    dialog.show();
                } else {
                    continueToLoad.setEnabled(true);
                    continueToLoad.setClickable(true);
                    loadingLayout.setVisibility(View.GONE);
                    switch (response.code()){
                        case 404:
                            //noinspection deprecation
                            Snackbar.make(view, "Error getting vehicle number.", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Retry", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            loadingLayout.setVisibility(View.VISIBLE);
                                            getVehicle(view, vehicleId);
                                        }
                                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                            break;
                        default:
                            Snackbar.make(view, "Vehicle not registered.", Snackbar.LENGTH_LONG).show();
                            continueToLoad.setEnabled(true);
                            continueToLoad.setClickable(true);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JSONResponseVehicle> call, @NonNull Throwable t) {
                continueToLoad.setEnabled(true);
                continueToLoad.setClickable(true);
                //noinspection deprecation
                Snackbar.make(view, "Network error.", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadingLayout.setVisibility(View.VISIBLE);
                                getVehicle(view, vehicleId);
                            }
                        }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                loadingLayout.setVisibility(View.GONE);
            }
        });
    }

    public void getVehicleInformation(final View view){
        final String vehicleNumberString;

        String dateAndTime = DateFormat.getDateTimeInstance().format(new Date());
        String calendarDate = Integer.toString(Calendar.getInstance().get(Calendar.DATE));
        String calendarMonth = Integer.toString(Calendar.getInstance().get(Calendar.MONTH));
        String calendarYear = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

        String departure = String.format("%s, %s", departureState.getSelectedItem().toString(), departurePark.getSelectedItem().toString());
        displacement = String.format("%s - %s", routeFrom.getSelectedItem().toString(), routeTo.getSelectedItem().toString());

        driver_sNameTextInputLayout.setErrorEnabled(false);
        driver_sPhoneTextInputLayout.setErrorEnabled(false);
        vehicleNumberTextInputLayout.setErrorEnabled(false);

        @SuppressLint("InflateParams")
        final View customView = LayoutInflater.from(getContext()).inflate(R.layout.vehicle_information_dialog, null);

        final TextView vehicleName, vehicleMake, vehicleCapacity, vehicleWeight, vehicleEngine, vehicleRTSSS;

        vehicleName = customView.findViewById(R.id.vehicle_name);
        vehicleMake = customView.findViewById(R.id.vehicle_make);
        vehicleCapacity = customView.findViewById(R.id.vehicle_capacity);
        vehicleWeight = customView.findViewById(R.id.vehicle_weight);
        vehicleEngine = customView.findViewById(R.id.vehicle_engine);
        vehicleRTSSS = customView.findViewById(R.id.vehicle_rt_sss);

        dialog = new AlertDialog.Builder(getContext())
                .setTitle("Vehicle Information").setView(customView)
                .setPositiveButton("CONTINUE", null)
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        continueToLoad.setEnabled(true);
                        continueToLoad.setClickable(true);
                        dialogInterface.dismiss();
                    }
                }).setCancelable(false).create();

        if (TextUtils.isEmpty(driver_sName.getText()) ||
                TextUtils.isEmpty(driver_sPhone.getText()) ||
                TextUtils.isEmpty(vehicleNumber.getText())){
            driver_sNameTextInputLayout.setErrorEnabled(true);
            driver_sPhoneTextInputLayout.setErrorEnabled(true);
            if (TextUtils.isEmpty(vehicleNumber.getText())){
                vehicleNumberTextInputLayout.setError("Field is empty");
            } else if (TextUtils.isEmpty(driver_sName.getText())){
                driver_sNameTextInputLayout.setError("Field is empty");
            } else {
                driver_sPhoneTextInputLayout.setError("Field is empty");
            }
        } else if (!TextUtils.isDigitsOnly(vehicleNumber.getText())){
            vehicleNumberTextInputLayout.setError("Invalid vehicle number type");
        } else {
            driverIntent = String.format("%s_%s", driver_sName.getText().toString().trim(), driver_sPhone.getText().toString().trim());
            tripIntent = String.format("%s_%s_%s_%s_%s_%s",
                    dateAndTime, displacement, departure, calendarDate, calendarMonth, calendarYear);

            vehicleNumberString = vehicleNumber.getText().toString();

            getVehicle(view, vehicleNumberString);

            loadingLayout.setVisibility(View.VISIBLE);

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {

                @Override
                public void onShow(DialogInterface dialogInterface) {
                    vehicleName.setText(authVehicle.getVehicleName());
                    vehicleMake.setText(authVehicle.getVehicleMake());
                    vehicleCapacity.setText(String.valueOf(authVehicle.getVehicleCapacity()));
                    vehicleWeight.setText(authVehicle.getVehicleWeight());
                    vehicleEngine.setText(authVehicle.getVehicleEngine());
                    vehicleRTSSS.setText(authVehicle.getVehicleRtSss());

                    vehicleIntent = String.format("%s_%s_%s_%s_%s_%s_%s",
                            authVehicle.getVehicleName(), authVehicle.getVehicleMake(), authVehicle.getVehicleCapacity(),
                            authVehicle.getVehicleWeight(), authVehicle.getVehicleEngine(), authVehicle.getVehicleRtSss(),
                            authVehicle.getVehicleRegistrationNumber());

                    Button button = ((AlertDialog) dialogInterface).getButton(AlertDialog.BUTTON_POSITIVE);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            registerTrip(view);
                        }
                    });
                }

            });
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void registerTrip(final View view){
        loadingLayout.setVisibility(View.VISIBLE);
        auth.addTrip("Bearer " + SessionUtils.getAppToken(), SessionUtils.getCurrentVehicleId(),
                displacement, getCurrentDateTime()).enqueue(new Callback<JSONResponseTripRegister>() {
            @Override
            public void onResponse(@NonNull Call<JSONResponseTripRegister> call, @NonNull Response<JSONResponseTripRegister> response) {
                loadingLayout.setVisibility(View.GONE);
                switch (response.code()){
                    case 200:
//                        makeToast("Trip registered");
                        SessionUtils.setCurrentTripId(response.body().getTrip().getTripId());
                        addDriver(view);
                        makeToast(String.valueOf(SessionUtils.getCurrentTripId()));
                        break;
                    case 400:
                        continueToLoad.setEnabled(true);
                        continueToLoad.setClickable(true);
                        makeToast(String.valueOf(response.code()));
                        if (response.body() != null){
                            if (response.body().getMessage() != null){
                                Snackbar.make(view, response.body().getMessage(), Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Retry", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                registerTrip(view);
                                            }
                                        }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                            } else {
                                makeToast("Message is null");
                            }
                        } else {
                            Snackbar.make(view, response.message(), Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Retry", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            registerTrip(view);
                                        }
                                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                        }
                        break;
                    case 500:
                        continueToLoad.setEnabled(true);
                        continueToLoad.setClickable(true);
                        Snackbar.make(view, "Internal server error.", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        registerTrip(view);
                                    }
                                }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                        break;
                    default:
                        continueToLoad.setEnabled(true);
                        continueToLoad.setClickable(true);
                        Snackbar.make(view, "Unexpected error occurred.", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        registerTrip(view);
                                    }
                                }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                        break;
                }
            }

            @Override
            public void onFailure(@NonNull Call<JSONResponseTripRegister> call, @NonNull Throwable t) {
                loadingLayout.setVisibility(View.GONE);
                continueToLoad.setEnabled(true);
                continueToLoad.setClickable(true);
                if (NetworkUtils.isPhoneConnected(getActivity())){
                    Snackbar.make(view, "Phone not connected.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    registerTrip(view);
                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                } else {
                    Snackbar.make(view, "Unknown error. Couldn't register trip.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    registerTrip(view);
                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                }
            }
        });
    }

    private void addDriver(final View view){
        if (!(loadingLayout.getVisibility() == View.VISIBLE)){
            loadingLayout.setVisibility(View.VISIBLE);
        }
        auth.sendDriver(driver_sName.getText().toString(), driver_sPhone.getText().toString(),
                "Bearer " + SessionUtils.getAppToken(),
                SessionUtils.getCurrentTripId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                loadingLayout.setVisibility(View.GONE);
                switch (response.code()){
                    case 200:
                        makeToast("Trip driver registered");
                        startActivity(new Intent(getContext(), RegisterPassenger.class)
                                .putExtra(Constants.INTENT_CAPACITY_JOURNEY, authVehicle.getVehicleCapacity())
                                .putExtra(Constants.INTENT_REGISTRATION_NUMBER_JOURNEY, authVehicle.getVehicleRegistrationNumber())
                                .putExtra(Constants.INTENT_DRIVER_INFORMATION_JOURNEY, driverIntent)
                                .putExtra(Constants.INTENT_TRIP_INFORMATION_JOURNEY, tripIntent)
                                .putExtra(Constants.INTENT_VEHICLE_INFORMATION_JOURNEY, vehicleIntent));
                        getActivity().finish();
                        break;
                    default:
                        continueToLoad.setEnabled(true);
                        continueToLoad.setClickable(true);
                        Snackbar.make(view, "Couldn't upload driver information. ", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        addDriver(view);
                                    }
                                }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                loadingLayout.setVisibility(View.GONE);
                continueToLoad.setEnabled(true);
                continueToLoad.setClickable(true);
                if (NetworkUtils.isPhoneConnected(getContext())){
                    Snackbar.make(view, "Phone is not connected. ", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    addDriver(view);
                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                } else {
                    Snackbar.make(view, "Driver information upload: Unknown error. ", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    addDriver(view);
                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                }
            }
        });
    }

    private void makeToast(String message){
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @SuppressLint("SimpleDateFormat")
    private String getCurrentDateTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp(new Date().getTime()));
    }
}