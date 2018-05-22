package com.box8.home.helpers;

import android.content.Context;
/*
import com.box8.home.models.Product;
import com.box8.home.models.ProductCategory;*/

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class JSONHelper {

    public static JSONObject readFromJSON(Context context) throws Exception{
        try {
            InputStream inputStream = context.getAssets().open("product_data.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            return new JSONObject(json);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
/*
    // Fetch all the categories
    public static ArrayList<ProductCategory> getProductCategories(Context context) throws Exception {
        ArrayList<ProductCategory> categories = new ArrayList<>();
        JSONObject data = readFromJSON(context);
        JSONObject jsonObject = JSONHelper.readFromJSON(context);
        Iterator<String> itr = jsonObject.keys();
        ProductCategory productCategory;
        while (itr.hasNext()) {
            String key = itr.next();
            productCategory = new ProductCategory();
            productCategory.setName(key);
            categories.add(productCategory);
        }
        return categories;
    }

    // Fetch all the products for a given category
    public static ArrayList<Product> getProducts(Context context, String category) throws Exception {
        ArrayList<Product> products = new ArrayList<>();
        JSONObject data = readFromJSON(context), temp;
        Iterator<String> itr = data.keys();
        Product product;
        JSONArray jsonArray;
        while (itr.hasNext()) {
            String key = itr.next();
            if(key.equals(category)) {
                jsonArray = data.getJSONArray(key);

                for(int i=0; i<jsonArray.length(); i++) {
                    temp = jsonArray.getJSONObject(i);
                    product = new Product();
                    product.setName(temp.getString("name"));
                    product.setPrice(temp.getString("price"));
                    product.setDescription(temp.getString("description"));
                    products.add(product);
                }
                break;
            }
        }
        return products;
    }*/
}