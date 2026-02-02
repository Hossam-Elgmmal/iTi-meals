package com.iti.cuisine.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.iti.cuisine.R;
import com.iti.cuisine.data.auth.AuthRepoImpl;


public class HomeFragment extends Fragment {

    Button signOutBtn;
    NavController navController;

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
        signOutBtn = view.findViewById(R.id.sign_out_btn);
        navController = Navigation.findNavController(view);
        signOutBtn.setOnClickListener(v -> {
            new AuthRepoImpl().signOut();
            navController.navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment());
        });
    }
}