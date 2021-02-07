package com.example.harrinsonhotelapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.harrinsonhotelapp.R;
import com.example.harrinsonhotelapp.model.Habitacion;
import com.example.harrinsonhotelapp.ui.login.LoginActivity;
import com.example.harrinsonhotelapp.utils.HabitacionPromocion;

import java.util.List;

import static androidx.navigation.Navigation.findNavController;

public class PromocionAdapter extends RecyclerView.Adapter<PromocionHolder> {

    List<Habitacion> habitacionList;
    Context context;
    HabitacionPromocion promocion;
    SharedPreferences preferences;

    public PromocionAdapter(Context context, HabitacionPromocion promocion) {
        this.context = context;
        this.promocion = promocion;
        notifyDataSetChanged();
    }

    public void setList(List<Habitacion> habitacionList) {
        this.habitacionList = habitacionList;

    }

    @NonNull
    @Override
    public PromocionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_descuento, parent, false);
        return new PromocionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromocionHolder holder, int position) {
        Habitacion habitacion = habitacionList.get(position);
        Double total_pagar_promocion = habitacion.getTipoHabitacion().getPrecio() - (habitacion.getTipoHabitacion().getPrecio() * habitacion.getDescuento());

        String parseTotal = context.getString(R.string.precio_habitacion_promocion, habitacion.getTipoHabitacion().getPrecio().toString());


        holder.tv_desc_promopcion.setText(context.getString(R.string.ahora, "S/" + total_pagar_promocion));
        holder.tv_descripcion_promocion.setText(habitacion.getDescripcion());
        holder.tv_nro_camas_promocion.setText(habitacion.getTipoHabitacion().getNroCamas().toString());
        holder.tv_precio_promocion.setText(parseTotal);
        holder.tv_precio_promocion.setPaintFlags(holder.tv_precio_promocion.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_tipo_habitacion_promocion.setText(context.getString(R.string.tipohabitacion, habitacion.getTipoHabitacion().getNombre()));


        Glide.with(context)
                .load(habitacion.getImg())
                .into(holder.image_habitacion_promocion);
        preferences = context.getSharedPreferences("datos", Context.MODE_PRIVATE);


        String f_inicio = preferences.getString("f_inicio", null);
        String f_final = preferences.getString("f_final", null);
        int id_huesped = preferences.getInt("id_huesped", 0);
        holder.btnInfo_promocion.setOnClickListener(v -> {

            if (id_huesped != 0) {
                int id, ncamas;
                double precio, des;
                String strDescripcion, strTipoHabitacion, strImg;

                id = habitacion.getId();
                precio = habitacion.getTipoHabitacion().getPrecio();
                des = habitacion.getDescuento();
                strTipoHabitacion = habitacion.getTipoHabitacion().getNombre();
                strImg = habitacion.getImg();
                strDescripcion = habitacion.getDescripcion();
                ncamas = habitacion.getTipoHabitacion().getNroCamas();

                promocion.onEventDetailPromocion(id,
                        precio, des, strTipoHabitacion, ncamas, strDescripcion, strImg
                );
                if (f_inicio == null) {
                    Toast.makeText(context.getApplicationContext(), "Necesita elegir fechas", Toast.LENGTH_LONG).show();
                } else {
                    findNavController(v).navigate(R.id.action_navigation_notifications_to_detailPromocionFragment);
                }
            } else {
                Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
                context.startActivity(intent);
            }


        });
    }

    @Override
    public int getItemCount() {
        return habitacionList.size();
    }
}
