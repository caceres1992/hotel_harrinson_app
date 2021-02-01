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
import com.example.harrinsonhotelapp.utils.HabitacionPromocion;

import java.util.List;

public class NotificationsFragment extends Fragment implements HabitacionPromocion {
    RecyclerView recyclerView;
    PromocionAdapter adapter;

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        notificationsViewModel.getPromocion();
        recyclerView = root.findViewById(R.id.rv_listado_promociones);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PromocionAdapter(getContext(),this);
        notificationsViewModel.mutableLiveDataPromocion.observe(getViewLifecycleOwner(), habitacions -> {
            settingAnimation();
                     adapter.setList(habitacions);
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