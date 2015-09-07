package com.tesis.alejofila.centrocomercial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import gcm.GCMConstants;
import gcm.QuickstartPreferences;
import gcm.RegistrationIntentService;
import http.Callback;
import http.Constants;
import http.ResultCallback;
import http.ServiceMannager;

/**
 * Created by tales on 4/09/15.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Constants
     */
    private static final String TAG = RegisterActivity.class.getSimpleName();

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    EditText edtName, edtEmail, edtPassword, edtPassword2;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtName = (EditText) findViewById(R.id.edt_register_name);
        edtEmail = (EditText) findViewById(R.id.edt_register_email);
        edtPassword = (EditText) findViewById(R.id.edt_register_password);
        edtPassword2 = (EditText) findViewById(R.id.edt_register_password_2);
        btnRegister = (Button) btnRegister.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                String token = sharedPreferences.getString(QuickstartPreferences.REGISTRATION_TOKEN,QuickstartPreferences.DEFAULT_TOKEN);
                if (sentToken) {
                    registerFunction(token);
                } else {
                   // mInformationTextView.setText(getString(R.string.token_error_message));
                }
            }
        };


    }

    @Override

    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        GCMConstants.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                if (validateRegistrationForm()) {
                    if (checkPlayServices()) {
                        Intent i = new Intent(this, RegistrationIntentService.class);
                        startService(i);
                    }
                }
                break;
        }


    }

    private void registerFunction(String token) {
        ServiceMannager.registerFunction(edtName.getText().toString(),
                edtEmail.getText().toString(),
                edtPassword.getText().toString(),
                token,
                registerCallback
        );
    }

    private boolean validateRegistrationForm() {
        if (edtName.getText().toString().isEmpty() || edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty() || edtPassword2.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.login_missing_values, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!edtPassword.getText().toString().equals(edtPassword2.getText().toString())) {
                Toast.makeText(this, "Los passwords no concuerdan", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    private Callback registerCallback = new Callback() {
        @Override
        public void receiveResult(ResultCallback result) {

            if(result.isValido()) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                i.putExtra(Constants.EMAIL,edtEmail.getText().toString());
                i.putExtra(Constants.PASSWORD,edtPassword.getText().toString());
                startActivity(i);
            }
            else{
                Toast.makeText(RegisterActivity.this,"Hubo un problema al intentar registrarte, intentalo luego",Toast.LENGTH_SHORT).show();
            }
        }
    };
}
