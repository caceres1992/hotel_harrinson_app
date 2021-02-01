package com.example.harrinsonhotelapp.ui.notifications;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.harrinsonhotelapp.api.HotelHarrinsonService;
import com.example.harrinsonhotelapp.model.Habitacion;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsViewModel extends AndroidViewModel {

    MutableLiveData<List<Habitacion>> mutableLiveDataPromocion = new MutableLiveData<>();



    public NotificationsViewModel(@NonNull Application application) {
        super(application);
    }


    public void getPromocion(){
        HotelHarrinsonService.getInstance().getHarrinsonCliente().findAllPromocion()
                .enqueue(new Callback<List<Habitacion>>() {
                    @Override
                    public void onResponse(Call<List<Habitacion>> call, Response<List<Habitacion>> response) {
                        if (response.isSuccessful()){
                            mutableLiveDataPromocion.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Habitacion>> call, Throwable t) {

                    }
                });
    }


}