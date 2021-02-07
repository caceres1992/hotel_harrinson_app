package com.example.harrinsonhotelapp.ui.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harrinsonhotelapp.R;
import com.example.harrinsonhotelapp.model.Huesped;
import com.example.harrinsonhotelapp.ui.login.LoginActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    Huesped huesped;
    RegisterViewModel viewModel;
    TextInputLayout huesped_nombre, huesped_apellido, huesped_email, huesped_passowrd, repet_password, huesped_dni;
    String nombre, apellido, email, passowrd, repetPassword, dni;
    MaterialButton btn_register, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        //ocultar actionbar
        getSupportActionBar().hide();


        initValues();

        btn_register.setOnClickListener(v -> {
            if (saveHuesped()) {
                huesped = new Huesped(nombre, apellido, passowrd, email, dni);
                viewModel.addHuesped(huesped);
                Toast.makeText(this, "Enviado", Toast.LENGTH_LONG).show();
                limpiarDatos();
            }
            ;

        });

        btn_back.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), LoginActivity.class);
            startActivityForResult(intent, 0);
        });
    }

    private void initValues() {
        huesped_apellido = findViewById(R.id.ipt_register_apellido);
        huesped_email = findViewById(R.id.ipt_register_email);
        huesped_nombre = findViewById(R.id.ipt_register_name);
        huesped_dni = findViewById(R.id.ipt_register_dni);
        huesped_passowrd = findViewById(R.id.ipt_register_password);
        repet_password = findViewById(R.id.ipt_register_repetir_password);
        btn_register = findViewById(R.id.btn_registrar);
        btn_back = findViewById(R.id.btn_registrar_volver);

    }

    boolean saveHuesped() {

        nombre = huesped_nombre.getEditText().getText().toString();
        apellido = huesped_apellido.getEditText().getText().toString();
        email = huesped_email.getEditText().getText().toString();
        passowrd = huesped_passowrd.getEditText().getText().toString();
        repetPassword = repet_password.getEditText().getText().toString();
        dni = huesped_dni.getEditText().getText().toString();

        if (nombre.isEmpty() || apellido.isEmpty() || passowrd.isEmpty() || email.isEmpty() || dni.isEmpty()) {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_LONG).show();
            return false;
        } else if (!passowrd.equals(repetPassword)) {
            Toast.makeText(this, "verifica el password  ", Toast.LENGTH_LONG).show();
            return false;
        } else
            return true;
    }


    void limpiarDatos() {
        huesped_nombre.getEditText().setText("");
        huesped_apellido.getEditText().setText("");
        huesped_email.getEditText().setText("");
        huesped_passowrd.getEditText().setText("");
        repet_password.getEditText().setText("");
    }
}