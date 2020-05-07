package com.learncamel.domain;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.math.BigDecimal;

/**
 * Created by Dilip on 1/15/18.
 */
@CsvRecord( separator = ",",skipFirstLine=true)
public class Item {

    @DataField(pos = 1)
    private String transactionType;

    @DataField(pos = 2)
    private String sku;

    @DataField(pos = 3)
    private String itemDescription;

    @DataField(pos = 4,precision = 2)
    private BigDecimal price;

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "transactionType='" + transactionType + '\'' +
                ", sku='" + sku + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", price=" + price +
                '}';
    }
}


