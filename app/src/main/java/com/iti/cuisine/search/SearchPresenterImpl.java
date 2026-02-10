package com.iti.cuisine.search;


import androidx.annotation.Nullable;

import com.iti.cuisine.data.meal.MealErrorResult;
import com.iti.cuisine.data.meal.MealRepo;
import com.iti.cuisine.data.meal.MealRepoImpl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class SearchPresenterImpl implements SearchPresenter {

    private boolean isInitialized = false;

    private final BehaviorSubject<Optional<SearchItem>> selectedSearchItem = BehaviorSubject.create();
    private final BehaviorSubject<List<SearchItem>> allSearchItems = BehaviorSubject.create();
    private final BehaviorSubject<SearchMode> searchMode = BehaviorSubject.create();
    private final BehaviorSubject<Boolean> showLoading = BehaviorSubject.create();

    private final CompositeDisposable disposables = new CompositeDisposable();

    private Disposable loadingDisposable;
    private Disposable selectedItemDisposable;
    private Disposable searchTextDisposable;

    private Disposable fetchDisposable;
    private Disposable itemsDisposable;

    private final MealRepo mealRepo;

    @Nullable
    private SearchView view;

    public static SearchPresenterImpl createNewInstance() {
        return new SearchPresenterImpl(MealRepoImpl.getInstance());
    }

    private SearchPresenterImpl(MealRepo mealRepo) {
        this.mealRepo = mealRepo;
        refreshData();
    }

    @Override
    public void refreshData() {
        if (fetchDisposable != null) {
            disposables.remove(fetchDisposable);
        }
        fetchDisposable = mealRepo.fetchCategories()
                .andThen(mealRepo.fetchCountries())
                .andThen(mealRepo.fetchIngredients())
                .doOnSubscribe(a -> showLoading.onNext(true))
                .subscribe(() -> {
                    showLoading.onNext(false);
                }, t -> {
                    showLoading.onNext(false);
                    if (view != null) {
                        view.showMessage(
                                MealErrorResult.fromThrowable(t).getMessageId()
                        );
                    }
                });
        disposables.add(fetchDisposable);
    }

    @Override
    public void showIngredients() {
        searchMode.onNext(SearchMode.INGREDIENT);
        if (itemsDisposable != null) {
            disposables.remove(itemsDisposable);
        }
        itemsDisposable = mealRepo.getAllIngredients()
                .filter(l -> !l.isEmpty())
                .take(1)
                .map(ingredientEntities -> ingredientEntities.stream()
                        .map(item -> new SearchItem(
                                item.getId(),
                                item.getTitle(),
                                item.getThumbnail(),
                                SearchItem.ViewType.TYPE_SEARCH
                        )).collect(Collectors.toList())
                )
                .onErrorReturn(t -> List.of())
                .subscribe(allSearchItems::onNext);

        disposables.add(itemsDisposable);
    }

    @Override
    public void showCategories() {
        searchMode.onNext(SearchMode.CATEGORY);
        if (itemsDisposable != null) {
            disposables.remove(itemsDisposable);
        }
        itemsDisposable = mealRepo.getAllCategories()
                .filter(l -> !l.isEmpty())
                .take(1)
                .map(categoryEntityList -> categoryEntityList.stream()
                        .map(item -> new SearchItem(
                                item.getId(),
                                item.getTitle(),
                                item.getThumbnail(),
                                SearchItem.ViewType.TYPE_SEARCH
                        )).collect(Collectors.toList())
                )
                .onErrorReturn(t -> List.of())
                .subscribe(allSearchItems::onNext);

        disposables.add(itemsDisposable);
    }

    @Override
    public void showCountries() {
        searchMode.onNext(SearchMode.COUNTRY);
        if (itemsDisposable != null) {
            disposables.remove(itemsDisposable);
        }
        itemsDisposable = mealRepo.getAllCountries()
                .filter(l -> !l.isEmpty())
                .take(1)
                .map(countryEntityList -> countryEntityList.stream()
                        .map(item -> new SearchItem(
                                item.getTitle(),
                                item.getTitle(),
                                item.getFlagUrl(),
                                SearchItem.ViewType.TYPE_SEARCH
                        )).collect(Collectors.toList())
                )
                .onErrorReturn(t -> List.of())
                .subscribe(allSearchItems::onNext);

        disposables.add(itemsDisposable);
    }

    @Override
    public void setSelectedItem(SearchItem searchItem) {
        selectedSearchItem.onNext(Optional.of(searchItem));
        SearchMode mode = searchMode.getValue();
        if (mode == null) return;
        switch (mode) {
            case CATEGORY: {
                getMealsForCategory(searchItem.getTitle());
                break;
            }
            case COUNTRY: {
                getMealsForCountry(searchItem.getTitle());
                break;
            }
            default: {
                getMealsForIngredient(searchItem.getTitle());
                break;
            }
        }
    }

    private void getMealsForCategory(String title) {
        fetchDisposable = mealRepo.fetchMealsByCategory(title)
                .doOnSubscribe(a -> showLoading.onNext(true))
                .subscribe(list -> {
                    showLoading.onNext(false);
                    allSearchItems.onNext(list);
                }, t -> {
                    showLoading.onNext(false);
                    clearSelectedItem();
                    if (view != null) {
                        view.showMessage(
                                MealErrorResult.fromThrowable(t).getMessageId()
                        );
                    }
                });
        disposables.add(fetchDisposable);
    }

    private void getMealsForCountry(String title) {
        fetchDisposable = mealRepo.fetchMealsByCountry(title)
                .doOnSubscribe(a -> showLoading.onNext(true))
                .subscribe(list -> {
                    showLoading.onNext(false);
                    allSearchItems.onNext(list);
                }, t -> {
                    showLoading.onNext(false);
                    clearSelectedItem();
                    if (view != null) {
                        view.showMessage(
                                MealErrorResult.fromThrowable(t).getMessageId()
                        );
                    }
                });
        disposables.add(fetchDisposable);

    }

    private void getMealsForIngredient(String title) {
        fetchDisposable = mealRepo.fetchMealsByIngredient(title)
                .doOnSubscribe(a -> showLoading.onNext(true))
                .subscribe(list -> {
                    showLoading.onNext(false);
                    allSearchItems.onNext(list);
                }, t -> {
                    showLoading.onNext(false);
                    clearSelectedItem();
                    if (view != null) {
                        view.showMessage(
                                MealErrorResult.fromThrowable(t).getMessageId()
                        );
                    }
                });
        disposables.add(fetchDisposable);
    }

    @Override
    public void clearSelectedItem() {
        selectedSearchItem.onNext(Optional.empty());
        SearchMode mode = searchMode.getValue();
        if (mode == null) return;
        switch (mode) {
            case CATEGORY: {
                showCategories();
                break;
            }
            case INGREDIENT: {
                showIngredients();
                break;
            }
            default: {
                showCountries();
                break;
            }
        }
    }

    @Override
    public void setView(SearchView view) {
        this.view = view;
        listenToLoading();
        listenToSelectedItem();
        listenToSearchText();
    }

    private void listenToSearchText() {
        if (view == null) return;

        searchTextDisposable = Observable.combineLatest(
                        view.getSearchText()
                                .debounce(300, TimeUnit.MILLISECONDS)
                                .map(String::trim)
                                .distinctUntilChanged(),
                        allSearchItems.startWithItem(List.of()),
                        SearchParams::new
                )
                .switchMap(params -> Observable.fromCallable(() -> {
                                    if (params.items == null || params.items.isEmpty()) {
                                        return List.<SearchItem>of();
                                    }
                                    if (params.query.isEmpty()) {
                                        return params.items.stream()
                                                .limit(30)
                                                .collect(Collectors.toList());
                                    }
                                    String lowerQuery = params.query.toLowerCase();
                                    return params.items.stream()
                                            .filter(item -> item.getLowerTitle().contains(lowerQuery))
                                            .limit(30)
                                            .collect(Collectors.toList());
                                })
                                .subscribeOn(Schedulers.computation())
                )
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> List.of())
                .subscribe(searchItems -> {
                    if (view != null) {
                        view.setSearchItems(searchItems);
                    }
                });

        disposables.add(searchTextDisposable);
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    private static class SearchParams {
        final String query;
        final List<SearchItem> items;

        SearchParams(String query, List<SearchItem> items) {
            this.query = query;
            this.items = items;
        }
    }

    private void listenToSelectedItem() {
        selectedItemDisposable = selectedSearchItem.subscribe(searchItem -> {
            if (view != null) {
                if (searchItem.isPresent()) {
                    view.setSelectedItem(searchItem.get());
                } else {
                    view.clearSelectedItem();
                }
            }
        });
        disposables.add(selectedItemDisposable);
    }

    private void listenToLoading() {
        loadingDisposable = showLoading.subscribe(show -> {
            if (view != null) {
                if (show) {
                    view.showLoading();
                } else {
                    view.hideLoading();
                }
            }
        });
        disposables.add(loadingDisposable);
    }

    @Override
    public void removeView() {
        clearSelectedItem();
        stopListeningToDiscoverData();
        view = null;
    }

    private void stopListeningToDiscoverData() {
        disposables.remove(loadingDisposable);
        disposables.remove(selectedItemDisposable);
        disposables.remove(searchTextDisposable);
        disposables.remove(fetchDisposable);
    }

    @Override
    public void destroy() {
        disposables.clear();
    }
}
