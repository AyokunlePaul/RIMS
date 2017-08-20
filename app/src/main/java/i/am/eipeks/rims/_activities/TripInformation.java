package i.am.eipeks.rims._activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import i.am.eipeks.rims.R;

public class TripInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_information);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
