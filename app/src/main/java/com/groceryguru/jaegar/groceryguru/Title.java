package com.groceryguru.jaegar.groceryguru;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Title extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }

    public void productsButtonClick(View view) {
        Intent intent = new Intent(this, ProductCatalog.class);
        startActivity(intent);
    }

    public void addProductButtonClick(View view) {
        Intent intent = new Intent(this, AddProduct.class);
        startActivity(intent);
    }

    public void listsButtonClick(View view) {
        Intent intent = new Intent(this, ListsCatalog.class);
        startActivity(intent);
    }


    public static void toast(Context c, String t) {
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(c, t, duration);
        toast.show();
    }
}
