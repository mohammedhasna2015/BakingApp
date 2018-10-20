package com.mohamedcoder.baking1016.util;

import com.mohamedcoder.baking1016.model.Result;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Mohamed AbuHasna  on 18/10/18.
 */

public interface ApiInterface {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Observable<List<Result>> getDetails();

}
