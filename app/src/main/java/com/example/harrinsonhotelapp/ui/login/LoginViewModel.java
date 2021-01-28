package com.example.harrinsonhotelapp.ui.login;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Build;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.harrinsonhotelapp.R;
import com.example.harrinsonhotelapp.api.HotelHarrinsonService;
import com.example.harrinsonhotelapp.model.Huesped;
import com.example.harrinsonhotelapp.response.HuespedResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel  extends AndroidViewModel {


    public  MutableLiveData<HuespedResponse> huespedMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String>textError = new MutableLiveData<>();
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }


    public void login(Huesped huesped , TextView tv){

        Call<HuespedResponse>huespedCall = HotelHarrinsonService.getInstance().getHarrinsonCliente().doLogin(huesped);
        huespedCall.enqueue(new Callback<HuespedResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<HuespedResponse> call, Response<HuespedResponse> response) {
                if (response.isSuccessful()){
                    textError.setValue(null);
                    huespedMutableLiveData.setValue(response.body());
                }else{
                    tv.setText("Credenciales no validas");
                    tv.setPadding(10,10,10,10);
                    tv.setBackgroundColor(R.color.color_stado_pendiente);

                }


            }
            @Override
            public void onFailure(Call<HuespedResponse> call, Throwable t) {
                System.out.println("Tiempo en respuesta " + t.getLocalizedMessage());
            }
        });
    }

}
