package com.iti.cuisine.auth.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
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
import com.iti.cuisine.utils.google_credentials.GoogleSignInManager;
import com.iti.cuisine.utils.presenter.PresenterHost;
import com.iti.cuisine.utils.snackbar.SnackbarBuilder;

import io.reactivex.rxjava3.core.Single;


public class LoginFragment extends Fragment implements LoginPresenter.LoginView {

    private final String PRESENTER_KEY = "login_presenter";

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

    private CredentialManager credentialManager;
    private PresenterHost presenterHost;

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
            int imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom;
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, Math.max(systemBars.bottom, imeHeight));
            return insets;
        });

        initializeParameters(view);
        initializeListeners();
        credentialManager = CredentialManager.create(requireContext().getApplicationContext());
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

        presenterHost = (PresenterHost) requireActivity();

        presenter = presenterHost
                .getPresenter(PRESENTER_KEY, LoginPresenterImpl::createNewInstance);
        presenter.setView(this);
    }

    private void initializeListeners() {
        loginBtn.setOnClickListener(v -> {
            String email = getTextFrom(emailEditText);
            String password = getTextFrom(passwordEditText);
            presenter.onLoginClick(email, password);
        });
        registerBtn.setOnClickListener(v -> presenter.onGoToSignUpClick());
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
        Navigation.findNavController(requireView())
                .navigate(
                        LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                );
    }

    @Override
    public void navigateToSignUpScreen() {
        Navigation.findNavController(requireView())
                .navigate(
                        LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
                );
    }

    @Override
    public void navigateToForgotPasswordScreen() {
        Navigation.findNavController(requireView())
                .navigate(
                        LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
                );
    }

    @Override
    public void showEmailError() {
        emailTextInputLayout.setError(" ");
    }

    @Override
    public void showPasswordError() {
        passwordTextInputLayout.setError(" ");
    }

    @Override
    public void removeEmailError() {
        emailTextInputLayout.setError(null);
    }

    @Override
    public void removePasswordError() {
        passwordTextInputLayout.setError(null);
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
                .setMessage(message).build();
        presenterHost.showSnackbar(data);
    }

    @Override
    public Single<Credential> getGoogleCredentials() {
        return new GoogleSignInManager().getGoogleCredentials(
                requireActivity(), credentialManager
        );
    }

    @Override
    public void onDestroyView() {
        presenter.removeView();
        if (isRemoving()) {
            presenterHost.removePresenter(PRESENTER_KEY);
        }
        super.onDestroyView();
    }
}