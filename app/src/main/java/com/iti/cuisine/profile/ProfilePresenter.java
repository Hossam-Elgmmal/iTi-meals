package com.iti.cuisine.profile;

import android.app.Application;

import com.iti.cuisine.data.user.UserData;
import com.iti.cuisine.utils.presenter.Presenter;

public interface ProfilePresenter extends Presenter {


    interface ProfileView {

        void showLoading();

        void hideLoading();

        void showMessage(int messageId);

        void navigateToLogin();

        void setPlanCount(String count);

        void setFavoriteCount(String count);

        void setUseData(UserData userData);
        void setEmail(String email);
    }

    void setView(ProfileView view);

    void uploadData();
    void signOut(Application application);

    void removeView();
}
