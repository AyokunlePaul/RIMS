package i.am.eipeks.rims._fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import i.am.eipeks.rims.Constants;
import i.am.eipeks.rims.R;
import i.am.eipeks.rims.Utils.APIUtils;
import i.am.eipeks.rims._activities.RegisterPassenger;
import i.am.eipeks.rims._classes._network.AuthVehicle;
import i.am.eipeks.rims._network.Auth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Journey extends Fragment implements
        AdapterView.OnItemSelectedListener {

    private String driverIntent, vehicleIntent, tripIntent;

    private Spinner departureState, departurePark, routeFrom, routeTo;

//    private VehicleDatabaseHelper helper;

    private RelativeLayout loadingLayout;
    private TextInputLayout driver_sNameTextInputLayout, driver_sPhoneTextInputLayout, vehicleNumberTextInputLayout;

    private EditText driver_sName, driver_sPhone, vehicleNumber;

//    private CentralDBHelper centralDB;

//    private Vehicle currentVehicle;
    private AuthVehicle authVehicle;
    private AlertDialog dialog;

    private Auth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_journey, container, false);

        loadingLayout = rootView.findViewById(R.id.loading_layout);

        auth = APIUtils.getAuth();

        Button continueToLoad = rootView.findViewById(R.id.continue_to_trip);

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

//        helper  = new VehicleDatabaseHelper(getContext());

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

    private void getVehicle(final View view, final String vehicleNumber){
        auth.getVehicle(vehicleNumber).enqueue(new Callback<AuthVehicle>() {
            @Override
            public void onResponse(@NonNull Call<AuthVehicle> call, @NonNull Response<AuthVehicle> response) {
                if (response.isSuccessful()){
                    authVehicle = response.body();
                    dialog.show();
                } else {
                    Snackbar.make(view, "Cannot get vehicle details", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getVehicle(view, vehicleNumber);
                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthVehicle> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "onFailure", Toast.LENGTH_SHORT).show();
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
        String displacement = String.format("%s - %s", routeFrom.getSelectedItem().toString(), routeTo.getSelectedItem().toString());

        driver_sNameTextInputLayout.setErrorEnabled(false);
        driver_sPhoneTextInputLayout.setErrorEnabled(false);
        vehicleNumberTextInputLayout.setErrorEnabled(false);

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
        } else {
            driverIntent = String.format("%s_%s", driver_sName.getText().toString().trim(), driver_sPhone.getText().toString().trim());
            tripIntent = String.format("%s_%s_%s_%s_%s_%s",
                    dateAndTime, displacement, departure, calendarDate, calendarMonth, calendarYear);

            vehicleNumberString = vehicleNumber.getText().toString();

            getVehicle(view, vehicleNumberString);

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {

                @Override
                public void onShow(DialogInterface dialogInterface) {
                    vehicleName.setText(authVehicle.getName());
                    vehicleMake.setText(authVehicle.getMake());
                    vehicleCapacity.setText(authVehicle.getCapacity());
                    vehicleWeight.setText(authVehicle.getWeight());
                    vehicleEngine.setText(authVehicle.getEngine());
                    vehicleRTSSS.setText(authVehicle.getRtSss());

                    vehicleIntent = String.format("%s_%s_%s_%s_%s_%s_%s",
                            authVehicle.getName(), authVehicle.getMake(), authVehicle.getCapacity(),
                            authVehicle.getWeight(), authVehicle.getEngine(), authVehicle.getRtSss(),
                            authVehicle.getRegistrationNumber());

                    Button button = ((AlertDialog) dialogInterface).getButton(AlertDialog.BUTTON_POSITIVE);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();

                            new AsyncTask<Void, Void, Void>() {

                                @Override
                                protected void onPreExecute() {
                                    loadingLayout.setVisibility(View.VISIBLE);
                                }

                                @Override
                                protected Void doInBackground(Void... voids) {
                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Void aVoid) {
                                    loadingLayout.setVisibility(View.GONE);
                                    startActivity(new Intent(getContext(), RegisterPassenger.class)
                                    .putExtra(Constants.INTENT_CAPACITY_JOURNEY, authVehicle.getCapacity())
                                    .putExtra(Constants.INTENT_REGISTRATION_NUMBER_JOURNEY, authVehicle.getRegistrationNumber())
                                    .putExtra(Constants.INTENT_DRIVER_INFORMATION_JOURNEY, driverIntent)
                                    .putExtra(Constants.INTENT_TRIP_INFORMATION_JOURNEY, tripIntent)
                                    .putExtra(Constants.INTENT_VEHICLE_INFORMATION_JOURNEY, vehicleIntent));
                                    getActivity().finish();
                                }
                            }.execute();
                        }
                    });
                }

            });
        }
    }
}