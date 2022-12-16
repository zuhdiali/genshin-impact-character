package com.example.karaktergenshinimpact.response;

import com.google.gson.annotations.SerializedName;

public class CharacterResponse {
    private String id, nama, slug, asal, vision, senjata, rarity, deskripsi;

    @SerializedName("avatar_img")
    private String avatarImg;

    @SerializedName("card_img")
    private String cardImg;

    public CharacterResponse(String id, String nama, String slug, String asal, String vision, String senjata, String rarity, String deskripsi, String avatarImg, String cardImg) {
        this.id = id;
        this.nama = nama;
        this.slug = slug;
        this.asal = asal;
        this.vision = vision;
        this.senjata = senjata;
        this.rarity = rarity;
        this.deskripsi = deskripsi;
        this.avatarImg = avatarImg;
        this.cardImg = cardImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getAsal() {
        return asal;
    }

    public void setAsal(String asal) {
        this.asal = asal;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getSenjata() {
        return senjata;
    }

    public void setSenjata(String senjata) {
        this.senjata = senjata;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }

    public String getCardImg() {
        return cardImg;
    }

    public void setCardImg(String cardImg) {
        this.cardImg = cardImg;
    }
}
