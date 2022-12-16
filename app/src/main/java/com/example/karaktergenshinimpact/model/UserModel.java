package com.example.karaktergenshinimpact.model;

public class UserModel {
    private String namaLengkap, username, email, idUser;

    public UserModel(String namaLengkap, String username, String email, String idUser) {
        this.namaLengkap = namaLengkap;
        this.username = username;
        this.email = email;
        this.idUser = idUser;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
