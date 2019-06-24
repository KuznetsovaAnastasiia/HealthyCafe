package com.kuznetsova.healthycafe.chef;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kuznetsova.healthycafe.R;
import com.kuznetsova.healthycafe.entity.Chef;
import com.kuznetsova.healthycafe.entity.Dish;
import com.kuznetsova.healthycafe.entity.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.SimpleViewHolder> {
    private final Context mContext;
    private final Chef chef;
    private List<Order> orders;
    final private OrderAdapter.ListItemClickListener mOnClickListener;

    //Handle in Activity
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    //Add order to adapter
    public void add(Order order, int position) {
        position = (position == -1) ? getItemCount()  : position;
        orders.add(position, order);
        notifyItemInserted(position);
    }

    //Remove order
    public void remove(int position){
        if (position < getItemCount()  ) {
            orders.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        //Views in holder
        public final TextView tvName;
        public final ImageButton btnReady;
        public final LinearLayout layout;

        public SimpleViewHolder(View view) {
            super(view);
            //Get reference to views
            tvName = view.findViewById(R.id.tv_order_dish);
            btnReady = view.findViewById(R.id.btn_order_dish_ready);
            layout = view.findViewById(R.id.layout_order_dish);
        }
    }

    public OrderAdapter(Context context, Chef chef, List<Order> order, ListItemClickListener listener) {
        mContext = context;
        this.chef = chef;
        mOnClickListener = listener;
        if (order != null) {
            orders = order;
        }
        else {
            orders = new ArrayList<>();
        }
    }

    public OrderAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_order_dish, parent, false);
        return new OrderAdapter.SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        //Set text for order item
        Order order = orders.get(position);
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<order.getDishes().size(); i++) {
            sb.append(order.getDishes().get(i).getName() + " x " + order.getNumbers().get(i) + "\n");
        }
        holder.tvName.setText(sb);

        holder.btnReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Order referred
                mOnClickListener.onListItemClick(position);
                remove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
