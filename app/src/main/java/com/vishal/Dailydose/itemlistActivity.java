package com.vishal.Dailydose;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.vishal.Dailydose.ModelClass.Cart;
import com.vishal.Dailydose.ModelClass.item;

import java.util.Objects;

public class itemlistActivity extends AppCompatActivity {

    private int finalcount = 0;
    private int finalprice = 0;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<item,itemViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); Utils.showCart = false;
        Objects.requireNonNull(getSupportActionBar()).setTitle(Utils.categoryData.getName());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setSubtitle(Utils.categoryData.getDescription());
        TextView itemprice = findViewById(R.id.itemprice);
        itemprice.setText("Rs. " + String.valueOf(Utils.categoryData.getDefaultPrice()) + "/-");
        Button mi = findViewById(R.id.minu); Button ad = findViewById(R.id.ad);
        final EditText cnt = findViewById(R.id.cartcoun); final int[] coun = {0}; final int[] pric = {0};
        cnt.setText("0");
        mi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.categoryData.getStatus().toUpperCase().equals("OPEN")) {
                    if (coun[0] > 0) {
                        coun[0] = coun[0] - 1;
                        pric[0] = coun[0] * Utils.categoryData.getDefaultPrice();
                        finalcount = finalcount - 1; finalprice = finalprice - Utils.categoryData.getDefaultPrice();
                        cnt.setText(String.valueOf(coun[0])); calculate();
                        Cart cart = new Cart("Special Thali","https://i.ibb.co/zHhRBSz/410px-Thali-svg.png",Utils.categoryData.getDefaultPrice(),coun[0],Utils.categoryData.getName());
                        CartList.addToCart(itemlistActivity.this,"Special Thali",cart);
                    }
                } else {Utils.showToast(itemlistActivity.this,"Not Open");}
            }
        });
        ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.categoryData.getStatus().toUpperCase().equals("OPEN")) {
                    coun[0] = coun[0] + 1;
                    pric[0] = coun[0] * Utils.categoryData.getDefaultPrice();
                    finalcount = finalcount + 1; finalprice = finalprice + Utils.categoryData.getDefaultPrice();
                    cnt.setText(String.valueOf(coun[0])); calculate();
                    Cart cart = new Cart("Special Thali","https://i.ibb.co/zHhRBSz/410px-Thali-svg.png",Utils.categoryData.getDefaultPrice(),coun[0],Utils.categoryData.getName());
                    CartList.addToCart(itemlistActivity.this,"Special Thali",cart);
                } else {Utils.showToast(itemlistActivity.this,"Not Open");}
            }
        });
        recyclerView = findViewById(R.id.recycler_item);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        loadListitem(Utils.categoryData.getName());
        adapter.startListening();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public static class itemViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public ImageView itemImage;
        public TextView itemPrice;
        public TextView itemQuantity;
        public Button Minus,Plus;
        public EditText itemCount;
        public itemViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemImage = itemView.findViewById(R.id.item_image);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            Minus = itemView.findViewById(R.id.minus);
            Plus = itemView.findViewById(R.id.add);
            itemCount = itemView.findViewById(R.id.cartcount);
        }
    }

    private void loadListitem(String shop) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(shop);
        databaseReference.keepSynced(true);
        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child(shop);
        Query personsQuery = personsRef.orderByKey();
        FirebaseRecyclerOptions<item> personsOptions = new FirebaseRecyclerOptions.Builder<item>().setQuery(personsQuery,item.class).build();
        adapter = new FirebaseRecyclerAdapter<item, itemViewHolder>(personsOptions) {
            @NonNull
            @Override
            public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
                return new itemViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final itemViewHolder viewHolder, final int i, @NonNull final item model) {
                viewHolder.itemName.setText(model.getName());
                Glide.with(getBaseContext()).load(model.getImage()).into(viewHolder.itemImage);
                viewHolder.itemPrice.setText("Rs. " + String.valueOf(model.getPrice()) + "/-");
                viewHolder.itemQuantity.setText(model.getQuantity());
                final int[] count = {0}; final int[] price = {0};
                viewHolder.itemCount.setText("0");
                viewHolder.Minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Utils.categoryData.getStatus().toUpperCase().equals("OPEN")) {
                            if (count[0] > 0) {
                                count[0] = count[0] - 1;
                                price[0] = (int) (count[0] * model.getPrice());
                                finalcount = finalcount - 1; finalprice = (int) (finalprice - model.getPrice());
                                viewHolder.itemCount.setText(String.valueOf(count[0])); calculate();
                                Cart cart = new Cart(model.getName(),model.getImage(),model.getPrice(),count[0],Utils.categoryData.getName());
                                CartList.addToCart(itemlistActivity.this,model.getName(),cart);
                            }
                        } else {Utils.showToast(itemlistActivity.this,"Not Open");}
                    }
                });
                viewHolder.Plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Utils.categoryData.getStatus().toUpperCase().equals("OPEN")) {
                            count[0] = count[0] + 1;
                            price[0] = (int) (count[0] * model.getPrice());
                            finalcount = finalcount + 1; finalprice = (int) (finalprice + model.getPrice());
                            viewHolder.itemCount.setText(String.valueOf(count[0])); calculate();
                            Cart cart = new Cart(model.getName(),model.getImage(),model.getPrice(),count[0],Utils.categoryData.getName());
                            CartList.addToCart(itemlistActivity.this,model.getName(),cart);
                        } else {Utils.showToast(itemlistActivity.this,"Not Open");}
                    }
                });
            }

        };
        recyclerView.setAdapter(adapter);
    }

    private void calculate() {
        LinearLayout cart = findViewById(R.id.cart);
        TextView item = findViewById(R.id.item);
        TextView amount = findViewById(R.id.amount);
        Button goCart = findViewById(R.id.checkout);
        goCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showCart = true;
                itemlistActivity.super.onBackPressed();
            }
        });
        if(finalcount>0 && finalprice>0){
            item.setText(String.valueOf(finalcount) + " Item");
            amount.setText("Amount Rs. " + String.valueOf(finalprice));
            cart.setVisibility(View.VISIBLE);
        } else {
            cart.setVisibility(View.GONE);
        }
    }

}