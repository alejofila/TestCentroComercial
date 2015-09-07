package com.tesis.alejofila.centrocomercial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import http.Callback;
import http.CallbackLogin;
import http.Constants;
import http.ResultCallback;
import http.ServiceMannager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnIniciar, btnGoToRegister;
    EditText edtEmail, edtPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = (EditText) findViewById(R.id.edtLogin);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnIniciar =(Button) findViewById(R.id.btnLogin);
        btnGoToRegister =(Button) findViewById(R.id.btnLinkToRegisterScreen);
        btnGoToRegister.setOnClickListener(this);
        btnIniciar.setOnClickListener(this);

        // In case that the activity is instantiated by RegisterActivity
        if(getIntent() != null){
            String email = getIntent().getStringExtra(Constants.EMAIL);
            String password = getIntent().getStringExtra(Constants.PASSWORD);
            edtEmail.setText(email);
            edtPassword.setText(password);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                if(validateLoginForm())
                    loginFunction();
                else
                    Toast.makeText(this, R.string.login_missing_values,Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnLinkToRegisterScreen:
                goToRegisterScreen();
                break;
            default:
                break;

        }

    }

    private boolean validateLoginForm() {
        if (edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty())
            return false;
        else
            return true;
    }

    private void goToRegisterScreen() {
        Intent i = new Intent(this , RegisterActivity.class);
        startActivity(i);
        finish();


    }

    private void loginFunction() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        ServiceMannager.loginFunction(email, password, new Callback() {
            @Override
            public void receiveResult(ResultCallback result) {
                if(result.isValido()){
                    Intent i = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}
