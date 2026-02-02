package com.iti.cuisine.utils.presenter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PresenterStoreImpl implements PresenterStore {

    private static final PresenterStoreImpl INSTANCE = new PresenterStoreImpl();

    private PresenterStoreImpl() {
    }

    public static PresenterStoreImpl getInstance() {
        return INSTANCE;
    }

    private final Map<String, Presenter> store = new HashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Presenter> T get(String key, Supplier<T> factory) {
        Presenter presenter = store.get(key);
        if (presenter == null) {
            presenter = factory.get();
            store.put(key, presenter);
        }
        return (T) presenter;
    }

    @Override
    public void remove(String key) {
        Presenter presenter = store.remove(key);
        if (presenter != null) {
            presenter.destroy();
        }
    }

    @Override
    public void clear() {
        store.values().forEach(Presenter::destroy);
        store.clear();
    }
}
