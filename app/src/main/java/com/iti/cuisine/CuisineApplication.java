package com.iti.cuisine;

import android.app.Application;

import com.iti.cuisine.data.meal.MealRepoImpl;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class CuisineApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        MealRepoImpl.initialize(this)
                .subscribeOn(Schedulers.io())
                .retry(3)
                .subscribe();
    }

}
