package com.example.harrinsonhotelapp.ui.habitacion.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.harrinsonhotelapp.api.HotelHarrinsonService;
import com.example.harrinsonhotelapp.model.Reserva;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservaViewModel extends AndroidViewModel {



    public ReservaViewModel(@NonNull Application application) {
        super(application);
    }


    public void sendReserva(Reserva reserva){
        HotelHarrinsonService.getInstance().getHarrinsonCliente().doReserva(reserva).enqueue(new Callback<Reserva>() {
            @Override
            public void onResponse(Call<Reserva> call, Response<Reserva> response) {
                    if (response.isSuccessful()){
                        System.out.println("Exito");
                    }else {
                        System.err.println("ya no se envio la reserva");
                    }

            }

            @Override
            public void onFailure(Call<Reserva> call, Throwable t) {
                    System.err.println("error");
            }
        });

    }
}
