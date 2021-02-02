package com.example.harrinsonhotelapp.api;

import com.example.harrinsonhotelapp.model.Habitacion;
import com.example.harrinsonhotelapp.model.Huesped;
import com.example.harrinsonhotelapp.model.Reserva;
import com.example.harrinsonhotelapp.request.RequestFilterHabitacion;
import com.example.harrinsonhotelapp.response.HuespedResponse;

import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IHarrinsonCliente {


    @POST("huespedes/login")
    Call<HuespedResponse> doLogin(@Body Huesped huesped);

    @POST("huespedes")
    Call<Huesped> doRegister(@Body Huesped Huesped);

    @POST("reservas/find-dateTime")
    Call<List<Habitacion>> doFilterHabitacion(@Body RequestFilterHabitacion filter);

    @POST("reservas")
    Call<Reserva> doReserva(@Body Reserva reserva);

    @GET("reservas/huesped/{id}")
    Call<List<Reserva>>findAllHistorial(@Path("id")int id);


}
