package com.groceryguru.jaegar.groceryguru;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProductView extends AppCompatActivity {

    Product product;

    TextView name;
    TextView price;
    TextView notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        product = (Product)getIntent().getSerializableExtra("Product");
        name = (TextView) findViewById(R.id.product_view_name);
        price = (TextView) findViewById(R.id.product_view_price);
        notes = (TextView) findViewById(R.id.product_view_notes);

        if (product != null) {
            name.setText(product.name);
            price.setText(Integer.toString(product.price));
            notes.setText(product.notes);
        }

    }
}
