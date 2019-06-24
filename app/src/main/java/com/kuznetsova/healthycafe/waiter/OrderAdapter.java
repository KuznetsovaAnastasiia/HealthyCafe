package com.kuznetsova.healthycafe.waiter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kuznetsova.healthycafe.R;
import com.kuznetsova.healthycafe.entity.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.SimpleViewHolder> {
    private final Context mContext;
    private List<Order> orders;
    private SimpleDateFormat sdf;
    final private OrderAdapter.ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public void add(Order order, int position) {
        position = (position == -1) ? getItemCount()  : position;
        orders.add(position, order);
        notifyItemInserted(position);
    }

    public void remove(int position){
        if (position < getItemCount()  ) {
            orders.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvNumber;
        public final TextView tvTable;
        public final TextView tvSum;
        public final TextView tvImplemTime;
        public final TextView tvTime;
        public final TextView tvDishes;
        public final LinearLayout layout;
        public final ImageButton btnReferred;

        public SimpleViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.layout_order_item);
            tvNumber = view.findViewById(R.id.tv_order_number);
            tvTable = view.findViewById(R.id.tv_order_table);
            tvSum = view.findViewById(R.id.tv_order_sum);
            tvImplemTime = view.findViewById(R.id.tv_order_time_implement);
            tvTime = view.findViewById(R.id.tv_order_time);
            tvDishes = view.findViewById(R.id.tv_order_dishes);
            btnReferred = view.findViewById(R.id.btn_order_referred);
        }
    }

    public OrderAdapter(Context context, List<Order> order, ListItemClickListener listener) {
        mContext = context;
        mOnClickListener = listener;
        if (order != null) {
            orders = order;
        }
        else {
            orders = new ArrayList<>();
        }
        sdf = new SimpleDateFormat( "HH:mm" );
    }

    public OrderAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_order_full, parent, false);
        return new OrderAdapter.SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        holder.tvNumber.setText(Long.toString(orders.get(position).getId()));
        holder.tvTable.setText(Integer.toString(orders.get(position).getTableNumber()));
        holder.tvSum.setText(Integer.toString(orders.get(position).getPrice()));
        holder.tvImplemTime.setText(orders.get(position).getLeadTime() + " мин.");

        String time = sdf.format(orders.get(position).getOrderTime());
        holder.tvTime.setText(time);

        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<orders.get(position).getDishes().size(); i++)
            stringBuilder.append(orders.get(position).getDishes().get(i).getName() + " x " + orders.get(position).getNumbers().get(i) + "\n");
        holder.tvDishes.setText(stringBuilder.toString());

        holder.btnReferred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orders.get(position).checkReadiness()) {
                    mOnClickListener.onListItemClick(position);
                    remove(position);
                }
            }
        });

        if(orders.get(position).isReady()) {
            holder.layout.setBackgroundColor(Color.parseColor("#ff99cc00"));
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
