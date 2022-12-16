package com.example.karaktergenshinimpact.model;

public class CharacterModel {
    private String nama, asal, vision, senjata, rarity, deskripsi, cardImgUrl, avatarImgUrl;

    public CharacterModel(String nama, String asal, String vision, String senjata, String rarity, String deskripsi, String cardImgUrl, String avatarImgUrl) {
        this.nama = nama;
        this.asal = asal;
        this.vision = vision;
        this.senjata = senjata;
        this.rarity = rarity;
        this.deskripsi = deskripsi;
        this.cardImgUrl = cardImgUrl;
        this.avatarImgUrl = avatarImgUrl;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public String getCardImgUrl() {
        return cardImgUrl;
    }

    public void setCardImgUrl(String cardImgUrl) {
        this.cardImgUrl = cardImgUrl;
    }

    public String getAvatarImgUrl() {
        return avatarImgUrl;
    }

    public void setAvatarImgUrl(String avatarImgUrl) {
        this.avatarImgUrl = avatarImgUrl;
    }
}
