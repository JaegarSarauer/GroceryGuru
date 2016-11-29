package com.groceryguru.jaegar.groceryguru;

import android.icu.text.NumberFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class ProductView extends AppCompatActivity {

    Product product;

    TextView name;
    TextView price;
    TextView notes;
    TextView upc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        product = (Product)getIntent().getSerializableExtra("Product");
        name = (TextView) findViewById(R.id.product_view_name);
        price = (TextView) findViewById(R.id.product_view_price);
        notes = (TextView) findViewById(R.id.product_view_notes);
        upc = (TextView) findViewById(R.id.product_view_upc);

        if (product != null) {
            name.setText("Name: " + product.name);
            price.setText("$" + Double.toString(product.price));
            notes.setText("Notes: " + product.notes);
            upc.setText("UPC Code: " + product.upc);
        }

    }
}
