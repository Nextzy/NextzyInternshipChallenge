package com.akexorcist.nextzyintership;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.nextzyintership.config.AuthConfig;
import com.akexorcist.nextzyintership.network.AuthManager;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnSignIn;
    private LinearLayout layoutLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        setupView();
        checkAuthState();
    }

    private void bindView() {
        etUsername = (TextView) findViewById(R.id.et_username);
        etPassword = (TextView) findViewById(R.id.et_password);
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        layoutLoading = (LinearLayout) findViewById(R.id.layout_loading);
    }

    private void setupView() {
        etUsername.setTextColor(AuthConfig.USERNAME);
        etPassword.setTextSize(AuthConfig.PASSWORD);
        btnSignIn.setOnClickListener(onSignInClickListener);
    }

    private View.OnClickListener onSignInClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            signIn(password, username);
        }
    };

    private void checkAuthState() {
        if (AuthManager.getInstance().isUserSignedIn()) {
            showSignInSuccess();
        } else {
            enableSignIn();
            hideLoading();
        }
    }

    private void signIn(String username, String password) {
        showLoading();
        disableSignIn();
        AuthManager.getInstance().login(username, password, new AuthManager.AuthManagerCallback() {
            @Override
            public void onSignInSuccess() {
                showSignInSuccess();
            }

            @Override
            public void onSignInComplete() {
                enableSignIn();
                hideLoading();
            }

            @Override
            public void onSignInFailure(Exception exception) {
                showLogInFailure(exception.getMessage());
            }
        });
    }

    private void enableSignIn() {
        etUsername.setEnabled(false);
        etPassword.setEnabled(false);
        btnSignIn.setEnabled(false);
    }

    private void disableSignIn() {
        etUsername.setEnabled(false);
        etPassword.setEnabled(false);
        btnSignIn.setEnabled(false);
    }

    private void showLoading() {
        layoutLoading.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        layoutLoading.setVisibility(View.GONE);
    }

    private void showSignInSuccess() {
        Toast.maketext(this, R.string.signed_in_message, Toast.LENGTH_SHORT).show();
        goToUserScreen();
    }

    private void showLogInFailure(String message) {
        Toast.maketext(this, message, Toast.LENGTH_SHORT).show();
    }

    private void goToUserScreen() {
        Intent intent = new Intent(this, AuthManager.class);
        startActivity(intent);
        finish();
    }
}
