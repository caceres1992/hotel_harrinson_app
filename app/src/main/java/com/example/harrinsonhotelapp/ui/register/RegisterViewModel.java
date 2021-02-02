package com.example.harrinsonhotelapp.ui.register;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.harrinsonhotelapp.api.HotelHarrinsonService;
import com.example.harrinsonhotelapp.model.Huesped;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel  extends AndroidViewModel {



    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }


    public void addHuesped(Huesped huesped){

        HotelHarrinsonService.getInstance().getHarrinsonCliente().doRegister(huesped).enqueue(new Callback<Huesped>() {
            @Override
            public void onResponse(Call<Huesped> call, Response<Huesped> response) {


                if (response.isSuccessful()){
                    Toast.makeText(getApplication(),"Creado con exito",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplication(),"Error",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Huesped> call, Throwable t) {
                Toast.makeText(getApplication(),"Error en tiempo de respuesta",Toast.LENGTH_LONG).show();
            }
        });
    }
}
