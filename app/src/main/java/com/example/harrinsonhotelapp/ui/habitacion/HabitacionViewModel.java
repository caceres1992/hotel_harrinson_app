package com.example.harrinsonhotelapp.ui.habitacion;

import android.app.AlertDialog;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.harrinsonhotelapp.R;
import com.example.harrinsonhotelapp.api.HotelHarrinsonService;
import com.example.harrinsonhotelapp.model.Habitacion;
import com.example.harrinsonhotelapp.request.RequestFilterHabitacion;
import com.google.android.material.card.MaterialCardView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabitacionViewModel  extends AndroidViewModel {

    AlertDialog alertDialog;

    TextView tv_dia_inicio, tv_dia_final;
    TextView tv_mes_inicio, tv_mes_final;
    TextView tv_year_inicio, tv_year_final;


    MutableLiveData<List<Habitacion>>listMutableLiveData = new MutableLiveData<>();
    Button filter_search,filter_close;
    MaterialCardView c_inicio,c_final;
    String MES[] = {"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"};

    Calendar calendar = Calendar.getInstance();
    DatePickerDialog pickerDialog;
    //    para fechas
    private int dia_inicio, mes_inicio, año_inicio;
    private int dia_final, mes_final, año_final;
    String strfechaInicio, strfechaFinal;
    int calculardia1 = 0, calculardia2 = 0, total_dias;
    SharedPreferences preferences ;
    public HabitacionViewModel(@NonNull Application application) {
        super(application);
    }


    public void filterHabitacionPorFecha(RequestFilterHabitacion filter){

        Call<List<Habitacion>> responseHabitacion = HotelHarrinsonService.getInstance().getHarrinsonCliente().doFilterHabitacion(filter);
        responseHabitacion.enqueue(new Callback<List<Habitacion>>() {
            @Override
            public void onResponse(Call<List<Habitacion>> call, Response<List<Habitacion>> response) {
                if (response.isSuccessful()){
                    listMutableLiveData.setValue(response.body());
                    Toast.makeText(getApplication(),"total "+ response.body().size(),Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplication(),"codigo "+response.code(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Habitacion>> call, Throwable t) {
                Toast.makeText(getApplication(),"tiempo de respuesta  "+ t.getCause().getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }


    public void ShowDialog(Context context){

        preferences = context.getSharedPreferences("datos",Context.MODE_PRIVATE);

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.dialog_filter,null);
        filter_close = view.findViewById(R.id.item_filter_cerrar);
        filter_search = view.findViewById(R.id.item_filter_buscar);
        c_inicio = view.findViewById(R.id.card_calendar_inicio);
        c_final = view.findViewById(R.id.card_calendar_final);

        tv_dia_inicio=view.findViewById(R.id.home_dia_inicio);
        tv_mes_inicio=view.findViewById(R.id.home_mes_inicio);
        tv_year_inicio=view.findViewById(R.id.home_year_inicio);

        tv_dia_final = view.findViewById(R.id.home_dia_final);
        tv_mes_final = view.findViewById(R.id.home_mes_final);
        tv_year_final = view.findViewById(R.id.home_year_final);

        c_inicio.setOnClickListener(v -> fechaInicio(context));
        c_final.setOnClickListener(v -> fechaFinal(context));



        filter_search.setOnClickListener(v -> {
            RequestFilterHabitacion filterHabitacion = new RequestFilterHabitacion();
            total_dias = calculardia2 - calculardia1;

            SharedPreferences.Editor editor = preferences.edit()
                    .putString("f_inicio",strfechaInicio)
                    .putString("f_final",strfechaFinal)
                    .putInt("total_dias",total_dias);
            editor.commit();

            filterHabitacion.setFinish(strfechaFinal);
            filterHabitacion.setStart(strfechaInicio);
            filterHabitacionPorFecha(filterHabitacion);
            alertDialog.dismiss();
        });

        filter_close.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
         alertDialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        alertDialog.show();
    }

    void  fechaInicio(Context context){

        c_inicio.setOnClickListener(v -> {
            dia_inicio = calendar.get(Calendar.DAY_OF_YEAR);
            mes_inicio = calendar.get(Calendar.MONTH);
            año_inicio = calendar.get(Calendar.YEAR);
            pickerDialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
                if ((month + 1) < 10) {
                    strfechaInicio = (year + "-" + ("0" + (month + 1)) + "-" + dayOfMonth);
                } else {
                    strfechaInicio = (year + "-" + (month + 1) + "-" + dayOfMonth);
                }
                calculardia1 = dayOfMonth;
                enviarValoresFechaInicial(dayOfMonth, month, year);
            },año_inicio,mes_inicio,dia_inicio);
            pickerDialog.getDatePicker().setMinDate(new Date().getTime());
            pickerDialog.show();
        });
    }

    void  fechaFinal(Context context){

        c_final.setOnClickListener(v -> {
            dia_final = calendar.get(Calendar.DAY_OF_YEAR);
            mes_final = calendar.get(Calendar.MONTH);
            año_final = calendar.get(Calendar.YEAR);
            pickerDialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
                if ((month + 1) < 10) {
                    strfechaFinal = (year + "-" + ("0" + (month + 1)) + "-" + dayOfMonth);
                } else {
                    strfechaFinal = (year + "-" + (month + 1) + "-" + dayOfMonth);
                }

                calculardia2 = dayOfMonth;
                enviarValoresFechaFinal(dayOfMonth, month, year);
            },año_final,mes_final,dia_final);
            pickerDialog.getDatePicker().setMinDate(new Date().getTime());
            pickerDialog.show();
        });
    }


    private void enviarValoresFechaFinal(int dias, int mes, int year) {

        tv_mes_final.setText(MES[mes]);
        if (dias < 10) {
            tv_dia_final.setText("0" + String.valueOf(dias));
        } else {
            tv_dia_final.setText(String.valueOf(dias));
        }
        tv_year_final.setText(String.valueOf(year));
    }


    private void enviarValoresFechaInicial(int dias, int mes, int year) {


        tv_mes_inicio.setText(MES[mes]);
        if (dias < 10) {
            tv_dia_inicio.setText("0" + String.valueOf(dias));
        } else {
            tv_dia_inicio.setText(String.valueOf(dias));
        }
        tv_year_inicio.setText(String.valueOf(year));


    }


}
