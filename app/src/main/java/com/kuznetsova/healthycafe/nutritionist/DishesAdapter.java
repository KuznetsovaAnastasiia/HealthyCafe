package com.kuznetsova.healthycafe.nutritionist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kuznetsova.healthycafe.R;
import com.kuznetsova.healthycafe.entity.Dish;

import java.util.ArrayList;
import java.util.List;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.SimpleViewHolder> {

    private final Context mContext;
    private List<Dish> mDishes;
    private int nutritionistId;

    public void add(Dish dish, int position) {
        position = (position == -1) ? getItemCount()  : position;
        mDishes.add(position,dish);
        notifyItemInserted(position);
    }

    public void remove(int position){
        if (position < getItemCount()  ) {
            mDishes.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvName;
        public final TextView tvCost;
        public final LinearLayout layout;

        public SimpleViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.dish_list_name);
            tvCost = view.findViewById(R.id.dish_list_cost);
            layout = view.findViewById(R.id.layout_list_item);
        }
    }

    public DishesAdapter(Context context, List<Dish> dishes, int nutritionistId) {
        mContext = context;

        if (dishes != null) {
            mDishes = dishes;
        }
        else {
            mDishes = new ArrayList<>();
        }

        this.nutritionistId = nutritionistId;
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_dish, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        holder.tvName.setText(mDishes.get(position).getName());
        holder.tvCost.setText(Integer.toString(mDishes.get(position).getPrice()));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "Position = " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, DishInfoActivity.class);
                intent.putExtra("nutritionist_id", nutritionistId);
                intent.putExtra("dish_id", (int)mDishes.get(position).getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDishes.size();
    }
}