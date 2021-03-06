package com.groceryguru.jaegar.groceryguru.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.groceryguru.jaegar.groceryguru.Content.CheckedAdapter;
import com.groceryguru.jaegar.groceryguru.ContentHandlers.DBHelper;
import com.groceryguru.jaegar.groceryguru.Content.List;
import com.groceryguru.jaegar.groceryguru.Content.Product;
import com.groceryguru.jaegar.groceryguru.ContentHandlers.ProductCatalogAddList;
import com.groceryguru.jaegar.groceryguru.R;

public class ListsView extends AppCompatActivity {
    DBHelper database;
    TextView listName;
    ListView listProducts;
    List list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists_view);
        database = new DBHelper(this);
        list = (List)getIntent().getSerializableExtra("List");
        listName = (TextView)findViewById(R.id.listName);
        listName.setText(list.name);

        listProducts = (ListView) findViewById(R.id.listProducts);
        setupListsListView();
    }

    public void listsAddProducts(View view) {
        Intent intent = new Intent(this, ProductCatalogAddList.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("List", list);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    private void setupListsListView() {
        listProducts.invalidate();

        CheckedAdapter adapter = new CheckedAdapter(this, list.products.toArray(new Product[list.products.size()]));

        listProducts.setAdapter(adapter);
    }

    public void onStop () {
        for (int i = 0; i < listProducts.getAdapter().getCount(); i++) {
            database.updateProductCheckInList(list.ID, i, ((CheckedAdapter)listProducts.getAdapter()).isChecked(i));
        }
        super.onStop();
    }

}
