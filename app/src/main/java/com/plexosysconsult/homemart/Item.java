package com.plexosysconsult.homemart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by senyer on 6/12/2016.
 */
public class Item {

    String imageUrl;
    String itemName;
    String itemPrice;
    int itemId;
    String itemShortDescription;
    Boolean hasVariations;
    List<Item> itemVariations;
    String optionUnit;



    public Item(){

        itemVariations = new ArrayList<>();


    }

    public String getOptionUnit() {
        return optionUnit;
    }

    public void setOptionUnit(String optionUnit) {
        this.optionUnit = optionUnit;
    }

    public Boolean getHasVariations() {
        return hasVariations;
    }

    public void setHasVariations(Boolean hasVariations) {
        this.hasVariations = hasVariations;
    }

    public String getItemShortDescription() {
        return itemShortDescription;
    }

    public void setItemShortDescription(String itemShortDescription) {
        this.itemShortDescription = itemShortDescription.trim();
    }

    public List<Item> getItemVariations() {
        return itemVariations;
    }

    public void setItemVariations(List<Item> itemVariations) {
        this.itemVariations = itemVariations;
    }



    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }




    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getItemName() {

        if(getItemShortDescription().contains("Kilogram")){

            return itemName + " (" + getItemShortDescription() +  ")";

        }


        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }
}
