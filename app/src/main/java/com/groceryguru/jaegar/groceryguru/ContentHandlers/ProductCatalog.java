package com.groceryguru.jaegar.groceryguru.ContentHandlers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.groceryguru.jaegar.groceryguru.Content.Product;
import com.groceryguru.jaegar.groceryguru.R;
import com.groceryguru.jaegar.groceryguru.UI.ProductView;

public class ProductCatalog extends AppCompatActivity {
    DBHelper database;
    ListView productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_catalog);

        database = new DBHelper(this);

        /*String[] columns = {"Name", "Price", "Notes"};
        int[] ids = {R.id.listName, R.id.listPrice, R.id.listNotes};

        Product[] p = database.getAllProducts();

        productList = (ListView) findViewById(R.id.productList);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.product);
        adapter.addAll(p);
        productList.setAdapter(adapter);*/

        productList = (ListView) findViewById(R.id.productList);
        String[] p = database.getAllProductsString();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, p);

        productList.setAdapter(adapter);



        // Create a message handling object as an anonymous class.
        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(ProductCatalog.this, ProductView.class);
                Product p = database.getProductByIDProduct((int)id);
                if (p != null) {
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("Product", p);
                    intent.putExtras(mBundle);
                }
                startActivity(intent);
            }
        };

        productList.setOnItemClickListener(mMessageClickedHandler);

    }

}

