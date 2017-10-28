package i.am.eipeks.rims._activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import i.am.eipeks.rims.R;
import i.am.eipeks.rims._authentication.Login;
import i.am.eipeks.rims._utils.SessionUtils;
import i.am.eipeks.rims._database.CentralDBHelper;
import i.am.eipeks.rims._database.VehicleDatabaseHelper;

public class WelcomePage extends AppCompatActivity {

    SessionUtils firstTimeSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rims);

        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.loading_layout);

        firstTimeSession = new SessionUtils(this);

        if (SessionUtils.hasAppRunOnce()){
            startActivity(new Intent(this, Login.class));
        }

        CentralDBHelper centralDBHelper = new CentralDBHelper(this);
        centralDBHelper.getWritableDatabase().close();

        VehicleDatabaseHelper vehicleDatabaseHelper = new VehicleDatabaseHelper(this);
        vehicleDatabaseHelper.getWritableDatabase().close();

        Button getStarted = (Button) findViewById(R.id.get_started);

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(WelcomePage.this, Login.class));
                                            SessionUtils.setAppHasRunBefore(true);
                                            finish();
                                        }
                                    },
                        2000);
                relativeLayout.setVisibility(View.VISIBLE);
            }
        });

    }
}
