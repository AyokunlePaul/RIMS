package i.am.eipeks.rims._activities;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import i.am.eipeks.rims.Constants;
import i.am.eipeks.rims.R;
import i.am.eipeks.rims._adapters.SeatNumberAdapter;
import i.am.eipeks.rims._classes.Passenger;
import i.am.eipeks.rims._database.CentralDBHelper;

public class RegisterPassenger extends AppCompatActivity{

    public static final String INTENT_TOTAL_NUMBER_OF_PASSENGERS = "Total";
    public static final String INTENT_UUID = "UUID";
    public static final String INTENT_REGISTRATION_NUMBER = "registrationNumber";

    private int counter =  1, total = 0, radio;
    private int capacity;
    private String uuid,totalNumberOfPassenger, registrationNumber, vehicleInformation, tripInformation, driverInformation, selectedSeats;

    private EditText passengerName, passengerAddress, passengerPhone, nextOfKin, kinPhone;
    private TextInputLayout passengerNameTextInput, passengerAddressTextInput, passengerPhoneTextInput, nextOfKinTextInput, kinPhoneTextInput;
    private TextView currentSeat;

    private SeatNumberAdapter seatNumberAdapter;

//    private Spinner spinner;

    private Passenger passenger;

    private CentralDBHelper centralDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_passenger);

        vehicleInformation = getIntent().getStringExtra(Constants.INTENT_VEHICLE_INFORMATION_JOURNEY);
        tripInformation = getIntent().getStringExtra(Constants.INTENT_TRIP_INFORMATION_JOURNEY);
        driverInformation = getIntent().getStringExtra(Constants.INTENT_DRIVER_INFORMATION_JOURNEY);
        capacity = Integer.parseInt(getIntent().getStringExtra(Constants.INTENT_CAPACITY_JOURNEY));

        TextView capacityTextView = (TextView) findViewById(R.id.capacity);

        passengerName = (EditText) findViewById(R.id.passenger_s_name);
        passengerAddress = (EditText) findViewById(R.id.passenger_s_address);
        passengerPhone = (EditText) findViewById(R.id.passenger_s_phone_number);
        nextOfKin = (EditText) findViewById(R.id.kin_s_name);
        kinPhone = (EditText) findViewById(R.id.kin_s_phone);

        passengerNameTextInput = (TextInputLayout) findViewById(R.id.passenger_s_name_input_layout);
        passengerAddressTextInput = (TextInputLayout) findViewById(R.id.passenger_s_address_input_layout);
        passengerPhoneTextInput = (TextInputLayout) findViewById(R.id.passenger_s_phone_number_input_layout);
        nextOfKinTextInput = (TextInputLayout) findViewById(R.id.kin_s_name_input_layout);
        kinPhoneTextInput = (TextInputLayout) findViewById(R.id.kin_s_phone_input_layout);

        RecyclerView seatNumbers = (RecyclerView) findViewById(R.id.seat_numbers);

        seatNumberAdapter = new SeatNumberAdapter(this, capacity, selectedSeats);

        seatNumbers.setAdapter(seatNumberAdapter);
        seatNumbers.setLayoutManager(new GridLayoutManager(this, 10));
        seatNumbers.addItemDecoration(new GridSpacing(10, dpToPx(2), true));

        RadioGroup sex = (RadioGroup) findViewById(R.id.sex);
        sex.getChildAt(0).setSelected(true);

        centralDB = new CentralDBHelper(this);

        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                radio = radioGroup.getCheckedRadioButtonId();
            }
        });

        if (savedInstanceState != null){
            uuid = savedInstanceState.getString("uuid-value");
        } else {
            uuid = UUID.randomUUID().toString();
        }
        registrationNumber = getIntent().getStringExtra(Constants.INTENT_REGISTRATION_NUMBER_JOURNEY);

        currentSeat = (TextView) findViewById(R.id.seat_count);

        currentSeat.setText(String.valueOf(counter));
        capacityTextView.setText(String.valueOf(capacity));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vehicle_information_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        passengerNameTextInput.setErrorEnabled(false);
        passengerAddressTextInput.setErrorEnabled(false);
        passengerPhoneTextInput.setErrorEnabled(false);
        nextOfKinTextInput.setErrorEnabled(false);
        kinPhoneTextInput.setErrorEnabled(false);

        String sex = null;

        switch (radio){
            case R.id.male:
                sex = "Male";
                break;
            case R.id.female:
                sex = "Female";
                break;
        }

        switch (item.getItemId()){
            case R.id.next_menu:
                if (item.getTitle().equals("DONE")){
                    totalNumberOfPassenger = Integer.toString(total);
                    startActivity(new Intent(this, RegisterReview.class)
                            .putExtra(INTENT_TOTAL_NUMBER_OF_PASSENGERS, totalNumberOfPassenger)
                            .putExtra(INTENT_UUID, uuid)
                            .putExtra(INTENT_REGISTRATION_NUMBER, registrationNumber)
                            .putExtra(Constants.CALLING_ACTIVITY, Constants.REGISTER_PASSENGER_ACTIVITY)
                            .putExtra(Constants.INTENT_DRIVER_INFORMATION_JOURNEY, driverInformation)
                            .putExtra(Constants.INTENT_TRIP_INFORMATION_JOURNEY, tripInformation)
                            .putExtra(Constants.INTENT_VEHICLE_INFORMATION_JOURNEY, vehicleInformation));
                } else {
                    if (TextUtils.isEmpty(passengerName.getText()) || TextUtils.isEmpty(passengerPhone.getText())
                            || TextUtils.isEmpty(passengerName.getText()) || TextUtils.isEmpty(passengerPhone.getText())
                            || TextUtils.isEmpty(passengerPhone.getText())){

                        passengerNameTextInput.setErrorEnabled(true);
                        passengerAddressTextInput.setErrorEnabled(true);
                        passengerPhoneTextInput.setErrorEnabled(true);
                        nextOfKinTextInput.setErrorEnabled(true);
                        kinPhoneTextInput.setErrorEnabled(true);

                        if (TextUtils.isEmpty(passengerName.getText())){
                            passengerNameTextInput.setError("Field is empty");
                        } else if (TextUtils.isEmpty(passengerPhone.getText())){
                            passengerPhoneTextInput.setError("Field is empty");
                        } else if (TextUtils.isEmpty(passengerPhone.getText())){
                            passengerAddressTextInput.setError("Field is empty");
                        } else if (TextUtils.isEmpty(passengerPhone.getText())){
                            nextOfKinTextInput.setError("Field is empty");
                        } else {
                            kinPhoneTextInput.setError("Field is empty");
                        }

                    } else {
                        if (seatNumberAdapter.getSelectedSeat() == 0){
                            Toast.makeText(this, "No seat selected", Toast.LENGTH_SHORT).show();
                        } else {
                            passenger = new Passenger(passengerName.getText().toString(), passengerPhone.getText().toString(), sex,
                                    passengerAddress.getText().toString(), nextOfKin.getText().toString(), String.valueOf(seatNumberAdapter.getSelectedSeat()),
                                    kinPhone.getText().toString());
                            new AsyncTask<Void, Void, Void>() {

                                @Override
                                protected void onPreExecute() {
                                    Toast.makeText(RegisterPassenger.this, "Adding passenger...", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                protected Void doInBackground(Void... voids) {
                                    centralDB.addPassenger(passenger, uuid);
                                    total += 1;
                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Void aVoid) {
                                    Toast.makeText(RegisterPassenger.this, "Done", Toast.LENGTH_SHORT).show();
//                                integers.remove(spinner.getSelectedItem());
//                                integerArrayAdapter.notifyDataSetChanged();
                                    passengerName.setText("");
                                    passengerPhone.setText("");
                                    passengerAddress.setText("");
                                    nextOfKin.setText("");
                                    kinPhone.setText("");
                                    counter += 1;
                                    currentSeat.setText(String.valueOf(counter));
                                    Toast.makeText(RegisterPassenger.this, centralDB.getPassengers(uuid).get(0).getPassengerName(), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(RegisterPassenger.this, String.valueOf(total), Toast.LENGTH_SHORT).show();
                                }
                            }.execute();

                            if (total == capacity){
                                item.setTitle("DONE");
                            }
                        }
                    }

                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void gotoReview(View view){
        totalNumberOfPassenger = Integer.toString(total);
        startActivity(new Intent(this, RegisterReview.class)
                .putExtra(INTENT_TOTAL_NUMBER_OF_PASSENGERS, totalNumberOfPassenger)
                .putExtra(INTENT_UUID, uuid)
                .putExtra(INTENT_REGISTRATION_NUMBER, registrationNumber)
                .putExtra(Constants.INTENT_DRIVER_INFORMATION_JOURNEY, driverInformation)
                .putExtra(Constants.INTENT_TRIP_INFORMATION_JOURNEY, tripInformation)
                .putExtra(Constants.INTENT_VEHICLE_INFORMATION_JOURNEY, vehicleInformation));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("uuid-value", uuid);
    }

    private class GridSpacing extends RecyclerView.ItemDecoration{

        private int spanCount, spacing;
        private boolean includeEdge;

        GridSpacing(int spanCount, int spacing, boolean includeEdge){
            this.spanCount = spanCount;
            this.includeEdge = includeEdge;
            this.spacing = spacing;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge){
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}