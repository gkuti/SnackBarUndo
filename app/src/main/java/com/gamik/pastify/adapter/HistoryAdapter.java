package com.gamik.pastify.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gamik.pastify.activity.OverLayListActivity;
import com.gamik.pastify.R;
import com.gamik.pastify.store.Store;
import com.gamik.pastify.model.DataItem;

import java.util.ArrayList;

import wei.mark.standout.StandOutWindow;

/**
 * HistoryAdapter class
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private ArrayList<DataItem> list;
    private Context context;
    private Store store;

    /**
     * Constructor for HistoryAdapter class
     *
     * @param list arraylist of history
     */
    public HistoryAdapter(ArrayList<DataItem> list, Context context) {
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
                .inflate(R.layout.overlay_data_item, parent, false);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TextView textView = (TextView) view.findViewById(R.id.data_text);
//                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//                ClipData clip = ClipData.newPlainText("paster", textView.getText());
//                clipboard.setPrimaryClip(clip);
//                store.updateDataByPlaceHolder(textView.getText())
//                StandOutWindow.closeAll(context, OverLayListActivity.class);
//            }
//        });

        return new ViewHolder(itemView);
    }

    /**
     * Called by RecyclerView to display the placeHolder at the specified position.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataItem dataItem = list.get(position);
        holder.placeHolder.setText(dataItem.getPlaceHolder());
        holder.usage = dataItem.getUsage();
        holder.value = dataItem.getValue();
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
        private TextView placeHolder;
        private String value;
        private int usage = 0;

        /**
         * Constructor for ViewHolder class
         */
        public ViewHolder(View itemView) {
            super(itemView);
            placeHolder = (TextView) itemView.findViewById(R.id.data_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("paster", value);
                    clipboard.setPrimaryClip(clip);
                    store.updateDataByPlaceHolder((String) placeHolder.getText(), ++usage);
                    StandOutWindow.closeAll(context, OverLayListActivity.class);
                }
            });
        }
    }
}
