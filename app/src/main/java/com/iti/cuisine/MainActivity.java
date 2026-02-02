package com.iti.cuisine;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.iti.cuisine.utils.loading.LoadingDialog;
import com.iti.cuisine.utils.presenter.Presenter;
import com.iti.cuisine.utils.presenter.PresenterStore;
import com.iti.cuisine.utils.presenter.PresenterStoreImpl;
import com.iti.cuisine.utils.snackbar.SnackbarBuilder;
import com.iti.cuisine.utils.snackbar.SnackbarManager;

import java.util.function.Supplier;

public class MainActivity extends AppCompatActivity {

    private PresenterStore presenterStore;
    private LoadingDialog loadingDialog;
    private SnackbarManager snackbarManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        presenterStore = PresenterStoreImpl.getInstance();
        loadingDialog = new LoadingDialog(this);
        snackbarManager = new SnackbarManager();
    }

    public <T extends Presenter> T getPresenter(String key, Supplier<T> factory) {
        return presenterStore.get(key, factory);
    }

    public void removePresenter(String key) {
        presenterStore.remove(key);
    }

    public void showLoadingDialog() {
        loadingDialog.show();
    }

    public void hideLoadingDialog() {
        loadingDialog.dismiss();
    }

    public void showSnackbar(SnackbarBuilder.SnackbarData data) {
        snackbarManager.showSnackbar(data, this, findViewById(R.id.main_fragment_container));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            presenterStore.clear();
        }
    }
}