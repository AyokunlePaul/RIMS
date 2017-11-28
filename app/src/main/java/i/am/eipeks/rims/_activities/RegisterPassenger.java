package i.am.eipeks.rims._activities;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import i.am.eipeks.rims.Constants;
import i.am.eipeks.rims.R;
import i.am.eipeks.rims._adapters.SeatNumberAdapter;
import i.am.eipeks.rims._classes._auth_class._json_response.JSONResponsePassenger;
import i.am.eipeks.rims._classes._model_class.Passenger;
import i.am.eipeks.rims._network.Auth;
import i.am.eipeks.rims._utils.APIUtils;
import i.am.eipeks.rims._utils.NetworkUtils;
import i.am.eipeks.rims._utils.SessionUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPassenger extends AppCompatActivity{
//    private String vehicleIntent, tripIntent, driverIntent;

    private int counter =  1, radio, capacity, total = 0;
    private boolean backPressedOnce = false;

    private EditText passengerName, passengerAddress, passengerPhone, nextOfKin, kinPhone;
    private TextInputLayout passengerNameTextInput, passengerAddressTextInput, passengerPhoneTextInput, nextOfKinTextInput, kinPhoneTextInput;
    private TextView currentSeat;
    private MenuItem nextItem;

    private RecyclerView seatNumbers;

    private ArrayList<Integer> seatNumberArray = new ArrayList<>();

    private Passenger passenger;

    private Auth auth;

    private RelativeLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_passenger);

        auth = APIUtils.getAuth();

        TextView capacityTextView = (TextView) findViewById(R.id.capacity);
        currentSeat = (TextView) findViewById(R.id.seat_count);

        if (savedInstanceState != null){
            seatNumberArray = savedInstanceState.getIntegerArrayList(Constants.SEAT_NUMBER_ARRAY);
            currentSeat.setText(savedInstanceState.getString(Constants.COUNTER));
            total = savedInstanceState.getInt(Constants.TOTAL);
            capacity = savedInstanceState.getInt(Constants.CAPACITY);
        } else {
//            vehicleIntent = getIntent().getStringExtra(Constants.INTENT_VEHICLE_INFORMATION_JOURNEY);
//            driverIntent = getIntent().getStringExtra(Constants.INTENT_DRIVER_INFORMATION_JOURNEY);
//            tripIntent = getIntent().getStringExtra(Constants.INTENT_TRIP_INFORMATION_JOURNEY);
            capacity = getIntent().getIntExtra(Constants.INTENT_CAPACITY_JOURNEY, 0);
        }

        currentSeat.setText(String.valueOf(counter));
        capacityTextView.setText(String.valueOf(capacity));

        loadingLayout = (RelativeLayout) findViewById(R.id.loading_layout);

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

        seatNumbers = (RecyclerView) findViewById(R.id.seat_numbers);

        SeatNumberAdapter seatNumberAdapter = new SeatNumberAdapter(this, capacity, seatNumberArray);

        seatNumbers.setAdapter(seatNumberAdapter);
        seatNumbers.setLayoutManager(new GridLayoutManager(this, 10));
        seatNumbers.addItemDecoration(new GridSpacing(10, dpToPx(2), true));

        RadioGroup sex = (RadioGroup) findViewById(R.id.sex);
        sex.getChildAt(0).setSelected(true);

        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                radio = radioGroup.getCheckedRadioButtonId();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vehicle_information_menu, menu);
        MenuItem doneItem = menu.findItem(R.id.done_menu);
        nextItem = menu.findItem(R.id.next_menu);
        if (total > 0 && !doneItem.isEnabled()){
            doneItem.setEnabled(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        passengerNameTextInput.setErrorEnabled(false);
        passengerAddressTextInput.setErrorEnabled(false);
        passengerPhoneTextInput.setErrorEnabled(false);
        nextOfKinTextInput.setErrorEnabled(false);
        kinPhoneTextInput.setErrorEnabled(false);

        String sex;

        switch (radio){
            case R.id.male:
                sex = "Male";
                break;
            case R.id.female:
                sex = "Female";
                break;
            default:
                sex = null;
        }
        switch (item.getItemId()){
            case R.id.done_menu:
                new AlertDialog.Builder(this)
                        .setMessage("Are all passengers registered?")
                        .setPositiveButton("Yes, They are", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(RegisterPassenger.this, Main.class));
                            }
                        }).setNegativeButton("No, Take me back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                }).create().show();
                break;

            case R.id.next_menu:
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
                    if (((SeatNumberAdapter)seatNumbers.getAdapter()).getSelectedSeat() == 0){
                        Toast.makeText(this, "No seat selected", Toast.LENGTH_SHORT).show();
                    } else if (sex == null){
                        Toast.makeText(this, "No gender selected", Toast.LENGTH_SHORT).show();
                    } else {
                        passenger = new Passenger(passengerName.getText().toString(), passengerPhone.getText().toString(), sex,
                                passengerAddress.getText().toString(), nextOfKin.getText().toString(),
                                String.valueOf(((SeatNumberAdapter)seatNumbers.getAdapter()).getSelectedSeat()),
                                kinPhone.getText().toString());

                        sendPassenger(loadingLayout);
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!backPressedOnce){
            backPressedOnce = true;
            new AlertDialog.Builder(this)
                    .setMessage("Cancel trip registration?")
                    .setTitle("Trip Registration")
                    .setPositiveButton("Cancel trip", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteTrip(loadingLayout);
                        }
                    }).setNegativeButton("Back", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            backPressedOnce = false;
                        }
                    })
                    .create().show();
        }
    }

    @SuppressWarnings("deprecation")
    private void sendPassenger(final View view){
        loadingLayout.setVisibility(View.VISIBLE);
        auth.sendPassenger(passenger.getPassengerName(), passenger.getPassengerPhone(),
                passenger.getPassengerSex(), passenger.getPassengerAddress(), passenger.getNextOfKin(),
                Integer.valueOf(passenger.getSeatNumber()), passenger.getNextOfKinPhone(),
                SessionUtils.getCurrentTripId(),"Bearer " + SessionUtils.getAppToken()).enqueue(new Callback<JSONResponsePassenger>() {
            @Override
            public void onResponse(@NonNull Call<JSONResponsePassenger> call, @NonNull Response<JSONResponsePassenger> response) {
                loadingLayout.setVisibility(View.GONE);
                //noinspection ConstantConditions
                switch (response.code()){
                    case 200:
                        passengerName.setText("");
                        passengerPhone.setText("");
                        passengerAddress.setText("");
                        nextOfKin.setText("");
                        kinPhone.setText("");
                        counter += 1;
                        if (counter <= capacity){
                            currentSeat.setText(String.valueOf(counter));
                            total += 1;
                            if (total == capacity){
                                nextItem.setVisible(false);
                            }
                        }
                        seatNumberArray.add(((SeatNumberAdapter)seatNumbers.getAdapter()).getSelectedSeat());
                        seatNumbers.swapAdapter(new SeatNumberAdapter(RegisterPassenger.this, capacity, seatNumberArray), true);
                        makeToast("Passenger added.");
                        break;
                    case 400:
                        //noinspection ConstantConditions
                        makeToast(String.valueOf(response.body() == null));
                        Snackbar.make(view, response.message(), Snackbar.LENGTH_INDEFINITE)
                                .setAction("Resend", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sendPassenger(view);
                                    }
                                }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                        Toast.makeText(RegisterPassenger.this, response.raw().toString(), Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        //noinspection ConstantConditions
                        Snackbar.make(view, "Unknown error.", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Resend", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sendPassenger(view);
                                    }
                                }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                        break;
                }
            }

            @Override
            public void onFailure(@NonNull Call<JSONResponsePassenger> call, @NonNull Throwable t) {
                loadingLayout.setVisibility(View.GONE);
                if (NetworkUtils.isPhoneConnected(RegisterPassenger.this)){
                    Snackbar.make(view, "Phone not connected.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    sendPassenger(view);
                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                } else {
                    Snackbar.make(view, "Unknown error.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    sendPassenger(view);
                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                }
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void deleteTrip(final View view){
        loadingLayout.setVisibility(View.VISIBLE);
        auth.deleteTrip(SessionUtils.getCurrentTripId(),
                "Bearer " + SessionUtils.getAppToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                loadingLayout.setVisibility(View.GONE);
                switch (response.code()){
                    case 200:
                        startActivity(new Intent(RegisterPassenger.this, Main.class));
                        break;
                    default:
                        Snackbar.make(view, "An error occurred. ", Snackbar.LENGTH_INDEFINITE)
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
                loadingLayout.setVisibility(View.GONE);
                if (NetworkUtils.isPhoneConnected(RegisterPassenger.this)){
                    Snackbar.make(view, "Phone is not connected. ", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    deleteTrip(view);
                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                } else {
                    Snackbar.make(view, "Passenger information removal: Unknown error. ", Snackbar.LENGTH_INDEFINITE)
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList(Constants.SEAT_NUMBER_ARRAY, seatNumberArray);
        outState.putString(Constants.COUNTER, String.valueOf(counter));
        outState.putInt(Constants.TOTAL, total);
        outState.putInt(Constants.CAPACITY, capacity);
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

    @SuppressWarnings("SameParameterValue")
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @SuppressWarnings("SameParameterValue")
    private void makeToast(String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}