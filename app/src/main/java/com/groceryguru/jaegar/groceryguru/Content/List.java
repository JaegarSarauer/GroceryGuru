package com.groceryguru.jaegar.groceryguru.Content;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jaegar on 2016-10-23.
 */

public class List implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;
    public String name;
    public int ID = -1;
    public java.util.List<Product> products;

    public List(String name, Product ... p) {
        this.name = name;
        products = new ArrayList<Product>();
        for (int i = 0; i < p.length; i++)
            products.add(p[i]);
    }

    @Override
    public String toString() {
        return "Name: " + name;
    }
}
