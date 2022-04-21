package com.uva.tai.model;

public class Auth {

    private String email;
    private String password;
    private String tocken;

    public Auth() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }


    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return this.tocken;
    }

    public void setToken(String tocken) {
        this.tocken = tocken;
    }

}
