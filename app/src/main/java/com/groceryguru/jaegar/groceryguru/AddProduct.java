package com.groceryguru.jaegar.groceryguru;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AddProduct extends AppCompatActivity {
    DBHelper database;
    public static TextView productName;
    TextView productPrice;
    TextView productNotes;
    TextView productUPC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        database = new DBHelper(this);
        productName = (TextView)findViewById(R.id.add_product_name);
        productPrice = (TextView)findViewById(R.id.add_product_price);
        productNotes = (TextView)findViewById(R.id.add_product_notes);
        productUPC = (TextView)findViewById(R.id.add_product_UPC);
    }

    public void scanProduct(View view) {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "ALL_CODE_TYPES");
        startActivityForResult(intent, 0);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                productUPC.setText(contents);
                ProductLookup.instance.getProductName(contents, this);
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }

    public void saveAndAnotherButton(View view) {
        saveProduct();
        productName.setText("");
        productPrice.setText("");
        productNotes.setText("");
        productUPC.setText("");
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
        String UPC = productUPC.getText().toString();
        database.insertProduct(name, price, notes, UPC);
    }
}
