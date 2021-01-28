package com.example.harrinsonhotelapp.ui.dashboard;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.harrinsonhotelapp.R;

import static androidx.navigation.Navigation.findNavController;

public class DashboardFragment extends Fragment {
Button button;
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);


        button = view.findViewById(R.id.btn_buscar);

        button.setOnClickListener(v -> {


            findNavController(v).navigate(R.id.nav_listar_habitacion);
        });

        return view;
    }
}