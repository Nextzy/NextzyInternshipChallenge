package com.akexorcist.nextzyintership.network.model;

/**
 * Created by Akexorcist on 5/7/2017 AD.
 */

public class NewUser {
    private String name;

    public NewUser() {
    }

    public NewUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
