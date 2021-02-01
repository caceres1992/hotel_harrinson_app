package com.example.harrinsonhotelapp.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harrinsonhotelapp.R;

public class PromocionHolder extends RecyclerView.ViewHolder {
    Button btnInfo_promocion;
    ImageView image_habitacion_promocion;
    TextView tv_precio_promocion,
            tv_tipo_habitacion_promocion,
            tv_descripcion_promocion,
            tv_nro_camas_promocion,
            tv_desc_promopcion
            ;



    public PromocionHolder(@NonNull View itemView) {
        super(itemView);
                tv_precio_promocion =itemView.findViewById(R.id.item_precio_habitacion_promocion);
                tv_tipo_habitacion_promocion=itemView.findViewById(R.id.item_tipo_habitacion_promocion);
                tv_descripcion_promocion=itemView.findViewById(R.id.item_descripcion_habitacion_promocion);
                tv_nro_camas_promocion=itemView.findViewById(R.id.item_nrcamas_habitacion_promocion);
                tv_desc_promopcion=itemView.findViewById(R.id.item_habitacion_descuento);

                btnInfo_promocion = itemView.findViewById(R.id.item_btn_ver_habitacion_promocion);
                image_habitacion_promocion = itemView.findViewById(R.id.item_img_habitacion_promocion);


    }
}
