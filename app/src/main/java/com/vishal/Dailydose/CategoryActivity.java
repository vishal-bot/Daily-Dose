package com.vishal.Dailydose;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.vishal.Dailydose.ModelClass.shop;

import java.util.Objects;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<shop, shopViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String nam = Objects.requireNonNull(getIntent().getExtras()).getString("naam");
        Objects.requireNonNull(getSupportActionBar()).setTitle(nam);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        recyclerView  = findViewById(R.id.recycler_shop); recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager); loadMenu(nam); adapter.startListening();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
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

    private void loadMenu(final String shop) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(shop);
        databaseReference.keepSynced(true);
        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child(shop);
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
                Glide.with(CategoryActivity.this).load(category.getImage()).into(viewHolder.shopImage);
                viewHolder.shopType.setText(category.getType());
                viewHolder.shopDistance.setText(category.getDistance());
                viewHolder.shopCharge.setText("Rs. " + String.valueOf(category.getCharge())+"/KM");
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
                        Intent itemlist = new Intent(CategoryActivity.this, itemlistActivity.class);
                        Utils.categoryData = category;
                        startActivity(itemlist); onBackPressed();
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
}