package com.example.harrinsonhotelapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.harrinsonhotelapp.R;
import com.example.harrinsonhotelapp.model.Habitacion;
import com.example.harrinsonhotelapp.ui.habitacion.Informacion_habitacion;
import com.example.harrinsonhotelapp.utils.HabitacionDetails;

import java.util.ArrayList;
import java.util.List;

import static androidx.navigation.Navigation.findNavController;

public class HabitacionesAdapter extends RecyclerView.Adapter<HabitacionesViewHolder> {

   Context context;
    List<Habitacion> list = new ArrayList<>();
    private HabitacionDetails habitacionDetails;
    public HabitacionesAdapter(Context context,HabitacionDetails habitacionDetails){
        this.context = context;
        this.habitacionDetails = habitacionDetails;
    }

    public void setList(List<Habitacion> list) {
        this.list = list;
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public HabitacionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_habitaciones, parent, false);
        return new HabitacionesViewHolder(v);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    @SuppressLint("StringFormatMatches")
    public void onBindViewHolder(@NonNull HabitacionesViewHolder holder, int position) {

        Habitacion habitacion =  list.get(position);
       String precio = context.getString(R.string.precio_habitacion,habitacion.getTipoHabitacion().getPrecio());
       String tipo_habitacion = context.getString(R.string.tipohabitacion,habitacion.getTipoHabitacion().getNombre());
       String description = habitacion.getDescripcion();
       String camas = String.valueOf(habitacion.getTipoHabitacion().getNroCamas());

        Glide.with(context)
                .load(habitacion.getImg())
                .into(holder.image_habitacion);

        holder.tv_precio.setText(precio);
        holder.tv_nro_camas.setText(camas);
        holder.tv_descripcion.setText(description);
        holder.tv_tipo_habitacion.setText(tipo_habitacion);



        holder.btnInfo.setOnClickListener(v -> {

            habitacionDetails.onEventDetailsHabitacion(habitacion.getId(),habitacion.getTipoHabitacion().getPrecio()
                    ,habitacion.getDescripcion()
                    ,habitacion.getTipoHabitacion().getNombre()
                    ,String.valueOf(habitacion.getTipoHabitacion().getNroCamas())
                    ,habitacion.getImg());

            findNavController(v).navigate(R.id.nav_informacion_habitacion);
        });
    }




}
