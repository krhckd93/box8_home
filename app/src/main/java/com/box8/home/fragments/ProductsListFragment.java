package com.box8.home.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.box8.home.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ProductsListFragment extends Fragment {

    View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        JSONArray product_data = null;
        if(getArguments() != null) {
            if(getArguments().get("products") != null) {

                try {
                    product_data = new JSONArray(getArguments().getString("products"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        View mView = inflater.inflate(R.layout.frag_menu, null);
        ListView listView = (ListView)mView.findViewById(R.id.listview);
        ListViewAdapter listViewAdapter = new ListViewAdapter(getContext(), product_data);
        listView.setAdapter(listViewAdapter);
        listView.setDivider(null);
        return mView;
    }

    List<String> jsonToList(JSONArray array) {
        ArrayList<String> listdata = new ArrayList<String>();
        try {
            if (array != null) {
                for (int i = 0; i < array.length(); i++) {
                    listdata.add(array.getString(i));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listdata;
    }

    class ListViewAdapter extends ArrayAdapter<String> {

        List<String> data;
        LayoutInflater inflater;
        private int last_position = -1;
        public ListViewAdapter(@NonNull Context context, JSONArray data) {
            super(context, R.layout.category_list_item, jsonToList(data));
            try {
                this.data = jsonToList(data);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            this.data = jsonToList(data);
        }

        private class ViewHolder {
            TextView txtName;
            TextView txtType;
            TextView txtVersion;
            ImageView imgProd;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            String product_name = getItem(position);
            String array_item = "I Didn't Work!";
            try {
                array_item  =  (String)data.get(position);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ViewHolder viewHolder;

            if(convertView == null) {
                viewHolder =new ViewHolder();

                if(inflater == null ){
                    inflater = LayoutInflater.from(getContext());
                }
                convertView = inflater.inflate(R.layout.product_list_item, null);
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.product_name);
                viewHolder.imgProd = (ImageView) convertView.findViewById(R.id.product_image);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            last_position = position;
            viewHolder.txtName.setText(array_item);
            Picasso.with(getContext()).load("http://via.placeholder.com/450x300").resize(240, 120).into(viewHolder.imgProd);

            viewHolder.imgProd.setTag(position);

            return convertView;
        }

        @Override
        public int getCount() {
            return data.size();
        }
    }
}