package com.kuznetsova.healthycafe.waiter;

import android.content.Context;
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

class DishesListAdapter extends RecyclerView.Adapter<DishesListAdapter.SimpleViewHolder> {

    private final Context mContext;
    private List<Dish> mDishes;
    final private ListItemClickListener mOnClickListener;

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

    public class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView tvName;
        public final TextView tvCost;
        public final LinearLayout layout;

        public SimpleViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.dish_list_name);
            tvCost = view.findViewById(R.id.dish_list_cost);
            layout = view.findViewById(R.id.layout_list_item);
            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public DishesListAdapter(Context context, List<Dish> dishes, ListItemClickListener listener) {
        mContext = context;
        mOnClickListener = listener;

        if (dishes != null) {
            mDishes = dishes;
        }
        else {
            mDishes = new ArrayList<>();
        }
    }

    public DishesListAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_dish, parent, false);
        return new DishesListAdapter.SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DishesListAdapter.SimpleViewHolder holder, final int position) {
        holder.tvName.setText(mDishes.get(position).getName());
        holder.tvCost.setText(Integer.toString(mDishes.get(position).getPrice()));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickListener.onListItemClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDishes.size();
    }
}
