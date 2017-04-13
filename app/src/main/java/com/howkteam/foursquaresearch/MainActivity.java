package com.howkteam.foursquaresearch;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.howkteam.foursquaresearch.models.BaseModel;
import com.howkteam.foursquaresearch.models.Venue;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, LocationListener {

  public static final String TAG = MainActivity.class.getSimpleName();
  private FourSquareAPI mainApi;
  private RecyclerView rv;
  private Button btnSearch;
  private EditText etSearch;
  private Location currentLoc;
  private LocationManager locationManager;
  private List<Venue> currentVenues;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mainApi = API.get().create(FourSquareAPI.class);
    init();
  }

  private void init() {
    rv = (RecyclerView) findViewById(R.id.rv_list);
    rv.setLayoutManager(new LinearLayoutManager(this));
    etSearch = (EditText) findViewById(R.id.et_search);
    btnSearch = (Button) findViewById(R.id.btn_search);
    btnSearch.setOnClickListener(this);

    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    currentLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    if (currentLoc != null) {
      updatePlaces(currentLoc);
    }

    try {
      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
              10, this);
    } catch (Exception e) {
      Log.e(TAG, "Error", e);
    }

    etSearch.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        searchPlaces(s.toString());
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });
  }

  private void searchPlaces(String keyword) {
    if (TextUtils.isEmpty(keyword)) {
      rv.setAdapter(new Adapter(currentVenues));
    }
    if (currentVenues == null) return;
    List<Venue> newTempList = new ArrayList<>();
    for (Venue venue : currentVenues) {
      if (venue.name.toLowerCase().contains(keyword.toLowerCase())) {
        newTempList.add(venue);
        Log.e(TAG, venue.name);
      }
    }

    Log.e(TAG, "Length" + String.valueOf(currentVenues.size()));
    rv.setAdapter(new Adapter(newTempList));
  }

  private void updatePlaces(Location loc) {
    String ll = String.valueOf(loc.getLatitude() + "," + String.valueOf(loc.getLongitude()));
    mainApi.venueSearchLL(ll)
            .delay(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(new Subscriber<BaseModel>() {
              @Override
              public void onCompleted() {
                Log.e(TAG, "Completed");
              }

              @Override
              public void onError(Throwable e) {
                Log.e(TAG, "Error", e);
              }

              @Override
              public void onNext(BaseModel baseModel) {
                currentVenues = baseModel.response.venues;
                rv.setAdapter(new Adapter(currentVenues));
                rv.getAdapter().notifyDataSetChanged();
              }
            });
  }

  @Override
  public void onClick(View view) {
    Log.e(TAG, "Clicked");
    String keyword = etSearch.getText().toString();
    searchPlaces(keyword);
  }

  @Override
  public void onLocationChanged(Location location) {
    currentLoc = location;
    if (currentLoc != null) {
      updatePlaces(currentLoc);
    }

    Log.e("Location lat", String.valueOf(location.getLatitude()));
    Log.e("Location lon", String.valueOf(location.getLongitude()));
  }

  @Override
  public void onStatusChanged(String s, int i, Bundle bundle) {

  }

  @Override
  public void onProviderEnabled(String s) {

  }

  @Override
  public void onProviderDisabled(String s) {

  }
}
