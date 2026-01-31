package com.iti.cuisine.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.iti.cuisine.R;


public class LoginFragment extends Fragment implements LoginPresenter.LoginView {

    private MaterialButton loginBtn;
    private MaterialButton googleBtn;
    private MaterialButton guestBtn;
    private MaterialTextView registerBtn;
    private MaterialTextView forgotPasswordBtn;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private TextInputLayout emailTextInputLayout;
    private TextInputLayout passwordTextInputLayout;

    private LoginPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.loginFragment), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeParameters(view);
        initializeListeners();
    }

    private void initializeParameters(@NonNull View view) {
        loginBtn = view.findViewById(R.id.login_btn);
        googleBtn = view.findViewById(R.id.google_btn);
        guestBtn = view.findViewById(R.id.guest_btn);
        registerBtn = view.findViewById(R.id.register_text);
        forgotPasswordBtn = view.findViewById(R.id.forgot_password_text);
        emailEditText = view.findViewById(R.id.email_edit_text);
        passwordEditText = view.findViewById(R.id.password_edit_text);
        emailTextInputLayout = view.findViewById(R.id.email_text_input_layout);
        passwordTextInputLayout = view.findViewById(R.id.password_text_input_layout);

        presenter = new LoginPresenterImpl(this);
    }

    private void initializeListeners() {
        loginBtn.setOnClickListener(v -> {
            String email = getTextFrom(emailEditText);
            String password = getTextFrom(passwordEditText);
            presenter.onLoginClick(email, password);
        });
        registerBtn.setOnClickListener(v -> presenter.onSignUpClick());
        forgotPasswordBtn.setOnClickListener(v -> presenter.onForgotPasswordClick());
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
        //todo
    }

    @Override
    public void navigateToSignUpScreen() {
        //todo
    }

    @Override
    public void navigateToForgotPasswordScreen() {
        //todo
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