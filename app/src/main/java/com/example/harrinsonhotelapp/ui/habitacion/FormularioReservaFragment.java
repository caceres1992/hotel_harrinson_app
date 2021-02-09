package com.example.harrinsonhotelapp.ui.habitacion;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harrinsonhotelapp.R;
import com.example.harrinsonhotelapp.model.Habitacion;
import com.example.harrinsonhotelapp.model.Huesped;
import com.example.harrinsonhotelapp.model.Reserva;
import com.example.harrinsonhotelapp.ui.habitacion.viewmodel.ReservaViewModel;
import com.google.android.material.textfield.TextInputEditText;

import static androidx.navigation.Navigation.findNavController;


public class FormularioReservaFragment extends Fragment {
    ReservaViewModel reservaViewModel;
    ImageButton btn_back;
    Button btn_reservar;
    SharedPreferences preferences;
    CheckBox cbxEstacionamiento;
    TextInputEditText placa;
    TextView precio, descripcion, fecha_incio, fecha_final,
            total_dias, precio_total, nr_camas, t_habitacion;
    double t_precio, precio_unitario;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        reservaViewModel = new ViewModelProvider(this).get(ReservaViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_formulario_reserva, container, false);
        preferences = getContext().getSharedPreferences("datos", Context.MODE_PRIVATE);
        btn_back = v.findViewById(R.id.btn_back_form);
        cbxEstacionamiento = v.findViewById(R.id.cbx_reserva_estacionamiento_promocion);
        precio = v.findViewById(R.id.rserva_precio_habitacion_promocion);
        descripcion = v.findViewById(R.id.reserva_descripcion_promocion);
        fecha_final = v.findViewById(R.id.reserva_fecha_final_promocion);
        fecha_incio = v.findViewById(R.id.reserva_fecha_inicio_promocion);
        precio_total = v.findViewById(R.id.reserva_precio_total_promocion);
        total_dias = v.findViewById(R.id.reserva_total_dias_promocion);
        nr_camas = v.findViewById(R.id.reserva_cant_camas_promocion);
        t_habitacion = v.findViewById(R.id.reserva_tipo_habitacion);
        placa = v.findViewById(R.id.reserva_placa_vehiculo_promocion);
        btn_reservar = v.findViewById(R.id.btn_reserva_finalizada_promocion);

        setValues();

        setOnclickListener();

        return v;
    }

    private void setOnclickListener() {

        btn_back.setOnClickListener(v -> findNavController(v).navigate(R.id.action_formularioReservaFragment_to_lista_Habitacion_Fragment));
        cbxEstacionamiento.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                placa.setEnabled(isChecked);
            } else {
                placa.setEnabled(isChecked);
            }
        });

        btn_reservar.setOnClickListener(v -> {

            int id_huesped = preferences.getInt("id_huesped", 0);
            int id_habitacion = preferences.getInt("id_habitacion", 0);
            Huesped huesped = new Huesped();
            huesped.setId(id_huesped);
            Habitacion habitacion = new Habitacion();
            habitacion.setId(id_habitacion);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(
                    "t_precio", String.valueOf(t_precio)
            );
            editor.commit();


            Reserva reserva = new Reserva(fecha_incio.getText() + "T12:00"
                    , fecha_final.getText() + "T12:00"
                    , placa.getText().toString(),
                    t_precio, huesped, habitacion);

            reservaViewModel.sendReserva(reserva);

            findNavController(v).navigate(R.id.action_formularioReservaFragment_to_detalleReservaFragment);
        });


    }


    private void setValues() {
        String strTipoHabitacion, strDescripcion;
        int t_dias;

        t_dias = preferences.getInt("total_dias", 0);
        precio_unitario = Double.parseDouble(preferences.getString("precio", null));
        t_precio = t_dias * precio_unitario;
        strTipoHabitacion = getString(R.string.tipohabitacion, preferences.getString("habitacion", null));
        strDescripcion = preferences.getString("descripcion", null);


        descripcion.setText(strDescripcion);
        fecha_incio.setText(preferences.getString("f_inicio", null));
        fecha_final.setText(preferences.getString("f_final", null));
        nr_camas.setText(preferences.getString("camas", null));
        precio_total.setText(" S/." + t_precio);
        precio.setText("S/." + precio_unitario);
        total_dias.setText(String.valueOf(t_dias));
        t_habitacion.setText(strTipoHabitacion);

    }
}