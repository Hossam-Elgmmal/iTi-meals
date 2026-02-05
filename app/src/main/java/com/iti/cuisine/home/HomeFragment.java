package com.iti.cuisine.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.iti.cuisine.R;


public class HomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavHostFragment navFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);

        NavController navController = navFragment.getNavController();
        BottomNavigationView bottomNav = view.findViewById(R.id.bottom_navigation);

        NavigationUI.setupWithNavController(bottomNav, navController);

        FragmentContainerView container = view.findViewById(R.id.fragmentContainerView);
        bottomNav.post(() -> {
            ViewGroup.MarginLayoutParams params =
                    (ViewGroup.MarginLayoutParams) container.getLayoutParams();

            params.bottomMargin = bottomNav.getHeight();
            container.setLayoutParams(params);
        });
    }
}