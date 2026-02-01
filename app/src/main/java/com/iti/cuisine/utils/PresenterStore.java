package com.iti.cuisine.utils;

import java.util.function.Supplier;

public interface PresenterStore {

    <T extends Presenter> T get(String key, Supplier<T> factory);
    void remove(String key);
    void clear();

}
