package com.example.harrinsonhotelapp.ui.habitacion;

import android.app.AlertDialog;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateFormat;
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

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.example.harrinsonhotelapp.R;
import com.example.harrinsonhotelapp.api.HotelHarrinsonService;
import com.example.harrinsonhotelapp.model.Habitacion;
import com.example.harrinsonhotelapp.request.RequestFilterHabitacion;
import com.example.harrinsonhotelapp.utils.CalendarListener;
import com.google.android.material.card.MaterialCardView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabitacionViewModel extends AndroidViewModel {

    SharedPreferences preferences;

    AlertDialog dialog;
    DateRangeCalendarView calendar;


    public MutableLiveData<String> mutableFechaInicio = new MutableLiveData<>();
    public MutableLiveData<String> mutableFechaFinal = new MutableLiveData<>();

    MutableLiveData<List<Habitacion>> listMutableLiveData = new MutableLiveData<>();


    public HabitacionViewModel(@NonNull Application application) {
        super(application);
    }


    public void filterHabitacionPorFecha(RequestFilterHabitacion filter) {

        Call<List<Habitacion>> responseHabitacion = HotelHarrinsonService.getInstance().getHarrinsonCliente().doFilterHabitacion(filter);
        responseHabitacion.enqueue(new Callback<List<Habitacion>>() {
            @Override
            public void onResponse(Call<List<Habitacion>> call, Response<List<Habitacion>> response) {

                if (response.isSuccessful()) {
                    listMutableLiveData.setValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "codigo " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Habitacion>> call, Throwable t) {
//                Toast.makeText(getApplication(),"tiempo de respuesta  "+ t.getCause().getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }


    public void ShowDialog(Context context) {

        preferences = context.getSharedPreferences("datos", Context.MODE_PRIVATE);
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.item_filter_range_picker, null);
        calendar = view.findViewById(R.id.calendar);

        calendar.setCalendarListener(calendarListener);


        //DESDE DONDE EMPEZARA
        final Calendar startMonth = Calendar.getInstance();
        final Calendar endMonth = (Calendar) startMonth.clone();
        endMonth.add(Calendar.MONTH, 5);

//        bloquear los dias antes  y un rango limitado
        final Calendar startDateSelectable = (Calendar) startMonth.clone();
        startDateSelectable.add(Calendar.DATE, 0);
        final Calendar endDateSelectable = (Calendar) endMonth.clone();
        endDateSelectable.add(Calendar.DATE, 0);

        calendar.setSelectableDateRange(startDateSelectable, endDateSelectable);

        dialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        dialog.show();
    }


    public CalendarListener calendarListener = new CalendarListener() {
        @Override
        public void onFirstDateSelected(Calendar startDate) {
        }

        @Override
        public void onDateRangeSelected(Calendar startDate, Calendar endDate) {

            int diaInicio = startDate.get(Calendar.DAY_OF_YEAR);
            int diafinal = endDate.get(Calendar.DAY_OF_YEAR);

            int diferencia = diafinal - diaInicio;

            String inicioParse = DateFormat.format("dd-MM-yyyy", startDate.getTime()).toString();
            String finalPase = DateFormat.format("dd-MM-yyyy", endDate.getTime()).toString();


            String iniciobd = DateFormat.format("yyyy-MM-dd", startDate.getTime()).toString();
            String finbd = DateFormat.format("yyyy-MM-dd", endDate.getTime()).toString();

            SharedPreferences.Editor editor = preferences.edit()
                    .putInt("total_dias", diferencia)
                    .putString("f_inicio", iniciobd)
                    .putString("f_final", finbd);
            editor.commit();

            RequestFilterHabitacion filterHabitacion = new RequestFilterHabitacion();

            filterHabitacion.setFinish(finbd);
            filterHabitacion.setStart(iniciobd);
            filterHabitacionPorFecha(filterHabitacion);
            mutableFechaFinal.setValue(finalPase);
            mutableFechaInicio.setValue(inicioParse);

        }
    };

}
