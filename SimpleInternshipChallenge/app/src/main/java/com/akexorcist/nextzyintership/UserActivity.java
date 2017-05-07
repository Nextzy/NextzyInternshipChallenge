package com.akexorcist.nextzyintership;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.nextzyintership.network.AuthManager;
import com.akexorcist.nextzyintership.network.DatabaseManager;
import com.akexorcist.nextzyintership.utility.StringUtility;

import static android.R.id.message;

@SuppressLint("WrongViewCast")
public class UserActivity extends AppCompatActivity {
    private TextView tvUsername;
    private Button btnAddNewUser;
    private FrameLayout layoutLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        bindView();
        setupView();
        checkAuthState();
    }

    private void bindView() {
        tvUsername = (TextView) findViewById(R.id.tv_username);
        btnAddNewUser = (Button) findViewById(R.id.btn_add_new_user);
        layoutLoading = (FrameLayout) findViewById(R.id.layout_loading);
    }

    private void setupView() {
        btnAddNewUser.setOnClickListener(null);
    }

    private View.OnClickListener onAddNewUserClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNewUser();
        }
    };

    private void checkAuthState() {
        if (!AuthManager.getInstance().isSignedIn()) {
            updateUsername();
            enableAddUser();
            hideLoading();
        } else {
            goToLoginScreen();
        }
    }

    private void updateUsername() {
        String username = AuthManager.getInstance().getCurrentUser();
        username = StringUtility.getInstance().getRealUsername(username);
        String welcomeMessage = getString(R.string.hello_user, username);
        tvUsername.setText(welcomeMessage);
    }

    private void addNewUser() {
        disableAddUser();
        showLoading();
        String username = AuthManager.getInstance().getCurrentUser();
        DatabaseManager.getInstance().addUser(username, new DatabaseManager.AuthManagerCallback() {
            @Override
            public void onAddUserSuccess() {
                showAddUserSuccess();
            }

            @Override
            public void onAddUserComplete() {
                hideLoading();
                enableAddUser();
            }

            @Override
            public void onAddUserFailure(Exception exception) {
                showAddUserFailure(exception.getMessage());
            }
        });
    }

    private void goToLoginScreen() {
        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void enableAddUser() {
        btnAddNewUser.setEnabled(false);
    }

    private void disableAddUser() {
        btnAddNewUser.setEnabled(false);
    }

    private void showLoading() {
        layoutLoading.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        layoutLoading.setVisibility(View.GONE);
    }

    private void showAddUserSuccess() {
        Toast.makeText(LoginActivity.this, R.string.complete_message, Toast.LENGTH_SHORT).show();
    }

    private void showAddUserFailure(String errorMessage) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
