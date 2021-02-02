package com.example.harrinsonhotelapp.ui.promocion;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.harrinsonhotelapp.R;


public class ResumenPromocionFragment extends Fragment {
    TextView resumen;
    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            preferences = getContext().getSharedPreferences("datos", Context.MODE_PRIVATE);
        View view = inflater.inflate(R.layout.fragment_resumen_promocion, container, false);
        resumen = view.findViewById(R.id.resumen_promocion);

        resumen.setText(getString(R.string.detalle_description,"(S/ "+preferences.getString("pagar_mitad",null)+") "));
        return view;
    }
}