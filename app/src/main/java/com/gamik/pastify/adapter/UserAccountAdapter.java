package com.gamik.pastify.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamik.pastify.R;
import com.gamik.pastify.activity.OverLayListActivity;
import com.gamik.pastify.model.DataItem;
import com.gamik.pastify.model.UserAccountOption;
import com.gamik.pastify.store.Store;

import java.util.ArrayList;

import wei.mark.standout.StandOutWindow;

/**
 * HistoryAdapter class
 */
public class UserAccountAdapter extends RecyclerView.Adapter<UserAccountAdapter.ViewHolder> {
    private ArrayList<UserAccountOption> list;
    private Context context;
    private Store store;

    /**
     * Constructor for HistoryAdapter class
     *
     * @param list arraylist of history
     */
    public UserAccountAdapter(ArrayList<UserAccountOption> list, Context context) {
        this.list = list;
        this.context = context;
        store = new Store(context);
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_account_item, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * Called by RecyclerView to display the placeHolder at the specified position.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserAccountOption userAccountOption = list.get(position);
        holder.textView.setText(userAccountOption.getValue());
        holder.imageView.setImageDrawable(context.getResources().getDrawable(userAccountOption.getImageId()));
    }

    /**
     * Returns the total number of items in the placeHolder set hold by the adapter.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * ViewHolder class
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        /**
         * Constructor for ViewHolder class
         */
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.user_account_value);
            imageView = (ImageView) itemView.findViewById(R.id.user_account_imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }
}
