package com.example.harrinsonhotelapp.ui.promocion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.harrinsonhotelapp.R;

import static androidx.navigation.Navigation.findNavController;


public class DetailPromocionFragment extends Fragment {
SharedPreferences preferences;
    Button btn_ir_formulario;
    TextView tvcamas,tvprecio,tvdes,tvdescription,tvhabitacion;
    ImageView imgHabitacion;
    String strcamas,strprecio,strdesc,strdescription,strhabitacion,img;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        preferences = getContext().getSharedPreferences("datos", Context.MODE_PRIVATE);
        View view = inflater.inflate(R.layout.fragment_detail_promocion, container, false);


        tvcamas = view.findViewById(R.id.detalle_nro_camas_promocion);
        tvprecio = view.findViewById(R.id.tv_detalle_precio_promocion);
        tvdes = view.findViewById(R.id.tv_precio_desc_promocion);
        tvdescription = view.findViewById(R.id.detalle_description_promocion);
        tvhabitacion = view.findViewById(R.id.detalle_tipo_habitacion_promocion);
        imgHabitacion = view.findViewById(R.id.detalle_img_promocion);
        btn_ir_formulario = view.findViewById(R.id.btn_info_reserva_promocion);

        strcamas = String.valueOf(preferences.getInt("camas",0));
        strprecio = preferences.getString("precio_habitacion",null);
        strdesc = preferences.getString("descuento",null);
        strdescription = preferences.getString("description",null);
        strhabitacion = preferences.getString("habitacion",null);
        img = preferences.getString("img",null);


        btn_ir_formulario.setOnClickListener(v -> {
            Toast.makeText(getContext(),"navegano",Toast.LENGTH_LONG).show();
            findNavController(v).navigate(R.id.action_detailPromocionFragment_to_formularioReservaPromocionFragment);
        });

        Toast.makeText(getContext(), strdescription,Toast.LENGTH_LONG).show();
        sendValues();
        return view;
    }




    @SuppressLint("StringFormatMatches")
    private void sendValues() {
            double precio = Double.parseDouble(strprecio);
            double dsc = Double.parseDouble(strdesc);
            double total = precio - (precio*dsc);
        tvhabitacion.setText(getString(R.string.tipohabitacion,strhabitacion));
        tvcamas.setText(strcamas);
        tvprecio.setText(getString(R.string.precio_habitacion_promocion, strprecio));
        tvprecio.setPaintFlags(tvprecio.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        tvdescription.setText(strdescription);
        tvdes.setText(getString(R.string.ahora,"S/ "+total));
        Glide.with(getContext())
                .load(img)
                .into(imgHabitacion);

    }


}