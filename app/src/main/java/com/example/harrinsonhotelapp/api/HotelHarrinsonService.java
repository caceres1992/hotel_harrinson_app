package com.example.harrinsonhotelapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HotelHarrinsonService {

    private static HotelHarrinsonService hotelHarrinsonService =null;
    private Retrofit retrofit;
    private IHarrinsonCliente harrinsonCliente;



    public HotelHarrinsonService(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_HOTELHARRISON_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//LLAMAMOS A LA INTERFACE
    harrinsonCliente = retrofit.create(IHarrinsonCliente.class);
    }

    public static HotelHarrinsonService getInstance(){
        if(hotelHarrinsonService==null){
            hotelHarrinsonService = new HotelHarrinsonService();
        }
        return hotelHarrinsonService;
    }

    public IHarrinsonCliente getHarrinsonCliente(){
        return harrinsonCliente;
    }
}
