package i.am.eipeks.rims._authentication;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import i.am.eipeks.rims.R;
import i.am.eipeks.rims._activities.Main;
import i.am.eipeks.rims._classes.Authentication;
import i.am.eipeks.rims._database.VehicleDatabaseHelper;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Session session;

    private EditText identificationNumber, password;

    private String[] authenticationProjection = {VehicleDatabaseHelper.COLUMN_PASSWORD,
            VehicleDatabaseHelper.COLUMN_IDENTIFICATION_NUMBER};

    private TextInputLayout identificationTextInputLayout, passwordTextInputLayout;

    private ArrayList<Authentication> authenticationInfo;

    private RelativeLayout loadingLayout;

    public static final String ID = "ID";
    public static final String PASSWORD = "Password";
    public static final String USER = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new Session(this);

        if (session.isLoggedIn()){
            startActivity(new Intent(this, Main.class));
        }

        VehicleDatabaseHelper vehicleDatabaseHelper = new VehicleDatabaseHelper(this);

        SQLiteDatabase vehicleDB = vehicleDatabaseHelper.getWritableDatabase();

//        retrofit = new Retrofit.Builder().build();

        authenticationInfo = new ArrayList<>();

        loadingLayout = (RelativeLayout) findViewById(R.id.loading_layout);

        identificationTextInputLayout = (TextInputLayout) findViewById(R.id.identification_number_input_layout);
        passwordTextInputLayout = (TextInputLayout) findViewById(R.id.password_input_layout);

        identificationNumber = (EditText) findViewById(R.id.identification_number);
        password = (EditText) findViewById(R.id.password);

        Button login = (Button) findViewById(R.id.login_button);
        Button forgotPassword = (Button) findViewById(R.id.forgot_password_button);

//        Cursor cursor = vehicleDB.query(VehicleDatabaseHelper.VEHICLE_TABLE_NAME, authenticationProjection,
//                null, null, null, null, null);
//
//        if (cursor.moveToFirst()){
//            do {
//                try {
//                    Authentication authentication = new Authentication(
//                            cursor.getString(cursor.getColumnIndexOrThrow(VehicleDatabaseHelper.COLUMN_IDENTIFICATION_NUMBER)),
//                            cursor.getString(cursor.getColumnIndexOrThrow(VehicleDatabaseHelper.COLUMN_PASSWORD)));
//                    authenticationInfo.add(authentication);
//                } catch (SQLiteException e){e.printStackTrace();}
//            } while (cursor.moveToNext());
//        }

        identificationTextInputLayout.setErrorEnabled(false);
        passwordTextInputLayout.setErrorEnabled(false);

        login.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);

//        cursor.close();
    }

    @Override
    public void onClick(final View view) {

        ImageView loadingImage = (ImageView) loadingLayout.findViewById(R.id.rims_custom_loading);
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(loadingImage, "rotation", 0f, 90f, 180f, 270f, 360f);
        rotationAnimator.setDuration(2000);
        rotationAnimator.setRepeatCount(ValueAnimator.INFINITE);

        switch (view.getId()){
            case R.id.login_button:
                if (TextUtils.isEmpty(identificationNumber.getText()) || TextUtils.isEmpty(password.getText())){
                    if (TextUtils.isEmpty(identificationNumber.getText())){
                        passwordTextInputLayout.setErrorEnabled(false);
                        identificationTextInputLayout.setErrorEnabled(true);
                        identificationTextInputLayout.setError("Field is empty");
                    } else {
                        identificationTextInputLayout.setErrorEnabled(false);
                        passwordTextInputLayout.setErrorEnabled(true);
                        passwordTextInputLayout.setError("Field is empty");
                    }
                } else {
                    loadingLayout.setVisibility(View.VISIBLE);
                    identificationTextInputLayout.setErrorEnabled(false);
                    passwordTextInputLayout.setErrorEnabled(false);
                    new Handler().postDelayed(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                          if ("123456789".equals(identificationNumber.getText().toString())
                                                                  && "password".equals(password.getText().toString())){
                                                              startActivity(new Intent(Login.this, Main.class)
                                                              .putExtra(ID, identificationNumber.getText().toString())
                                                              .putExtra(PASSWORD, password.getText().toString()));
                                                              session.setLoggedIn(true);
                                                              session.setUserLoggedIn("Ayokunle Paul_123456789");
                                                              finish();
                                                          } else {
                                                              loadingLayout.setVisibility(View.GONE);
//                                                              Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                                                              Snackbar.make(view, "Invalid username or password", Snackbar.LENGTH_SHORT).show();
                                                          }
                                                  }
                                              },
                            4000);
                    rotationAnimator.start();
                }
                break;
            case R.id.forgot_password_button:
                break;
        }
    }
}
