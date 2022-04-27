package com.vishal.Dailydose.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.vishal.Dailydose.CategoryActivity;
import com.vishal.Dailydose.Constant;
import com.vishal.Dailydose.itemlistActivity;
import com.vishal.Dailydose.ModelClass.shop;
import com.vishal.Dailydose.R;
import com.vishal.Dailydose.Utils;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private Context ctx;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<shop, shopViewHolder> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false); ctx = getContext();

        RecyclerView recycler  = view.findViewById(R.id.recycler_cate); recycler.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(ctx,LinearLayoutManager.HORIZONTAL,false);
        recycler.setLayoutManager(manager); CategoryAdapter categoryAdapter = new CategoryAdapter(ctx);
        recycler.setAdapter(categoryAdapter);

        recyclerView  = view.findViewById(R.id.recycler_shop); recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager); loadMenu("All"); adapter.startListening();
        return view;
    }

    public static class shopViewHolder extends RecyclerView.ViewHolder {
        public TextView shopName;
        public ImageView shopImage;
        public TextView shopType;
        public TextView shopDistance;
        public TextView shopCharge;
        public TextView shopOpen;
        public TextView shopClose;
        public shopViewHolder(View itemView) {
            super(itemView);
            shopName = itemView.findViewById(R.id.cat_name);
            shopImage = itemView.findViewById(R.id.cat_image);
            shopType = itemView.findViewById(R.id.cat_type);
            shopDistance = itemView.findViewById(R.id.cat_distance);
            shopCharge = itemView.findViewById(R.id.cat_charge);
            shopOpen = itemView.findViewById(R.id.cat_open);
            shopClose = itemView.findViewById(R.id.cat_close);
        }
    }

    private void loadMenu(final String type) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constant.shop);
        databaseReference.keepSynced(true);
        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child(Constant.shop);
        Query personsQuery = personsRef.orderByKey();
        final FirebaseRecyclerOptions<shop> personsOptions = new FirebaseRecyclerOptions.Builder<shop>().setQuery(personsQuery, shop.class).build();
        adapter = new FirebaseRecyclerAdapter<shop, shopViewHolder>(personsOptions) {
            @NonNull
            @Override
            public shopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_view, parent, false);
                return new shopViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull shopViewHolder viewHolder, int i, @NonNull final shop category) {
                viewHolder.shopName.setText(category.getName());
                Glide.with(ctx).load(category.getImage()).into(viewHolder.shopImage);
                viewHolder.shopType.setText(category.getType());
                viewHolder.shopDistance.setText(category.getDistance());
                viewHolder.shopCharge.setText("Rs. " +String.valueOf(category.getCharge())+"/KM");
                if (category.getStatus().toUpperCase().equals("OPEN")) {
                    viewHolder.shopClose.setVisibility(View.GONE);
                    viewHolder.shopOpen.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.shopOpen.setVisibility(View.GONE);
                    viewHolder.shopClose.setVisibility(View.VISIBLE);
                }
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent itemlist = new Intent(ctx, itemlistActivity.class);
                        Utils.categoryData = category;
                        ctx.startActivity(itemlist);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.UserViewHolder> {
        private List<String> titleList;
        private Context context;
        public CategoryAdapter(Context ctx) {
            this.context = ctx;
            this.titleList = new ArrayList<>();
            titleList.add("All");titleList.add("Restaurant");titleList.add("Pharmacy");titleList.add("Services");titleList.add("Bakery");
        }
        @NonNull
        @Override
        public CategoryAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.cate,parent,false);
            return new UserViewHolder(view);
        }
        @Override
        public void onBindViewHolder(CategoryAdapter.UserViewHolder holder, final int position) {
            holder.title.setText(titleList.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent list = new Intent(ctx, CategoryActivity.class);
                    list.putExtra("naam",titleList.get(position));
                    ctx.startActivity(list);
                }
            });
        }
        @Override
        public int getItemCount() {
            return titleList.size();
        }
        private class UserViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            public UserViewHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.catname);
            }
        }
    }
}
