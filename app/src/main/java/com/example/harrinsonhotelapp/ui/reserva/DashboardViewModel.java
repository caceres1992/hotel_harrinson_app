package com.example.harrinsonhotelapp.ui.reserva;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.example.harrinsonhotelapp.R;

import com.example.harrinsonhotelapp.utils.CalendarListener;

import java.util.Calendar;
import java.util.Locale;

import static androidx.navigation.Navigation.findNavController;

public class DashboardViewModel extends AndroidViewModel {
SharedPreferences preferences;

AlertDialog dialog;
    DateRangeCalendarView calendar;
    Button btn_filter_range;



   public MutableLiveData<String>mutableFechaInicio = new MutableLiveData<>();
   public  MutableLiveData<String>mutableFechaFinal = new MutableLiveData<>();
    public DashboardViewModel(@NonNull Application application) {
        super(application);
    }



    public void ShowDialog(Context context ){

        preferences = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.item_filter_range_picker,null);
        calendar = view.findViewById(R.id.calendar);
        btn_filter_range = view.findViewById(R.id.btn_filter_range);
      
        calendar.setCalendarListener(calendarListener);
        btn_filter_range.setOnClickListener(v -> {
                dialog.dismiss();
        });

//        calendar.setNavLeftImage(ContextCompat.getDrawable(this,R.drawable.ic_left));
//        calendar.setNavRightImage(ContextCompat.getDrawable(this,R.drawable.ic_right));
        Calendar caledario = Calendar.getInstance();
        int anio = caledario.get(Calendar.YEAR);
        int day = caledario.get(Calendar.DAY_OF_MONTH);

        int mes = caledario.get(Calendar.MONTH)-1;

        final Calendar startMonth = Calendar.getInstance();
        startMonth.set(anio, mes, day);
        final Calendar endMonth = (Calendar) startMonth.clone();
        endMonth.add(Calendar.MONTH, 5);

        final Calendar startDateSelectable = (Calendar) startMonth.clone();
        startDateSelectable.add(Calendar.DATE, 31);
        final Calendar endDateSelectable = (Calendar) endMonth.clone();
        endDateSelectable.add(Calendar.DATE, -31);

        calendar.setSelectableDateRange(startDateSelectable, endDateSelectable);

        final Calendar current = (Calendar) startMonth.clone();
        current.add(Calendar.MONTH, 1);
        calendar.setCurrentMonth(current);



      dialog   = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        dialog.show();
    }


    public  CalendarListener calendarListener = new CalendarListener() {
        @Override
        public void onFirstDateSelected(Calendar startDate) {
            Toast.makeText(getApplication(), "Start Date: " + startDate.getTime().toString(), Toast.LENGTH_SHORT).show();
            Log.d("TITULO", "Selected dates: Start: " + (calendar.getStartDate()) +
                    " End:" + (calendar.getEndDate()));
        }

        @Override
        public void onDateRangeSelected(Calendar startDate, Calendar endDate) {

            int diaInicio = startDate.get(Calendar.DAY_OF_YEAR);
          int diafinal=  endDate.get(Calendar.DAY_OF_YEAR);
          int diferencia= diafinal - diaInicio;
            String inicioParse = DateFormat.format("dd-MM-yyyy",startDate.getTime()).toString();
            String finalPase = DateFormat.format("dd-MM-yyyy",endDate.getTime()).toString();


            String iniciobd =DateFormat.format("yyyy-MM-dd",startDate.getTime()).toString();
            String finbd =DateFormat.format("yyyy-MM-dd",endDate.getTime()).toString();
          SharedPreferences.Editor editor = preferences.edit()
                  .putInt("total_dias",diferencia)
                  .putString("f_inicio",iniciobd)
                  .putString("f_final",finbd);
          editor.commit();



            mutableFechaFinal.setValue(finalPase);
            mutableFechaInicio.setValue(inicioParse);

        }
    };


}