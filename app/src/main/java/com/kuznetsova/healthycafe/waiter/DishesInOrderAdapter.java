package com.kuznetsova.healthycafe.waiter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kuznetsova.healthycafe.R;
import com.kuznetsova.healthycafe.entity.Dish;

import java.util.ArrayList;
import java.util.List;

public class DishesInOrderAdapter extends RecyclerView.Adapter<DishesInOrderAdapter.SimpleViewHolder> {

    private final Context mContext;
    private List<Dish> mDishes;
    private List<Integer> mNumbers;
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public void add(Dish dish, int position) {
        position = (position == -1) ? getItemCount()  : position;
        mDishes.add(position, dish);
        mNumbers.add(position,1);
        notifyItemInserted(position);
    }

    public void remove(int position){
        if (position < getItemCount()  ) {
            mDishes.remove(position);
            mNumbers.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvName;
        public final TextView tvNumb;
        public final Button btnAdd;
        public final Button btnRemove;

        public SimpleViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_dish_name_editable);
            tvNumb = view.findViewById(R.id.tv_dish_numb_editable);
            btnRemove = view.findViewById(R.id.btn_minus_dish);
            btnAdd = view.findViewById(R.id.btn_plus_dish);
        }
    }

    public DishesInOrderAdapter(Context context, List<Dish> dishes, List<Integer> numbers, ListItemClickListener listener) {
        mContext = context;
        mOnClickListener = listener;
        if (dishes != null) {
            mDishes = dishes;
            mNumbers = numbers;
        }
        else {
            mDishes = new ArrayList<>();
            mNumbers = new ArrayList<>();
        }
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_dish_editable, parent, false);
        return new SimpleViewHolder(view);
    }

    public int calcPrice(){
        int price = 0;
        for(int i=0; i<mDishes.size(); i++)
            price += (mNumbers.get(i) * mDishes.get(i).getPrice());
        return price;
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        holder.tvName.setText(mDishes.get(position).getName());
        holder.tvNumb.setText(Integer.toString(mNumbers.get(position)));
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curr = mNumbers.get(position);
                if(curr > 1) {
                    mNumbers.set(position, curr - 1);
                    notifyDataSetChanged();
                }
                else
                    remove(position);
                mOnClickListener.onListItemClick(position);
            }
        });
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumbers.set(position, mNumbers.get(position) + 1);
                notifyDataSetChanged();
                mOnClickListener.onListItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDishes.size();
    }
}