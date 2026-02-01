package com.iti.cuisine.sign_up;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.iti.cuisine.R;


public class SignUpFragment extends Fragment implements SignUpPresenter.SignUpView {

    private MaterialButton signUpBtn;
    private MaterialButton googleBtn;
    private MaterialButton guestBtn;
    private MaterialTextView loginBtn;
    private TextInputEditText usernameEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText confirmPasswordEditText;
    private TextInputLayout usernameTextInputLayout;
    private TextInputLayout emailTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    private TextInputLayout confirmPasswordTextInputLayout;

    private SignUpPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.signUpFragment), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeParameters(view);
        initializeListeners();
    }

    private void initializeParameters(@NonNull View view) {
        loginBtn = view.findViewById(R.id.login_text);
        googleBtn = view.findViewById(R.id.google_btn);
        guestBtn = view.findViewById(R.id.guest_btn);
        signUpBtn = view.findViewById(R.id.sign_up_btn);

        usernameEditText = view.findViewById(R.id.username_edit_text);
        usernameTextInputLayout = view.findViewById(R.id.username_text_input_layout);
        emailEditText = view.findViewById(R.id.email_edit_text);
        emailTextInputLayout = view.findViewById(R.id.email_text_input_layout);
        passwordEditText = view.findViewById(R.id.password_edit_text);
        passwordTextInputLayout = view.findViewById(R.id.password_text_input_layout);
        confirmPasswordEditText = view.findViewById(R.id.confirm_password_edit_text);
        confirmPasswordTextInputLayout = view.findViewById(R.id.confirm_password_text_input_layout);

        presenter = new SignUpPresenterImpl(this);
    }

    private void initializeListeners() {
        signUpBtn.setOnClickListener(v -> {
            String username = getTextFrom(usernameEditText);
            String email = getTextFrom(emailEditText);
            String password = getTextFrom(passwordEditText);
            String confirmPassword = getTextFrom(confirmPasswordEditText);

            presenter.onSignUpClick(username, email, password, confirmPassword);
        });
        loginBtn.setOnClickListener(v -> presenter.onGoToLoginClick());
        googleBtn.setOnClickListener(v -> presenter.onGoogleLoginClick());
        guestBtn.setOnClickListener(v -> presenter.onGuestLoginClick());
    }

    @NonNull
    private String getTextFrom(@Nullable EditText editText) {
        return editText != null && editText.getText() != null
                ? editText.getText().toString().trim()
                : "";
    }

    @Override
    public void navigateToHomeScreen() {
        Navigation.findNavController(requireView())
                .navigate(
                        SignUpFragmentDirections.actionSignUpFragmentToHomeFragment()
                );
    }

    @Override
    public void navigateToLoginScreen() {
        Navigation.findNavController(requireView())
                .navigate(
                        SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
                );
    }

    @Override
    public void showLoading() {
        //todo
    }

    @Override
    public void hideLoading() {
        //todo
    }

    @Override
    public void showErrorMessage(String message) {
        //todo
    }
}