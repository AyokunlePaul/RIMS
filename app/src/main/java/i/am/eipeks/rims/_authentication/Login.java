package i.am.eipeks.rims._authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.Calendar;

import i.am.eipeks.rims.R;
import i.am.eipeks.rims._activities.Main;
import i.am.eipeks.rims._classes._auth_class._auth_pojo.AuthUser;
import i.am.eipeks.rims._classes._auth_class._json_response.JSONResponseUser;
import i.am.eipeks.rims._network.Auth;
import i.am.eipeks.rims._utils.APIUtils;
import i.am.eipeks.rims._utils.NetworkUtils;
import i.am.eipeks.rims._utils.SessionUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText identificationNumber, password;
    private Button loginButton, forgotPassword;

    private String token;

    private TextInputLayout identificationTextInputLayout, passwordTextInputLayout;

    private Auth authenticateUser;
    private AuthUser user;

    private RelativeLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SessionUtils.isLoggedIn()){
            startActivity(new Intent(this, Main.class));
        }

        authenticateUser = APIUtils.getAuth();

        loadingLayout = (RelativeLayout) findViewById(R.id.loading_layout);

        identificationTextInputLayout = (TextInputLayout) findViewById(R.id.identification_number_input_layout);
        passwordTextInputLayout = (TextInputLayout) findViewById(R.id.password_input_layout);

        identificationNumber = (EditText) findViewById(R.id.identification_number);
        password = (EditText) findViewById(R.id.password);

        loginButton = (Button) findViewById(R.id.login_button);
        forgotPassword = (Button) findViewById(R.id.forgot_password_button);

        identificationTextInputLayout.setErrorEnabled(false);
        passwordTextInputLayout.setErrorEnabled(false);

        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {
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
                    loginButton.setEnabled(false);
                    loginButton.setClickable(false);
                    //noinspection deprecation
                    loginButton.setBackground(getResources().getDrawable(R.drawable.button_background_two));
                    forgotPassword.setEnabled(false);
                    forgotPassword.setClickable(false);
                    identificationTextInputLayout.setErrorEnabled(false);
                    passwordTextInputLayout.setErrorEnabled(false);

                    authenticateUser(view, identificationNumber.getText().toString(), password.getText().toString());
                }
                break;
            case R.id.forgot_password_button:
                break;
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void authenticateUser(final View view, final String username, final String password) {
        loadingLayout.setVisibility(View.VISIBLE);
        authenticateUser.authenticateUser(username, password).enqueue(new Callback<JSONResponseUser>() {
            @Override
            public void onResponse(@NonNull Call<JSONResponseUser> call,@NonNull Response<JSONResponseUser> response) {
                loadingLayout.setVisibility(View.GONE);
                if (response.body() == null){
                    Snackbar.make(view, "Invalid username or password", Snackbar.LENGTH_LONG).show();
                    loginButton.setEnabled(true);
                    loginButton.setClickable(true);
                    //noinspection deprecation
                    loginButton.setBackground(getResources().getDrawable(R.drawable.button_background));
                    forgotPassword.setEnabled(true);
                    forgotPassword.setClickable(true);
                } else {
                    token = response.body().getToken();
                    user = response.body().getUser();
                    SessionUtils.setAppToken(token);
                    SessionUtils.setUserLoggedIn(user.getName());
                    SessionUtils.setUserId(user.getUserId());
                    SessionUtils.setLoggedIn(true);
                    SessionUtils.setDateLoggedIn(Calendar.DATE);
                    SessionUtils.setHourLoggedIn(Calendar.HOUR_OF_DAY);
                    SessionUtils.setMonthLoggedIn(Calendar.MONTH);
                    SessionUtils.setYearLoggedIn(Calendar.YEAR);
                    SessionUtils.setMinuteLoggedIn(Calendar.MINUTE);
                    startActivity(new Intent(Login.this, Main.class));
                }
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onFailure(@NonNull Call<JSONResponseUser> call, @NonNull Throwable t) {
                loadingLayout.setVisibility(View.GONE);
                loginButton.setEnabled(true);
                loginButton.setClickable(true);
                //noinspection deprecation
                loginButton.setBackground(getResources().getDrawable(R.drawable.button_background));
                forgotPassword.setEnabled(true);
                forgotPassword.setClickable(true);
                if (!NetworkUtils.isPhoneConnected(getApplicationContext())){
                    Snackbar.make(view, "Network not available on the device.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    authenticateUser(view, username, password);
                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                } else {
                    Snackbar.make(view, "Unable to connect.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    authenticateUser(view, username, password);
                                }
                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                }
            }
        });
    }
}