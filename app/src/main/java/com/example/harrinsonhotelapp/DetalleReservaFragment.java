package com.example.harrinsonhotelapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetalleReservaFragment extends Fragment {



 TextView detalle;
SharedPreferences preferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            preferences = getContext().getSharedPreferences("datos", Context.MODE_PRIVATE);
        View v = inflater.inflate(R.layout.fragment_detalle_reserva, container, false);

        double tprecio = Double.parseDouble(preferences.getString("t_precio",null));
        double preciototal = tprecio/2;
        String strdetalle= getString(R.string.detalle_description,"% (S/"+preciototal+") ");
           detalle = v.findViewById(R.id.detalle_reserva_descrption);
           detalle.setText(strdetalle);



        return v;
    }
}