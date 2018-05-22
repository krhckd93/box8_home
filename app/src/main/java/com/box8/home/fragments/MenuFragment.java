package com.box8.home.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.box8.home.helpers.JSONHelper;
import com.box8.home.MainActivity;
import com.box8.home.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuFragment extends Fragment {

    String[] product_categories = {"Fusion Box", "Curries", "Biryani", "Wraps", "Ice Cream"};
    ViewPager viewPager;
    TabLayout tabLayout;
    View mView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(mView == null) {
            mView = inflater.inflate(R.layout.frag_products, null);
        }
        try {

            tabLayout = (TabLayout)mView.findViewById(R.id.tab_layout);
            viewPager = (ViewPager)mView.findViewById(R.id.viewpager);


            JSONObject product_data = JSONHelper.readFromJSON(getActivity());
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(((MainActivity)getActivity()).getSupportFragmentManager(), product_data);
            viewPager.setAdapter(viewPagerAdapter);
            viewPager.setOffscreenPageLimit(2);
            tabLayout.setupWithViewPager(viewPager);

            if(getArguments() != null) {
                if(getArguments().getString("category") != null) {
                    if(getCategoryIndex(getArguments().getString("category")) != -1)
                    viewPager.setCurrentItem(getCategoryIndex(getArguments().getString("category")));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mView;
    }

    private int getCategoryIndex(String category) {
        for(int i=0; i<product_categories.length; i++) {
            if(product_categories[i].equals(category)) {
                return i;
            }
        }
        return -1;
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        JSONObject data;

        ViewPagerAdapter(android.support.v4.app.FragmentManager fm, JSONObject data) {
            super(fm);
            this.data = data;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            JSONArray jsonArray = null;
            try {
                jsonArray = data.getJSONArray(product_categories[position]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(jsonArray == null) {
                // Handle empty data
            }
            ProductsListFragment productsListFragment = new ProductsListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("products", jsonArray.toString());
            productsListFragment.setArguments(bundle);
            return productsListFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return product_categories[position];
        }

        @Override
        public int getCount() {
            return product_categories.length;
        }
    }
}
