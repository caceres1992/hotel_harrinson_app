package com.example.harrinsonhotelapp.ui.infoUser;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.harrinsonhotelapp.api.HotelHarrinsonService;
import com.example.harrinsonhotelapp.model.Reserva;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    public MutableLiveData<List<Reserva>> mReserva = new MutableLiveData<>();



    public void findAllHistorial(int id){
        HotelHarrinsonService.getInstance().getHarrinsonCliente().findAllHistorial(id)
        .enqueue(new Callback<List<Reserva>>() {
            @Override
            public void onResponse(Call<List<Reserva>> call, Response<List<Reserva>> response) {
                                if (response.isSuccessful()){
                                    mReserva.setValue(response.body());
                                }else {
                                    System.err.println("Error al realizar la consulta");
                                }
            }

            @Override
            public void onFailure(Call<List<Reserva>> call, Throwable t) {
                System.err.println("Error en el tiempo : "+t.getMessage());
            }
        });

    }


}