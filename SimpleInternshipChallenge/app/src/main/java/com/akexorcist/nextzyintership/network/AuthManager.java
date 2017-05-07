package com.akexorcist.nextzyintership.network;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Akexorcist on 5/7/2017 AD.
 */

public class AuthManager {
    private static AuthManager manager;

    public static AuthManager getInstance() {
        if (manager == null) {
            manager = new AuthManager();
        }
        return manager;
    }

    public void login(String username, String password, final AuthManagerCallback callback) {
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (callback != null) {
                            callback.onSignInComplete();
                        }
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if (callback != null) {
                            callback.onSignInSuccess();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (callback != null) {
                            callback.onSignInFailure(e);
                        }
                    }
                });
    }

    public String getCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return user.getEmail();
        } else {
            return null;
        }
    }

    public boolean isSignedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public interface AuthManagerCallback {
        void onSignInSuccess();

        void onSignInComplete();

        void onSignInFailure(Exception exception);
    }

}
