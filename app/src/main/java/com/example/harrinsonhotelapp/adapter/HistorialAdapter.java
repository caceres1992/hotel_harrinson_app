package com.example.harrinsonhotelapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harrinsonhotelapp.R;
import com.example.harrinsonhotelapp.model.Reserva;

import java.util.ArrayList;
import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialViewHolder> {

    List<Reserva> listReserva = new ArrayList<>();
    Context context;


    public HistorialAdapter(Context context) {
        this.context = context;
    }

    public void setLis(List<Reserva> reservas) {
        this.listReserva = reservas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historial_reserva, parent, false);
        return new HistorialViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull HistorialViewHolder holder, int position) {
        Reserva reserva = listReserva.get(position);
        String fecha = context.getString(R.string.fecha_de_reserva, reserva.getFechaInicio(), reserva.getFechaFinal());
        String precio_total = context.getString(R.string.precio_total, reserva.getPrecioTotal().toString());
        String status = context.getString(R.string.stado_reserva, reserva.getEstado());
        String tipo_habitacion = context.getString(R.string.tipohabitacion, reserva.getHabitacion().getTipoHabitacion().getNombre());
        String code_habitacion = context.getString(R.string.code_bar, reserva.getCodigoReserva());


        holder.fecha_reserva.setText(fecha);
        holder.status.setText(status);
        holder.precio_total.setText(precio_total);
        holder.tipo_habitacion.setText(tipo_habitacion);
        holder.code_habitacion.setText(code_habitacion);
                
        if (holder.status.getText().equals("FINALIZADO")) {
            holder.status.setBackgroundColor(context.getColor(R.color.color_stado_finalizado));
        } else if (holder.status.getText().equals("ACTIVO")) {
            holder.status.setBackgroundColor(context.getColor(R.color.color_stado_activa));
        } else {
            holder.status.setBackgroundColor(context.getColor(R.color.color_stado_pendiente));
        }

    }

    @Override
    public int getItemCount() {
        return listReserva.size();
    }
}
