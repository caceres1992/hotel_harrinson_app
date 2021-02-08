package com.example.harrinsonhotelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

public class MapaActivity extends AppCompatActivity implements
        OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener {

    private MapView mapView;
    private MapboxMap mapboxMap;
    private Button btnnavigation;
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;

    // variables para calcular y trazar la ruta
    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;

    //    variable statica para los hoteles
    private static final LatLng locationOne = new LatLng(-12.163598, -77.024129);
    private static final LatLng locationTwo = new LatLng(-12.098111, -77.068242);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Mapbox.getInstance(this, getString(R.string.access_token));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        setContentView(R.layout.activity_mapa);
        mapView = findViewById(R.id.mapView);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {

    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onMapClick(@NonNull LatLng point) {

        Point puntoDestino = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        Point puntoOrigen = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());

        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
            source.setGeoJson(Feature.fromGeometry(puntoDestino));
        }
        getRoute(puntoOrigen, puntoDestino);
        btnnavigation.setEnabled(true);
        btnnavigation.setBackgroundColor(getColor(R.color.input_hotel));

        LatLngBounds latLngBounds = new LatLngBounds.Builder()
                .include(locationOne) // primer Hotel
                .include(locationTwo) // Segundo Hotel
                .build();
        mapboxMap.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 200), 5000);
        return true;
    }

    //    Obtendremos la ruta por los puntos de origen y destino
    private void getRoute(Point origen, Point destino) {

        NavigationRoute.builder(this).
                accessToken(Mapbox.getAccessToken())
                .origin(origen)
                .destination(destino)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call,
                                           Response<DirectionsResponse> response) {

                        Log.e(TAG, "Response code " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "Rutas no encontradas , estas seguro que usaste el token correcto");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "Ruta no encontrada");
                            return;
                        }
                        currentRoute = response.body().routes().get(0);
                        //dibujar la ruta en el mapa
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(
                                    null,
                                    mapView,
                                    mapboxMap,
                                    R.style.NavigationMapRoute);
                        }

                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {

                    }
                });

    }


    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(getString(R.string.navigation_guidance_day)
                , style -> {
                    enableLocationComponent(style);
                    addDestinationIconSymbolLayer(style);
                    mapboxMap.addOnMapClickListener(MapaActivity.this);
                    btnnavigation = findViewById(R.id.startButton);
                    btnnavigation.setOnClickListener(v -> {

//                        al dar click al boton navigation simulara la ruta trazada
                        boolean simulateRoute = true;
                        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                .directionsRoute(currentRoute)
                                .shouldSimulateRoute(simulateRoute).build();
                        NavigationLauncher.startNavigation(MapaActivity.this, options);
                    });

                });
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
// Verifica si los permisos están habilitados y si no, solicita
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
//Activar MapboxMap LocationComponent para mostrar la ubicación del usuario
// Agregar LocationComponentOptions también es un parámetro opcional
            LocationComponentOptions localtionCustumer = LocationComponentOptions.builder(this)
                    .elevation(4)
                    .accuracyAlpha(.6f)
                    .build();


            locationComponent = mapboxMap.getLocationComponent();
            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(this, loadedMapStyle)
                            .locationComponentOptions(localtionCustumer)
                            .build();

            locationComponent.activateLocationComponent(locationComponentActivationOptions);
            locationComponent.setLocationComponentEnabled(true);
            // Establecer el modo de cámara del componente
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);

            //ubicacion del dispositivo
            findViewById(R.id.back_to_camera_tracking_mode).setOnClickListener(view -> {
                locationComponent.setCameraMode(CameraMode.TRACKING);
                locationComponent.zoomWhileTracking(16f);
                Toast.makeText(MapaActivity.this, "Ubicacion Exacta",
                        Toast.LENGTH_SHORT).show();
            });

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    private void addDestinationIconSymbolLayer(Style style) {

        addMarkerIconsToMap(style);

        style.addImage("destination-icon-id",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
        style.addSource(geoJsonSource);
        SymbolLayer destinoSimbolo = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
        destinoSimbolo.withProperties(
                iconImage("destination-icon-id"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );

        style.addLayer(destinoSimbolo);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void addMarkerIconsToMap(Style loadedMapStyle) {
        loadedMapStyle.addImage("icon-id", BitmapUtils.getBitmapFromDrawable(
                getResources().getDrawable(R.drawable.ic_icohotel)));

        loadedMapStyle.addSource(new GeoJsonSource("source-id",
                FeatureCollection.fromFeatures(new Feature[]{
                        Feature.fromGeometry(Point.fromLngLat(locationOne.getLongitude(), locationOne.getLatitude())),
                        Feature.fromGeometry(Point.fromLngLat(locationTwo.getLongitude(), locationTwo.getLatitude())),
                })));

        loadedMapStyle.addLayer(new SymbolLayer("layer-id",
                "source-id").withProperties(
                iconImage("icon-id"),
                iconOffset(new Float[]{0f, -8f})
        ));
    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}