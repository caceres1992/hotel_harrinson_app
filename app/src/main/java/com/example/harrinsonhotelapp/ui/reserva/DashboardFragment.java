package com.example.harrinsonhotelapp.ui.reserva;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.harrinsonhotelapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import static androidx.navigation.Navigation.findNavController;

public class DashboardFragment extends Fragment {
    Button button;
    FloatingActionButton btn_range_date;
    TextView fecha_inicio_dashboard, fecha_final_dashboard;
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        fecha_final_dashboard = view.findViewById(R.id.tv_fecha_final_dashbord);
        fecha_inicio_dashboard = view.findViewById(R.id.tv_fecha_inicio_dashbord);
        button = view.findViewById(R.id.btn_buscar);
        btn_range_date = view.findViewById(R.id.bt_range_date);


        btn_range_date.setOnClickListener(v -> {

            dashboardViewModel.ShowDialog(getContext());

            dashboardViewModel.mutableFechaInicio.observe(getViewLifecycleOwner(), start -> {
                fecha_inicio_dashboard.setText(start);
            });
            dashboardViewModel.mutableFechaFinal.observe(getViewLifecycleOwner(), end -> {
                fecha_final_dashboard.setText(end);

            });
        });


        button.setOnClickListener(v -> {
            if (fecha_inicio_dashboard.getText().toString().isEmpty() && fecha_final_dashboard.getText().toString().isEmpty()) {
                Snackbar.make(v, "Necesitas elegir fecha", Snackbar.LENGTH_SHORT).show();
            } else {
                findNavController(v).navigate(R.id.nav_listar_habitacion);
                Toast.makeText(getContext(), "haciendo click  para listado ", Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }


}