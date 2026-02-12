package com.iti.cuisine.data.user;

public class UserData {


    private final String uid;
    private final String username;
    private final String email;
    private final boolean isGuest;

    public UserData(String uid, String username, String email, boolean isGuest) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.isGuest = isGuest;
    }

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isGuest() {
        return isGuest;
    }
}
