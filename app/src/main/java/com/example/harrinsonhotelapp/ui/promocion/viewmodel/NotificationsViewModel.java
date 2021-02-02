package com.example.harrinsonhotelapp.ui.promocion.viewmodel;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.example.harrinsonhotelapp.R;
import com.example.harrinsonhotelapp.api.HotelHarrinsonService;
import com.example.harrinsonhotelapp.model.Habitacion;
import com.example.harrinsonhotelapp.model.Reserva;
import com.example.harrinsonhotelapp.request.RequestFilterHabitacion;
import com.example.harrinsonhotelapp.utils.CalendarListener;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsViewModel extends AndroidViewModel {
    SharedPreferences preferences;

    AlertDialog dialog;
    DateRangeCalendarView calendar;
   public MutableLiveData<List<Habitacion>> mutableLiveDataPromocion = new MutableLiveData<>();
    Button btn_close;
   public MutableLiveData<String> fecha_inicio = new MutableLiveData<>();

    public NotificationsViewModel(@NonNull Application application) {
        super(application);
    }



    public void filterHabitacionPorFecha(RequestFilterHabitacion filter){

        Call<List<Habitacion>> responseHabitacion = HotelHarrinsonService.getInstance().getHarrinsonCliente().doFilterHabitacion(filter);
        responseHabitacion.enqueue(new Callback<List<Habitacion>>() {
            @Override
            public void onResponse(Call<List<Habitacion>> call, Response<List<Habitacion>> response) {
                if (response.isSuccessful()){
                    mutableLiveDataPromocion.setValue(response.body());
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







    public void showDialog(Context context){

        preferences = context.getSharedPreferences("datos",Context.MODE_PRIVATE);

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.item_filter_range_picker,null);

        calendar = view.findViewById(R.id.calendar);

        calendar.setCalendarListener(calendarListener);

        dialog   = new AlertDialog.Builder(context)
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
            int diafinal=  endDate.get(Calendar.DAY_OF_YEAR);

            int diferencia= diafinal - diaInicio;

            String fecha_inicioCast  = DateFormat.format("dd-MM-yyyy",startDate.getTime()).toString();
            String fecha_finalCast  = DateFormat.format("dd-MM-yyyy",endDate.getTime()).toString();

            String inicioFilter  = DateFormat.format("yyyy-MM-dd",startDate.getTime()).toString();
            String finalFilter  = DateFormat.format("yyyy-MM-dd",endDate.getTime()).toString();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("f_inicio",inicioFilter)
                    .putString("f_final",finalFilter)
                    .putInt("total_dias",diferencia)
            ;

            editor.commit();
            RequestFilterHabitacion filterHabitacion = new RequestFilterHabitacion();
            filterHabitacion.setStart(inicioFilter);
            filterHabitacion.setFinish(finalFilter);
            filterHabitacionPorFecha(filterHabitacion);

            String concatFecha = fecha_inicioCast +" / "+ fecha_finalCast;
                    fecha_inicio.setValue(concatFecha);
        }
    };







    public void reservarPromocion(Reserva reserva){
        Call<Reserva> reservaCall = HotelHarrinsonService.getInstance().getHarrinsonCliente().doReserva(reserva);
        reservaCall.enqueue(new Callback<Reserva>() {
            @Override
            public void onResponse(Call<Reserva> call, Response<Reserva> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getApplication(),"Promocion Reserva",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplication(),"Error con code : " + response.code(),Toast.LENGTH_LONG).show();
                    }
            }

            @Override
            public void onFailure(Call<Reserva> call, Throwable t) {
                Toast.makeText(getApplication(),"Error de tiempo de esperara " + t.getMessage() ,Toast.LENGTH_LONG).show();
            }
        });

    }

}