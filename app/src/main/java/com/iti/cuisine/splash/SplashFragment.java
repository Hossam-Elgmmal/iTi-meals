package com.iti.cuisine.splash;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iti.cuisine.R;


public class SplashFragment extends Fragment implements SplashPresenter.SplashView {

    private Handler handler;
    private SplashPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        handler = new Handler(Looper.getMainLooper());
        presenter = new SplashPresenterImpl(this);

        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.navigateToNextScreen();
    }

    @Override
    public void navigateToLoginScreen() {
        handler.postDelayed(
                () -> Navigation.findNavController(requireView())
                        .navigate(
                                SplashFragmentDirections
                                        .actionSplashFragmentToLoginFragment()
                        ),
                3000
        );
    }

    @Override
    public void navigateToHomeScreen() {
        handler.postDelayed(
                () -> Navigation.findNavController(requireView())
                        .navigate(
                                SplashFragmentDirections
                                        .actionSplashFragmentToHomeFragment()
                        ),
                3000
        );
    }
}