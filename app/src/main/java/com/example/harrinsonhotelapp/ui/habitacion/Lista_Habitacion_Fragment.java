package com.example.harrinsonhotelapp.ui.habitacion;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.harrinsonhotelapp.R;
import com.example.harrinsonhotelapp.adapter.HabitacionesAdapter;
import com.example.harrinsonhotelapp.model.Habitacion;
import com.example.harrinsonhotelapp.request.RequestFilterHabitacion;
import com.example.harrinsonhotelapp.ui.dashboard.DashboardViewModel;
import com.example.harrinsonhotelapp.utils.HabitacionDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static androidx.navigation.Navigation.findNavController;


public class Lista_Habitacion_Fragment extends Fragment implements HabitacionDetails {
    ImageButton ibtnBack;
    RecyclerView recyclerView;
    HabitacionesAdapter adapter;
    FloatingActionButton btn_filter;
    HabitacionViewModel habitacionViewModel;
    SharedPreferences preferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        preferences = getContext().getSharedPreferences("datos", Context.MODE_PRIVATE);
        habitacionViewModel =
                new ViewModelProvider(this).get(HabitacionViewModel.class);

        View v =inflater.inflate(R.layout.fragment_lista__habitacion_, container, false);
        ibtnBack = v.findViewById(R.id.btn_back);
        recyclerView=v.findViewById(R.id.rv_listado_habitacion);
        btn_filter = v.findViewById(R.id.btnf_filter);

        btn_filter.setOnClickListener(v1 -> habitacionViewModel.ShowDialog(getContext()));
        RequestFilterHabitacion filterHabitacion = new RequestFilterHabitacion();


        filterHabitacion.setStart("2021-01-20");
        filterHabitacion.setFinish("2021-01-20");
        habitacionViewModel.filterHabitacionPorFecha(filterHabitacion);
        adapter = new HabitacionesAdapter(getContext(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        settingAnimation();
        
        ibtnBack.setOnClickListener(view ->{
            findNavController(v).navigate(R.id.action_lista_Habitacion_Fragment_to_navigation_dashboard);
        } );


        habitacionViewModel.listMutableLiveData.observe(getViewLifecycleOwner(), new Observer<List<Habitacion>>() {
            @Override
            public void onChanged(List<Habitacion> habitacions) {
                settingAnimation();
            adapter.setList(habitacions);
            recyclerView.setAdapter(adapter);
            }
        });
        return v;
    }

    void settingAnimation(){
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layou_slide_right);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.scheduleLayoutAnimation();
    }




    @Override
    public void onEventDetailsHabitacion(int id, double precio, String descripcion, String tipoHabitacion, String nrcamas, String img) {

                Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        bundle.putDouble("precio",precio);
        bundle.putString("descripcion",descripcion);
        bundle.putString("habitacion",tipoHabitacion);
        bundle.putString("camas",nrcamas);
        bundle.putString("img",img);
        getParentFragmentManager().setFragmentResult("key",bundle);



    }


}