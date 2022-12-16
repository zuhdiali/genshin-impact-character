package com.example.karaktergenshinimpact.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    private String role, idUser, email, username;

    @SerializedName("nama_lengkap")
    private String namaLengkap;

    @SerializedName("access_token")
    private String token;

    @SerializedName("messages")
    private PesanError pesanError;

    public static class PesanError {
        private String error;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

    ;

    public LoginResponse(String role, String idUser, String email, String username, String namaLengkap, String token, PesanError pesanError) {
        this.role = role;
        this.idUser = idUser;
        this.email = email;
        this.username = username;
        this.namaLengkap = namaLengkap;
        this.token = token;
        this.pesanError = pesanError;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PesanError getPesanError() {
        return pesanError;
    }

    public void setPesanError(PesanError pesanError) {
        this.pesanError = pesanError;
    }
}
