package com.example.harrinsonhotelapp.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harrinsonhotelapp.R;
import com.example.harrinsonhotelapp.adapter.PromocionAdapter;
import com.example.harrinsonhotelapp.model.Habitacion;
import com.example.harrinsonhotelapp.request.RequestFilterHabitacion;
import com.example.harrinsonhotelapp.utils.HabitacionPromocion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationsFragment extends Fragment implements HabitacionPromocion {
    RecyclerView recyclerView;
    PromocionAdapter adapter;
    TextView tv_fecha_promocion;
    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        tv_fecha_promocion = root.findViewById(R.id.fecha_promocion);
        recyclerView = root.findViewById(R.id.rv_listado_promociones);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PromocionAdapter(getContext(),this);

        tv_fecha_promocion.setOnClickListener(v -> notificationsViewModel.showDialog(getContext()));

        notificationsViewModel.fecha_inicio.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                    tv_fecha_promocion.setText(s);
            }
        });

        RequestFilterHabitacion requestFilterHabitacion =new RequestFilterHabitacion();
        requestFilterHabitacion.setStart("2021-02-01");
        requestFilterHabitacion.setFinish("2021-02-02");
        notificationsViewModel.filterHabitacionPorFecha(requestFilterHabitacion);
        notificationsViewModel.mutableLiveDataPromocion.observe(getViewLifecycleOwner(), habitacions -> {
            settingAnimation();

            List<Habitacion>nuevaListaPromocion;
            nuevaListaPromocion = habitacions.stream().filter(habitacion -> habitacion.isPromocion() !=false )
                    .collect(Collectors.toList());

            adapter.setList(nuevaListaPromocion);
            recyclerView.setAdapter(adapter);
        });




        return root;
    }

    void settingAnimation(){
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layou_slide_right);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void onEventDetailPromocion(int id, double precio, double desc, String tipoHabitacion, int nrcamas, String descripcion, String img) {

    }
}