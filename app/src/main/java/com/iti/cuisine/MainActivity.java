package com.iti.cuisine;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.iti.cuisine.utils.LoadingDialog;
import com.iti.cuisine.utils.Presenter;
import com.iti.cuisine.utils.PresenterStore;
import com.iti.cuisine.utils.PresenterStoreImpl;

import java.util.function.Supplier;

public class MainActivity extends AppCompatActivity {

    private PresenterStore presenterStore;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        presenterStore = PresenterStoreImpl.getInstance();
        loadingDialog = new LoadingDialog(this);
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

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            presenterStore.clear();
        }
    }
}