package com.groceryguru.jaegar.groceryguru;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AddProduct extends AppCompatActivity {
    DBHelper database;
    TextView productName;
    TextView productPrice;
    TextView productNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        database = new DBHelper(this);
        productName = (TextView)findViewById(R.id.add_product_name);
        productPrice = (TextView)findViewById(R.id.add_product_price);
        productNotes = (TextView)findViewById(R.id.add_product_notes);
    }

    public void saveAndAnotherButton(View view) {
        //clear fields here.
        saveProduct();
    }

    public void saveButton(View view) {
        saveProduct();
        finish();
    }

    public void cancelButton(View view) {
        finish();
    }

    private void saveProduct() {
        String name = productName.getText().toString();
        double price = Double.parseDouble(productPrice.getText().toString());
        String notes = productNotes.getText().toString();
        database.insertProduct(name, price, notes);
    }
}
