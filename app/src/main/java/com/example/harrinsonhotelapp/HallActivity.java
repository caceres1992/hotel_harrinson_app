package com.example.harrinsonhotelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harrinsonhotelapp.ui.login.LoginActivity;

public class HallActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_hall_iniciar_session, btn_hall_listado;
    TextView tv_ir_mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);
        getSupportActionBar().hide();
        initValue();

        tv_ir_mapa.setOnClickListener(this);
        btn_hall_listado.setOnClickListener(this);
        btn_hall_iniciar_session.setOnClickListener(this);

    }

    private void initValue() {
        tv_ir_mapa = findViewById(R.id.tv_ir_mapa);
        btn_hall_iniciar_session = findViewById(R.id.btn_hall_iniciar);
        btn_hall_listado = findViewById(R.id.btn_hall_habitaciones);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == tv_ir_mapa.getId()) {
            Intent intent = new Intent(getApplicationContext(), MapaActivity.class);
            startActivity(intent);
        }

        if (v.getId() == btn_hall_iniciar_session.getId()) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        if (v.getId() == btn_hall_listado.getId()) {
            Toast.makeText(getApplicationContext(),"que opaso",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), ListadoHabitacionActivity.class);
            startActivity(intent);
        }
    }
}