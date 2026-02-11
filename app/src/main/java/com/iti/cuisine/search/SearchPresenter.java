package com.iti.cuisine.search;


import com.iti.cuisine.utils.presenter.Presenter;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;


public interface SearchPresenter extends Presenter {


    interface SearchView {

        void showLoading();
        void hideLoading();

        void showMessage(int messageId);

        void setSelectedItem(SearchItem searchItem);

        void clearSelectedItem();

        Observable<String> getSearchText();

        void setSearchItems(List<SearchItem> searchItems);
    }


    void showIngredients();
    void showCategories();
    void showCountries();
    void setSelectedItem(SearchItem searchItem);
    void clearSelectedItem();
    void setView(SearchView view);
    void refreshData();
    void removeView();
}
