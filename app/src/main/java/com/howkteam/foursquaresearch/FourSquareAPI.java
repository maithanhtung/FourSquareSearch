package com.howkteam.foursquaresearch;

import com.howkteam.foursquaresearch.models.BaseModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface FourSquareAPI {

  @GET("venues/search/")
  Observable<BaseModel> venueSearch(@Query("near") String near);

  @GET("venues/search/")
  Observable<BaseModel> venueSearchLL(@Query("ll") String latlon);
}
