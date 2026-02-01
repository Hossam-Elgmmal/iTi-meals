package com.iti.cuisine.splash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iti.cuisine.MainActivity;
import com.iti.cuisine.R;


public class SplashFragment extends Fragment implements SplashPresenter.SplashView {

    private final String PRESENTER_KEY = "splash_presenter";

    private SplashPresenter presenter;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        presenter = ((MainActivity) requireActivity())
                .getPresenter(PRESENTER_KEY, SplashPresenterImpl::new);
        presenter.setView(this);
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.navigateToNextScreen();
    }

    @Override
    public void navigateToLoginScreen() {
        navController.navigate(
                SplashFragmentDirections
                        .actionSplashFragmentToLoginFragment()
        );
    }

    @Override
    public void navigateToHomeScreen() {
        navController.navigate(
                SplashFragmentDirections
                        .actionSplashFragmentToHomeFragment()
        );
    }

    @Override
    public void onDestroyView() {
        presenter.removeView();
        if (isRemoving()) {
            ((MainActivity) requireActivity()).removePresenter(PRESENTER_KEY);
        }
        super.onDestroyView();
    }
}