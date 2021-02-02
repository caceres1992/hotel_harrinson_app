package com.example.harrinsonhotelapp.ui.promocion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harrinsonhotelapp.R;
import com.example.harrinsonhotelapp.model.Habitacion;
import com.example.harrinsonhotelapp.model.Huesped;
import com.example.harrinsonhotelapp.model.Reserva;
import com.example.harrinsonhotelapp.ui.promocion.viewmodel.NotificationsViewModel;

import static androidx.navigation.Navigation.findNavController;


public class FormularioReservaPromocionFragment extends Fragment  implements View.OnClickListener {
    SharedPreferences preferences;
    Button btn_reservar;
    CheckBox cbxHabilitarPlaca;
    TextView placa;
    TextView tvcamas,tvprecio,tvdes,tvdescription,tvhabitacion,tvTotal,tv_totalDes,tvsubtotal,fecha_inicio,fecha_final,tvtotalDias;
    NotificationsViewModel viewModel;
    String strdescription,strhabitacion,f_inicio,f_final;
   double precio,desc;
    int id_habitacion,id_huesped,camas , nrdias;


    double subtotal,total_dsc,totalpagar;

    @SuppressLint("StringFormatMatches")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        preferences = getContext().getSharedPreferences("datos", Context.MODE_PRIVATE);
         viewModel =   new ViewModelProvider(this).get(NotificationsViewModel.class);
         
         
         
         View view=   inflater.inflate(R.layout.fragment_formulario_reserva_promocion, container, false);

        btn_reservar = view.findViewById(R.id.btn_reserva_finalizada_promocion);
        placa = view.findViewById(R.id.reserva_placa_vehiculo_promocion);
        cbxHabilitarPlaca = view.findViewById(R.id.cbx_reserva_estacionamiento_promocion);
        tvcamas = view.findViewById(R.id.reserva_cant_camas_promocion);
        tvprecio = view.findViewById(R.id.rserva_precio_habitacion_promocion);
        tvdes = view.findViewById(R.id.reserva_descuento_promocion);
        tvdescription =view.findViewById(R.id.reserva_descripcion_promocion);
        tvhabitacion = view.findViewById(R.id.reserva_tipo_habitacion);
        tvtotalDias = view.findViewById(R.id.reserva_total_dias_promocion);
        tvTotal = view.findViewById(R.id.reserva_precio_total_promocion);
        tv_totalDes = view.findViewById(R.id.reserva_descuento_promocion);
        tvsubtotal = view.findViewById(R.id.reserva_tv_subtotal_promocion);
        fecha_final = view.findViewById(R.id.reserva_fecha_final_promocion);
        fecha_inicio = view.findViewById(R.id.reserva_fecha_inicio_promocion);

        
        id_habitacion = preferences.getInt("id_habitacion",0);
        id_huesped = preferences.getInt("id_huesped",0);
        camas = preferences.getInt("camas",0);
        precio = Double.parseDouble(preferences.getString("precio_habitacion",null));
        desc = Double.parseDouble(preferences.getString("descuento",null));
        strhabitacion = preferences.getString("habitacion",null);
        strdescription = preferences.getString("description",null);
        nrdias = preferences.getInt("total_dias",0);
        f_inicio =preferences.getString("f_inicio",null);
        f_final = preferences.getString("f_final",null);
         subtotal = (precio * nrdias);
         total_dsc = subtotal * desc;
         totalpagar = subtotal - total_dsc;



        tvhabitacion.setText(getString(R.string.tipohabitacion,strhabitacion));
        tvTotal.setText("S/ "+totalpagar);
        tvdes.setText("S/ "+total_dsc);
        tvsubtotal.setText("S/ "+subtotal);
        tvprecio.setText(getString(R.string.precio_habitacion,precio));
        tvtotalDias.setText(String.valueOf(nrdias));
        fecha_inicio.setText(f_inicio);
        fecha_final.setText(f_final);
        tvcamas.setText(String.valueOf(camas));





        cbxHabilitarPlaca.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                placa.setEnabled(isChecked);
            }else {
                placa.setEnabled(isChecked);
            }
        });
        btn_reservar.setOnClickListener(this::onClick);
        return view;

    }










    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(),"haciendo click",Toast.LENGTH_LONG).show();
        Huesped huesped = new Huesped();
        Habitacion habitacion = new Habitacion();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("pagar_mitad", String.valueOf((totalpagar*0.5)));
        editor.commit();
        huesped.setId(id_huesped);
        habitacion.setId(id_habitacion);


        Reserva reserva = new Reserva(
                f_inicio+"T12:00",
                f_final+"T12:00"
                ,placa.getText().toString(),
                desc,
                totalpagar,
                huesped,habitacion
        );
        viewModel.reservarPromocion(reserva);
        findNavController(v).navigate(R.id.action_formularioReservaPromocionFragment_to_resumenPromocionFragment);

    }
}