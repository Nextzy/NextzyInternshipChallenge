package com.akexorcist.nextzyintership.network;

import android.support.annotation.NonNull;

import com.akexorcist.nextzyintership.network.model.NewUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Akexorcist on 5/7/2017 AD.
 */

public class DatabaseManager {
    private static DatabaseManager manager;

    public static DatabaseManager getInstance() {
        if (manager == null) {
            manager = new DatabaseManager();
        }
        return manager;
    }

    public void addUser(String username, final AuthManagerCallback callback) {
        NewUser newUser = new NewUser(username);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users");
        reference.push()
                .setValue(newUser)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (callback != null) {
                            callback.onAddUserComplete();
                        }
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (callback != null) {
                            callback.onAddUserSuccess();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (callback != null) {
                            callback.onAddUserFailure(e);
                        }
                    }
                });
    }

    public interface AuthManagerCallback {
        void onAddUserSuccess();

        void onAddUserComplete();

        void onAddUserFailure(Exception exception);
    }
}
