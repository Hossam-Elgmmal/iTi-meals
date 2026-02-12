package com.iti.cuisine.data.user;

import com.iti.cuisine.data.database_models.FavoriteMealEntity;
import com.iti.cuisine.data.database_models.PlanMealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface UserRepo {

    Observable<UserData> getUser();
    Completable uploadData(List<FavoriteMealEntity> favorites, List<PlanMealEntity> plannedMeals);
    Single<FireUser> getFireUser();
}
