package com.groceryguru.jaegar.groceryguru;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ProductCatalogAddList extends AppCompatActivity {
    ListView productsList;
    DBHelper database;
    List list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_catalog_add_list);
        productsList = (ListView) findViewById(R.id.moreProductsToList);
        database = new DBHelper(this);
        list = (List)getIntent().getSerializableExtra("List");

        setupProductsList();
    }

    public void newListCreate(View view) {
        saveList();
        finish();
    }

    private void saveList() {
        database.removeAllProductsInList(list.ID);
        for (int i = 0; i < productsList.getAdapter().getCount(); i++) {
            if (((CheckedAdapter)productsList.getAdapter()).isChecked(i))
                database.insertProductIntoList(list.ID, i);
        }
    }

    private void setupProductsList() {
        productsList.invalidate();
        Product[] p = database.getAllProductsWithListChecks(list.ID);

        //see if checked here

        CheckedAdapter adapter = new CheckedAdapter(this, p);

        productsList.setAdapter(adapter);


    }
}
