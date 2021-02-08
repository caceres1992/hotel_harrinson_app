package com.example.harrinsonhotelapp.ui.habitacion;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harrinsonhotelapp.HallActivity;
import com.example.harrinsonhotelapp.MainActivity;
import com.example.harrinsonhotelapp.R;
import com.example.harrinsonhotelapp.adapter.HabitacionesAdapter;
import com.example.harrinsonhotelapp.model.Habitacion;
import com.example.harrinsonhotelapp.request.RequestFilterHabitacion;
import com.example.harrinsonhotelapp.utils.HabitacionDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static androidx.navigation.Navigation.findNavController;


public class Lista_Habitacion_Fragment extends Fragment implements HabitacionDetails {
    TextView tv_filter;
    ImageButton ibtnBack;
    RecyclerView recyclerView;
    HabitacionesAdapter adapter;
    FloatingActionButton btn_filter;
    HabitacionViewModel habitacionViewModel;
    SharedPreferences preferences;
    Dialog dialogFilter;
    String f_inicio, f_final;
    ListView listView;
    ArrayList<String> tipo = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        preferences = getContext().getSharedPreferences("datos", Context.MODE_PRIVATE);

        habitacionViewModel =
                new ViewModelProvider(this).get(HabitacionViewModel.class);

        View v = inflater.inflate(R.layout.fragment_lista__habitacion_, container, false);

        ibtnBack = v.findViewById(R.id.btn_back);
        tv_filter = v.findViewById(R.id.tv_filter_habitacion);
        recyclerView = v.findViewById(R.id.rv_listado_habitacion);
        btn_filter = v.findViewById(R.id.btnf_filter);

//        filtrar por tipo de habitacion
        addDatetoFilter();

        btn_filter.setOnClickListener(v1 -> habitacionViewModel.ShowDialog(getContext()));

        RequestFilterHabitacion filterHabitacion = new RequestFilterHabitacion();

        f_inicio = preferences.getString("f_inicio", null);
        f_final = preferences.getString("f_final", null);


        filterHabitacion.setStart(f_inicio);
        filterHabitacion.setFinish(f_final);


        habitacionViewModel.filterHabitacionPorFecha(filterHabitacion);

        adapter = new HabitacionesAdapter(getContext(), this);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        settingAnimation();

        ibtnBack.setOnClickListener(view -> {
            int id_huesped = preferences.getInt("id_huesped", 0);

            if (id_huesped != 0) {
                findNavController(v).navigate(R.id.action_lista_Habitacion_Fragment_to_navigation_dashboard);
            } else {
                Intent intent = new Intent(getContext(), HallActivity.class);
                startActivity(intent);
            }


        });


        habitacionViewModel.listMutableLiveData.observe(getViewLifecycleOwner(), habitacions -> {
            settingAnimation();
            List<Habitacion> nuevaListaHabitacion = habitacions.stream().filter(habitacion -> !habitacion.isPromocion()).collect(Collectors.toList());
            Toast.makeText(getContext(), "total habitaciones " + nuevaListaHabitacion.size(), Toast.LENGTH_LONG).show();
            adapter.setList(nuevaListaHabitacion);
            recyclerView.setAdapter(adapter);

        });
        return v;
    }


    void addDatetoFilter() {
        tipo.add("KING");
        tipo.add("QUEEN");
        tipo.add("MASTER SUITE");
        tipo.add("MINI SUITE");
        tipo.add("INDIVIDUAL");
        tipo.add("TODOS");


        tv_filter.setOnClickListener(v -> {
            //inicializamos el dialogo;
            dialogFilter = new Dialog(getContext());
            dialogFilter.setContentView(R.layout.dialog_filter_room);
            //ajustar el ancho
            dialogFilter.getWindow().setLayout(700, 900);
            //enviamos tranparencia
            dialogFilter.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //MOSTRAMOS EL DIALOGO
            dialogFilter.show();

            EditText editText = dialogFilter.findViewById(R.id.edt_tipo_room);
            listView = dialogFilter.findViewById(R.id.listv_tipo_room);

            ArrayAdapter<String> adapterString = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, tipo);
            listView.setAdapter(adapterString);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapterString.getFilter().filter(s);

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            listView.setOnItemClickListener((parent, view, position, id) -> {
//                    cuando el item es selecionado pasara al text view automaticamente
                tv_filter.setText(adapterString.getItem(position));
                habitacionViewModel.listMutableLiveData.observe(getViewLifecycleOwner(), habitacions -> {
                    settingAnimation();
                    List<Habitacion> nuevaListaFiltrada;

                    nuevaListaFiltrada = habitacions.stream().filter(habitacion -> habitacion.getTipoHabitacion().getNombre().contains(adapterString.getItem(position)))
                            .filter(habitacion -> !habitacion.isPromocion())
                            .collect(Collectors.toList());

                    if (nuevaListaFiltrada.size() != 0) {
                        adapter.setList(nuevaListaFiltrada);
                    } else {
                        adapter.setList(habitacions.stream().filter(habitacion -> !habitacion.isPromocion()).collect(Collectors.toList()));
                    }
                    recyclerView.setAdapter(adapter);


                });
                dialogFilter.dismiss();
            });
        });
    }

    void settingAnimation() {
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layou_slide_right);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void onEventDetailsHabitacion(int id, double precio, String descripcion, String tipoHabitacion, String nrcamas, String img) {

        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putDouble("precio", precio);
        bundle.putString("descripcion", descripcion);
        bundle.putString("habitacion", tipoHabitacion);
        bundle.putString("camas", nrcamas);
        bundle.putString("img", img);
        getParentFragmentManager().setFragmentResult("key", bundle);
    }

}