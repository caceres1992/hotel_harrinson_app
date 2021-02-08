package com.example.harrinsonhotelapp.ui.habitacion;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.harrinsonhotelapp.R;

import static androidx.navigation.Navigation.findNavController;


public class Informacion_habitacion extends Fragment {
    ImageButton button_Back;
    SharedPreferences preferences;
    Button btn_Reservar;
    ImageView ivHabitacion;
    TextView tv_precio, tv_descripcion, tv_nr_camas, tv_tipo_habitacion;
    String tipo_habitacion, description, camas, img;
    String precio;
    int id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("key", this, (requestKey, result) -> {
            enviarDatosFinales(result);

            description = result.getString("descripcion");
            precio = getString(R.string.precio_habitacion, String.valueOf(result.getDouble("precio")));
            tipo_habitacion = getString(R.string.tipohabitacion, result.getString("habitacion"));
            camas = result.getString("camas");
            img = result.getString("img");
            id = result.getInt("id");


            Glide.with(getContext())
                    .load(img)
                    .into(ivHabitacion);

            tv_precio.setText(precio);
            tv_tipo_habitacion.setText(tipo_habitacion);
            tv_descripcion.setText(description);
            tv_nr_camas.setText(camas);
        });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_informacion_habitacion, container, false);
        preferences = getContext().getSharedPreferences("datos", Context.MODE_PRIVATE);
        button_Back = v.findViewById(R.id.btn_back_info);
        btn_Reservar = v.findViewById(R.id.btn_info_reserva_promocion);
        tv_precio = v.findViewById(R.id.tv_detalle_precio_promocion);
        tv_tipo_habitacion = v.findViewById(R.id.detalle_tipo_habitacion_promocion);
        tv_descripcion = v.findViewById(R.id.detalle_description_promocion);
        tv_nr_camas = v.findViewById(R.id.detalle_nro_camas_promocion);
        ivHabitacion = v.findViewById(R.id.detalle_img_promocion);

        setOnclickListener();

        return v;

    }


    private void setOnclickListener() {
        button_Back.setOnClickListener(v1 -> findNavController(v1).navigate(R.id.action_nav_informacion_habitacion_to_lista_Habitacion_Fragment));
        btn_Reservar.setOnClickListener(v -> {

            findNavController(v).navigate(R.id.action_nav_informacion_habitacion_to_formularioReservaFragment);
        });

    }

    void enviarDatosFinales(Bundle result) {
        SharedPreferences.Editor editor = preferences.edit()
                .putString("descripcion", result.getString("descripcion"))
                .putString("camas", result.getString("camas"))
                .putString("precio", String.valueOf(result.getDouble("precio")))
                .putInt("id_habitacion", result.getInt("id"))
                .putString("habitacion", result.getString("habitacion"));

        ;
        editor.commit();
    }
}