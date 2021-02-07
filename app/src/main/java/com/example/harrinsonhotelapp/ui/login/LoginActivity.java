package com.example.harrinsonhotelapp.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.harrinsonhotelapp.HallActivity;
import com.example.harrinsonhotelapp.MainActivity;
import com.example.harrinsonhotelapp.R;
import com.example.harrinsonhotelapp.model.Huesped;
import com.example.harrinsonhotelapp.response.HuespedResponse;
import com.example.harrinsonhotelapp.ui.register.RegisterActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    MaterialButton btn_login, btn_regresar;
    TextInputLayout mPassword, mUsername;
    TextView tv_registrar;
    Huesped huesped;
    TextView errorLogin;
    LoginViewModel loginViewModel;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        preferences = getSharedPreferences("datos", MODE_PRIVATE);
        init();
        onClick();
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }


    private void init() {

        tv_registrar = findViewById(R.id.tv_registrar);
        btn_login = findViewById(R.id.btn_login);
        btn_regresar = findViewById(R.id.btn_login_back);
        mPassword = findViewById(R.id.tl_login_password);
        mUsername = findViewById(R.id.tl_login_username);
        errorLogin = findViewById(R.id.tv_error_login);
    }


    private void onClick() {

        tv_registrar.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RegisterActivity.class);
            startActivityForResult(intent, 0);
        });

        btn_regresar.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HallActivity.class);
            startActivity(intent);
        });
        btn_login.setOnClickListener(v -> {

            if (validation()) {
                huesped = new Huesped(mPassword.getEditText().getText().toString(), mUsername.getEditText().getText().toString());
                loginViewModel.login(huesped, errorLogin);
                loginViewModel.huespedMutableLiveData.observe(this, huespedResponse -> {
                    SharedPreferences.Editor editor = preferences.edit()
                            .putInt("id_huesped", huespedResponse.getHuesped().getId())
                            .putString("nombre_huesped", huespedResponse.getHuesped().getNombre())
                            .putString("apellido_huesped", huespedResponse.getHuesped().getApellido())
                            .putString("email_huesped", huespedResponse.getHuesped().getCorreo())
                            .putString("dni_huesped", huespedResponse.getHuesped().getDocumento());
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                });
            }
        });
    }


    boolean validation() {
        String sPassword, sUsername;
        sUsername = mUsername.getEditText().getText().toString();
        sPassword = mPassword.getEditText().getText().toString();

        if (sPassword.isEmpty() && sUsername.isEmpty()) {
            mUsername.setError("Correo Requerido");
            mPassword.setError("Password requerido");
            errorLogin.setText(null);
            return false;

        } else if (sUsername.isEmpty()) {
            mUsername.setError("Correo Requerido");
            mPassword.setError(null);
            errorLogin.setText(null);
            return false;
        } else if (sPassword.isEmpty()) {
            mUsername.setError(null);
            mPassword.setError("password es requerido");
            return false;
        } else {
            return true;
        }


    }
}