package com.example.harrinsonhotelapp.ui.habitacion.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.harrinsonhotelapp.api.HotelHarrinsonService;
import com.example.harrinsonhotelapp.model.Reserva;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservaViewModel extends AndroidViewModel {
    SharedPreferences preferences;


    public ReservaViewModel(@NonNull Application application) {
        super(application);
    }


    public void sendReserva(Reserva reserva) {

        HotelHarrinsonService.getInstance().getHarrinsonCliente().doReserva(reserva).enqueue(new Callback<Reserva>() {
            @Override
            public void onResponse(Call<Reserva> call, Response<Reserva> response) {
                if (response.isSuccessful()) {

                    preferences = getApplication().getSharedPreferences("datos", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit().putString("codigo_reserva", response.body().getCodigoReserva());
                    editor.commit();


                } else {
                    System.err.println("ya no se envio la reserva " + response.code());
                }

            }

            @Override
            public void onFailure(Call<Reserva> call, Throwable t) {
                System.err.println("error");
            }
        });

    }
}
