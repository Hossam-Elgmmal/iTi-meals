package com.iti.cuisine.data.user;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.iti.cuisine.data.database_models.FavoriteMealEntity;
import com.iti.cuisine.data.database_models.PlanMealEntity;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserRepoImpl implements UserRepo {

    private final String USERS = "users";

    @Override
    public Observable<UserData> getUserObservable() {
        return Observable.create(emitter -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();

            FirebaseAuth.AuthStateListener listener = firebaseAuth -> {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user == null) {
                    emitter.onNext(new UserData("", "", "", true));
                } else {
                    emitter.onNext(new UserData(
                            user.getUid(),
                            user.getDisplayName() != null ? user.getDisplayName() : "",
                            user.getEmail() != null ? user.getEmail() : "",
                            user.isAnonymous()
                    ));
                }
            };
            auth.addAuthStateListener(listener);
            listener.onAuthStateChanged(auth);
            emitter.setCancellable(() -> auth.removeAuthStateListener(listener));
        });
    }

    @Override
    public UserData getUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            return new UserData("", "", "", true);
        } else {
            return new UserData(
                    user.getUid(),
                    user.getDisplayName() != null ? user.getDisplayName() : "",
                    user.getEmail() != null ? user.getEmail() : "",
                    user.isAnonymous()
            );
        }
    }

    @Override
    public Completable uploadData(List<FavoriteMealEntity> favorites,
                                  List<PlanMealEntity> plannedMeals) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return Completable.error(new IllegalStateException("User not logged in"));
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = user.getUid();

        return Completable.create(emitter -> {

            FireUser fireUser = new FireUser(
                    favorites.stream()
                            .map(FireFavoriteMapper::toFireFavoriteMeal)
                            .collect(Collectors.toList()),
                    plannedMeals.stream()
                            .map(FirePlanMapper::toFirePlanMeal)
                            .collect(Collectors.toList())
            );

            db.collection(USERS)
                    .document(userId)
                    .set(fireUser)
                    .addOnSuccessListener(unused -> {
                        emitter.onComplete();
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    @Override
    public Single<FireUser> getFireUser() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return Single.just((new FireUser(List.of(), List.of())));
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = user.getUid();

        return Single.<FireUser>create(emitter -> {
            db.collection(USERS)
                    .document(userId)
                    .get()
                    .addOnSuccessListener(querySnapshot -> {
                        if (!querySnapshot.exists()) {
                            emitter.onSuccess(new FireUser(List.of(), List.of()));
                        } else {
                            FireUser fireUser = querySnapshot.toObject(FireUser.class);
                            emitter.onSuccess(fireUser);
                        }
                    })
                    .addOnFailureListener(emitter::onError);
        }).subscribeOn(Schedulers.io());
    }
}
