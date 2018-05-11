package com.plexosysconsult.homemart;

import android.content.Context;

/**
 * Created by senyer on 6/13/2016.
 */
public class CartItem {

    String itemName;
    int itemId;
    int itemVariationId;
    boolean isVariation = false;
    String itemUnitPrice;
    String quantity;
    Long total;
    BigDecimalClass bigDecimalClass;

    public CartItem(Context context){

        bigDecimalClass = new BigDecimalClass(context);

    }

    public boolean isVariation() {
        return isVariation;
    }

    public void setIsVariation(boolean variation) {
        isVariation = variation;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemVariationId() {
        return itemVariationId;
    }

    public void setItemVariationId(int itemVariationId) {
        this.itemVariationId = itemVariationId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemUnitPrice() {
        return itemUnitPrice;
    }

    public void setItemUnitPrice(String itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    private void calculateTotal(){

        total = bigDecimalClass.multiplyParameters(itemUnitPrice, quantity);
    }

    public String getItemTotalForShow() {

        String totalForShow;

        calculateTotal();

        totalForShow = bigDecimalClass.convertLongToDisplayCurrencyString(total);


        return totalForShow;
    }

    public Long getItemTotalForCalculation(){

        calculateTotal();

        return total;
    }


}
