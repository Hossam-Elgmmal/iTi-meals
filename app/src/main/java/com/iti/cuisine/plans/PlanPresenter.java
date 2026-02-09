package com.iti.cuisine.plans;


import com.iti.cuisine.utils.presenter.Presenter;

public interface PlanPresenter extends Presenter {


    interface PlanView {


    }
    void setView(PlanView view);

    void removeView();

}
