package com.example.harrinsonhotelapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.WindowManager;

import com.daimajia.androidanimations.library.Techniques;
import com.example.harrinsonhotelapp.ui.login.LoginActivity;
import com.mapbox.android.gestures.Constants;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashActivity extends AwesomeSplash {

    SharedPreferences preferences ;

    @SuppressLint("ResourceType")
    @Override
    public void initSplash(ConfigSplash configSplash) {
        preferences = getSharedPreferences("datos",MODE_PRIVATE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        Personalizando efecto circular
        configSplash.setBackgroundColor(R.color.color_oscuro_hotel);
        configSplash.setAnimCircularRevealDuration(2000);
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM);


//        escojemos el Logo
        configSplash.setLogoSplash(R.drawable.ic_logo_no_bg); //or any other drawable
        configSplash.setAnimLogoSplashDuration(2000); //int ms



        //personalizar titulo
        configSplash.setTitleSplash("Hotel Harrinson");
        configSplash.setTitleTextColor(R.color.color_claro_hotel);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(3000);
        configSplash.setAnimTitleTechnique(Techniques.FadeIn);

    }

    @Override
    public void animationsFinished() {
        int usuario_id = preferences.getInt("id_huesped",0);
        if (usuario_id>0){
            Intent intent = new Intent (getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent (getApplicationContext(), HallActivity.class);
            startActivity(intent);
        }

    }



}