package com.groceryguru.jaegar.groceryguru.WebQuery;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.groceryguru.jaegar.groceryguru.ContentHandlers.AddProduct;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Jaegar on 2016-11-28.
 */

public class ProductLookup {
    public static ProductLookup instance = new ProductLookup();
    private final String BASE_URL = "https://www.upcdatabase.com/item/";
    private final String END_URL = "";

    private AsyncHttpClient client = new AsyncHttpClient();

    private void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getProductURL(url), params, responseHandler);
    }

    private String getProductURL(String UPCCode) {
        return BASE_URL + UPCCode + END_URL;
    }

    public void getProductName(String UPCCode, final AppCompatActivity activity) {
        ProductLookup.instance.post(UPCCode, null, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                try {
                    String[] lines = response.split("\n");
                    for (String s : lines) {
                        if (s.contains("Description")) {
                            AddProduct.productName.setText(s.replaceAll("(Description)|(<([a-z]+) *[^/]*?>)|(<([/a-z]+) *[^/]*?>)", ""));
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Product not Found");
                    Toast.makeText(activity, "Error retrieving name", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] result, String str, Throwable throwable) {
                System.out.println(str);
                Toast.makeText(activity, "Error retrieving name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}