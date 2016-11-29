package com.groceryguru.jaegar.groceryguru.ContentHandlers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.groceryguru.jaegar.groceryguru.Content.List;
import com.groceryguru.jaegar.groceryguru.R;
import com.groceryguru.jaegar.groceryguru.UI.ListsView;

import java.util.Arrays;

public class ListsCatalog extends AppCompatActivity {
    DBHelper database;
    ListView listsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists_catalog);
        database = new DBHelper(this);
        listsList = (ListView) findViewById(R.id.listsList);

        setupListsListView();

        // Create a message handling object as an anonymous class.
        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(ListsCatalog.this, ListsView.class);
                List l = database.getListByIDList((int)id);
                l.products = Arrays.asList(database.getAllProductsInList((int)id));
                l.ID = (int)id;
                if (l != null) {
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("List", l);
                    intent.putExtras(mBundle);
                }
                startActivity(intent);
            }
        };

        listsList.setOnItemClickListener(mMessageClickedHandler);
    }

    public void newListClick(View view) {
        Intent intent = new Intent(this, AddList.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 1) { //new list created, refresh
            setupListsListView();
        }
    }

    private void setupListsListView() {
        listsList.invalidate();
        String[] p = database.getAllListsString();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, p);

        listsList.setAdapter(adapter);
    }
}
