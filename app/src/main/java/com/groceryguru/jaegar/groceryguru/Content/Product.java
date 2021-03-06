package com.groceryguru.jaegar.groceryguru.Content;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Jaegar on 2016-10-23.
 */

public class Product implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;
    public String name;
    public int price;
    public String notes;
    public String upc;
    public boolean selected;

    public Product(String name, int price, String note, String upc) {
        this.name = name;
        this.price = price;
        this.notes = note;
        this.upc = upc;
    }

    public Product() {
        name = "";
        price = 0;
        notes = "";
        upc = "";
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nPrice: $" + price + "\nNotes: " + notes + "\nUPC Code: " + upc;
    }
}
