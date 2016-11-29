package com.groceryguru.jaegar.groceryguru.ContentHandlers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.groceryguru.jaegar.groceryguru.Content.CheckedAdapter;
import com.groceryguru.jaegar.groceryguru.Content.Product;
import com.groceryguru.jaegar.groceryguru.R;

public class AddList extends AppCompatActivity {
    DBHelper database;
    TextView listName;
    ListView productsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);
        productsList = (ListView) findViewById(R.id.productsToList);
        database = new DBHelper(this);

        setupProductsList();
    }

    public void newListCreate(View view) {
        listName = (TextView)findViewById(R.id.newListName);
        saveList();
        finish();
    }

    private void saveList() {
        String name = listName.getText().toString();
        if (name != null && name != "" && name.trim().length() > 0) {
            long rowID = database.insertList(name);
            if (rowID == -1)
                return;
            for (int i = 0; i < productsList.getAdapter().getCount(); i++) {
                if (((CheckedAdapter)productsList.getAdapter()).isChecked(i))
                database.insertProductIntoList((int)rowID, i);
                //Toast.makeText(this, ((CheckedAdapter)productsList.getAdapter()).isChecked(i) + "", Toast.LENGTH_LONG).show();
            }
            setResult(1);
        } else {
            setResult(0);
        }
    }

    private void setupProductsList() {
        productsList.invalidate();
        Product[] p = database.getAllProducts();

        CheckedAdapter adapter = new CheckedAdapter(this, p);

        productsList.setAdapter(adapter);


    }




}
