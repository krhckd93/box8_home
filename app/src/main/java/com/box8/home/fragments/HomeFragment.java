package com.box8.home.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.box8.home.helpers.JSONHelper;
import com.box8.home.R;
import com.box8.home.interfaces.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class HomeFragment extends Fragment {

    private ArrayList<String> categories;
    private int[] sample_images = {R.drawable.amazon_banner_web, R.drawable.banne_web, R.drawable.free_delivery_banner, R.drawable.happ_hour_banner, R.drawable.paytm_web_banner, R.drawable.phonepe_web_banner, R.drawable.web_banner_finnal};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, null);
        CarouselView carouselView = (CarouselView)view.findViewById(R.id.carousel_view);
        carouselView.requestFocus();
        carouselView.setPageCount(sample_images.length);

        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sample_images[position]);
            }
        });
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);
        try {
            categories = getCategories();
            RecyclerViewClickListener listener = new RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {
                    MenuFragment menuFragment = new MenuFragment();
                    Bundle bundle = new Bundle();
                    try {
                        bundle.putString("category", categories.get(position));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    menuFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.frag_container, menuFragment).addToBackStack("menuFragment").commit();
//                    Toast.makeText(getActivity(), "Listener called on " + categories.get(position), Toast.LENGTH_SHORT).show();
                }
            };
            RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter( categories, getActivity(), listener);
            recyclerView.setAdapter(recyclerViewAdapter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return view;


    }


    public ArrayList<String> getCategories() throws Exception {
        JSONObject jsonObject = JSONHelper.readFromJSON(getActivity());
        Iterator<String> itr = jsonObject.keys();
        ArrayList<String> categories = new ArrayList<>();
        while (itr.hasNext()) {
            String key = itr.next();
            categories.add(key);
        }
        return categories;
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
        private ArrayList<String> categories;
        private Context context;
        private RecyclerViewClickListener mListener;

        public RecyclerViewAdapter(ArrayList<String> categories, Context context, RecyclerViewClickListener listener) {
            this.categories = categories;
            this.context = context;
            mListener = listener;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.cat_name.setText(categories.get(position));
            Picasso.with(context).load("http://via.placeholder.com/200x200").resize(240, 120).into(holder.cat_img);

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
            return new ViewHolder(view, mListener);
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView cat_name;
            private ImageView cat_img;
            private RecyclerViewClickListener mListener;

            public ViewHolder(View itemView, RecyclerViewClickListener listener) {
                super(itemView);
                this.cat_name = (TextView) itemView.findViewById(R.id.product_name);
                this.cat_img = (ImageView) itemView.findViewById(R.id.product_image);
                mListener = listener;
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                this.mListener.onClick(v, getAdapterPosition());
            }
        }
    }


}
