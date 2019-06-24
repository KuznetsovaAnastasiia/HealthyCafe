package com.kuznetsova.healthycafe.nutritionist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kuznetsova.healthycafe.R;
import com.kuznetsova.healthycafe.entity.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientsEditAdapter extends RecyclerView.Adapter<IngredientsEditAdapter.SimpleViewHolder> {

    private final Context mContext;
    private final int mDishId;
    private final boolean editable;
    private List<Ingredient> mIngredients;
    final private IngredientsEditAdapter.ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public void add(Ingredient s, int position) {
        position = (position == -1) ? getItemCount()  : position;
        mIngredients.add(position,s);
        notifyItemInserted(position);
    }

    public void remove(int position){
        if (position < getItemCount()  ) {
            mIngredients.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvName;
        public final Button btnRemove;

        public SimpleViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.ingredient_list_name_edit);
            btnRemove = view.findViewById(R.id.btn_delete_ingredient);
        }
    }

    public IngredientsEditAdapter(Context context, List<Ingredient> ingredients, ListItemClickListener listener, boolean editable, int dishId) {
        mContext = context;
        mDishId = dishId;
        mOnClickListener = listener;
        if (ingredients != null) {
            mIngredients = ingredients;
        }
        else {
            mIngredients = new ArrayList<>();
        }
        this.editable = editable;
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_ingredient_editable, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        holder.tvName.setText(mIngredients.get(position).getName());
        if(!editable)
            holder.btnRemove.setVisibility(View.GONE);
        else{
            holder.btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mDishId != -1)
                        mOnClickListener.onListItemClick(position);
                    remove(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }
}