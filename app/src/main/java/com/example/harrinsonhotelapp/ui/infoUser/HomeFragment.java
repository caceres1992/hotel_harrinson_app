package com.example.harrinsonhotelapp.ui.infoUser;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harrinsonhotelapp.R;
import com.example.harrinsonhotelapp.adapter.HistorialAdapter;
import com.example.harrinsonhotelapp.model.Reserva;

import java.util.List;

import static androidx.navigation.Navigation.findNavController;

public class HomeFragment extends Fragment {
    SharedPreferences preferences;
    Button btn_cerrar_session;
    HistorialAdapter adapter;
    RecyclerView recyclerView;
    TextView tv_huesped_name, tv_huesped_lastname, tv_huesped_email, tv_huesped_dni;
    String huesped_name, huesped_lastname, huesped_email, huesped_dni;
    int huesped_id;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        preferences = getContext().getSharedPreferences("datos", Context.MODE_PRIVATE);

        initValues(root);
        setValues();
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        huesped_id = preferences.getInt("id_huesped", 0);
        Toast.makeText(getContext(), "id " + huesped_id, Toast.LENGTH_LONG).show();
        homeViewModel.findAllHistorial(huesped_id);
        adapter = new HistorialAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        settingAnimation();
        homeViewModel.mReserva.observe(getViewLifecycleOwner(), new Observer<List<Reserva>>() {
            @Override
            public void onChanged(List<Reserva> reservas) {
                adapter.setLis(reservas);
            }
        });

        recyclerView.setAdapter(adapter);
        btn_cerrar_session.setOnClickListener(v -> {
            SharedPreferences.Editor edit = preferences.edit();
            edit.clear().commit();
            findNavController(v).navigate(R.id.action_navigation_home_to_loginActivity);

        });

        return root;
    }


    void initValues(View v) {
        recyclerView = v.findViewById(R.id.rv_historial);
        tv_huesped_email = v.findViewById(R.id.home_huesped_email);
        tv_huesped_lastname = v.findViewById(R.id.home_huesped_apellido);
        tv_huesped_name = v.findViewById(R.id.home_huesped_name);
        btn_cerrar_session = v.findViewById(R.id.home_btn_logout);
        tv_huesped_dni = v.findViewById(R.id.home_huesped_dni);
    }

    void setValues() {

        huesped_email = getString(R.string.email, preferences.getString("email_huesped", null));
        huesped_lastname = getString(R.string.apellido, preferences.getString("apellido_huesped", null));
        huesped_name = getString(R.string.nombre, preferences.getString("nombre_huesped", null));
        huesped_dni = getString(R.string.dni, preferences.getString("dni_huesped", null));

        tv_huesped_name.setText(huesped_name);
        tv_huesped_email.setText(huesped_email);
        tv_huesped_lastname.setText(huesped_lastname);
        tv_huesped_dni.setText(huesped_dni);
    }

    void settingAnimation() {
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layou_slide_right);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.scheduleLayoutAnimation();
    }
}