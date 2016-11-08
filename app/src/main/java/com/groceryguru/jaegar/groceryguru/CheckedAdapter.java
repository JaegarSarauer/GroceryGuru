package com.groceryguru.jaegar.groceryguru;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.groceryguru.jaegar.groceryguru.Product;
import com.groceryguru.jaegar.groceryguru.R;

import java.util.Arrays;
import java.util.List;

public class CheckedAdapter extends ArrayAdapter<Product> {

    private final List<Product> list;
    private final Activity context;
    boolean checkAll_flag = false;
    boolean checkItem_flag = false;

    public CheckedAdapter(Activity context, Product ... list) {
        super(context, R.layout.checkbox_list_item, list);
        this.context = context;
        this.list = Arrays.asList(list);
    }

    static class ViewHolder {
        protected TextView text;
        protected CheckBox checkbox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.checkbox_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.productNameItem);
            viewHolder.text.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductView.class);
                    Log.d("WHAT POSITION ARE WE", v.getTag() + "");
                    Product p = list.get((Integer) v.getTag());
                    if (p != null) {
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("Product", p);
                        intent.putExtras(mBundle);
                    }
                    context.startActivity(intent);
                }

                });
            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checkBoxItem);
            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();
                    list.get(getPosition).selected = (buttonView.isChecked());
                }
            });
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.productNameItem, viewHolder.text);
            convertView.setTag(R.id.checkBoxItem, viewHolder.checkbox);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.checkbox.setTag(position); // This line is important.
        viewHolder.text.setTag(position); // This line is important.
        viewHolder.text.setText(list.get(position).name);
        viewHolder.checkbox.setChecked(list.get(position).selected);

        return convertView;
    }

    public boolean isChecked(int pos) {
        if (pos >= 0 && pos < list.size())
            return list.get(pos).selected;
        return false;
    }

}