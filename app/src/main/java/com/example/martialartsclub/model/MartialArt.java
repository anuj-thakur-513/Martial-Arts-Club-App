package com.example.martialartsclub.model;

public class MartialArt {

    private String martialArtName;
    private String martialArtColor;
    private double martialArtPrice;
    private int martialArtId;

    public MartialArt(int id, String name, double price, String color){
        setMartialArtName(name);
        setMartialArtColor(color);
        setMartialArtPrice(price);
        setMartialArtId(id);
    }

    public String getMartialArtName() {
        return martialArtName;
    }

    public void setMartialArtName(String martialArtName) {
        this.martialArtName = martialArtName;
    }

    public String getMartialArtColor() {
        return martialArtColor;
    }

    public void setMartialArtColor(String martialArtColor) {
        this.martialArtColor = martialArtColor;
    }

    public double getMartialArtPrice() {
        return martialArtPrice;
    }

    public void setMartialArtPrice(double martialArtPrice) {
        this.martialArtPrice = martialArtPrice;
    }

    public int getMartialArtId() {
        return martialArtId;
    }

    public void setMartialArtId(int martialArtId) {
        this.martialArtId = martialArtId;
    }

    @Override
    public String toString() {
        return martialArtName + " " + martialArtPrice + " " + martialArtColor;
    }
}
