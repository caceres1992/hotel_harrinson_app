package com.example.harrinsonhotelapp.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harrinsonhotelapp.R;

public class HabitacionesViewHolder extends RecyclerView.ViewHolder {

    Button btnInfo;
    ImageView image_habitacion;
    TextView tv_precio,tv_tipo_habitacion,tv_descripcion,tv_nro_camas;
    public HabitacionesViewHolder(@NonNull View itemView) {
        super(itemView);
        btnInfo = itemView.findViewById(R.id.item_btn_ver_habitacion);
        image_habitacion = itemView.findViewById(R.id.item_img_habitacion);
        tv_precio = itemView.findViewById(R.id.item_precio_habitacion);
        tv_tipo_habitacion = itemView.findViewById(R.id.item_tipo_habitacion);
        tv_descripcion = itemView.findViewById(R.id.item_descripcion_habitacion);
        tv_nro_camas = itemView.findViewById(R.id.item_nrcamas_habitacion);
    }
}
