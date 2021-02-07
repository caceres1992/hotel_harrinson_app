package com.example.harrinsonhotelapp.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harrinsonhotelapp.R;

public class HistorialViewHolder  extends RecyclerView.ViewHolder {
    TextView tipo_habitacion,precio_total,fecha_reserva,status,code_habitacion;

    public HistorialViewHolder(@NonNull View itemView) {
        super(itemView);
        tipo_habitacion = itemView.findViewById(R.id.item_historial_tipo_habitacion);
        fecha_reserva = itemView.findViewById(R.id.item_histororial_fecha);
        precio_total = itemView.findViewById(R.id.item_historial_precio_total);
        status = itemView.findViewById(R.id.item_historial_status);
        code_habitacion = itemView.findViewById(R.id.item_code_bar);
    }
}
