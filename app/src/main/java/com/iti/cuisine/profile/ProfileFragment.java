package com.iti.cuisine.profile;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.cuisine.MainNavGraphDirections;
import com.iti.cuisine.R;
import com.iti.cuisine.data.user.UserData;
import com.iti.cuisine.utils.presenter.PresenterHost;
import com.iti.cuisine.utils.snackbar.SnackbarBuilder;


public class ProfileFragment extends Fragment implements ProfilePresenter.ProfileView {

    private final String PRESENTER_KEY = "profile_presenter";


    private TextView username;
    private TextView emailTextView;
    private TextView plansCount;
    private TextView favoritesCount;
    private Button backupBtn;
    private Button logoutBtn;

    private ProfilePresenter presenter;

    private PresenterHost presenterHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.profile_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, 0);
            return insets;
        });

        initializeParameters(view);
        initializeOnClickListeners();
    }

    private void initializeParameters(@NonNull View view) {

        username = view.findViewById(R.id.username);
        emailTextView = view.findViewById(R.id.email);
        plansCount = view.findViewById(R.id.plans_count);
        favoritesCount = view.findViewById(R.id.favorite_count);
        backupBtn = view.findViewById(R.id.backup_btn);
        logoutBtn = view.findViewById(R.id.logout_btn);

        presenterHost = (PresenterHost) requireActivity();

        presenter = presenterHost
                .getPresenter(PRESENTER_KEY, ProfilePresenterImpl::createNewInstance);

        presenter.setView(this);

    }

    private void initializeOnClickListeners() {
        backupBtn.setOnClickListener(v -> presenter.uploadData());
        logoutBtn.setOnClickListener(v -> showLogoutConfirmDialog());
    }

    private void showLogoutConfirmDialog() {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_confirm_delete);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        ImageView icon = dialog.findViewById(R.id.iconImage);
        TextView title = dialog.findViewById(R.id.titleText);
        TextView description = dialog.findViewById(R.id.descriptionTextView);
        Button cancelButton = dialog.findViewById(R.id.btnCancel);
        Button confirm = dialog.findViewById(R.id.btnConfirm);

        icon.setImageResource(R.drawable.ic_logout);
        title.setText(R.string.are_you_sure_you_want_to_sign_out);
        description.setText(R.string.you_will_lose_all_data_that_hasn_t_been_backed_up);

        cancelButton.setOnClickListener(v -> dialog.dismiss());
        confirm.setOnClickListener(v -> {
            presenter.signOut(requireActivity().getApplication());
            dialog.dismiss();
        });
        dialog.show();
    }


    @Override
    public void showLoading() {
        presenterHost.showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        presenterHost.hideLoadingDialog();
    }

    @Override
    public void showMessage(int messageId) {
        String message = getString(messageId);
        SnackbarBuilder snackbarBuilder = new SnackbarBuilder();
        SnackbarBuilder.SnackbarData data = snackbarBuilder
                .setMessage(message)
                .setBottomPadding(84)
                .build();
        presenterHost.showSnackbar(data);
    }

    @Override
    public void navigateToLogin() {
        NavDirections action = MainNavGraphDirections
                .actionGlobalLoginFragment();

        presenterHost.navigate(action);
    }

    @Override
    public void setPlanCount(String count) {
        plansCount.setText(count);
    }

    @Override
    public void setFavoriteCount(String count) {
        favoritesCount.setText(count);
    }

    @Override
    public void setUseData(UserData userData) {
        if (userData.isGuest()) {
            username.setText(R.string.guest);
            emailTextView.setText(R.string.guest);
            return;
        }
        username.setText(userData.getUsername());
        emailTextView.setText(userData.getEmail());
    }

    @Override
    public void setEmail(String email) {
        emailTextView.setText(email);
    }

    @Override
    public void onDestroyView() {
        presenter.removeView();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        if (isRemoving() && presenterHost != null) {
            presenterHost.removePresenter(PRESENTER_KEY);
        }
        super.onDetach();
    }
}