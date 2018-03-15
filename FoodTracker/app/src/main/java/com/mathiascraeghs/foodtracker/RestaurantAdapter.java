package com.mathiascraeghs.foodtracker;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mathi on 14/03/2018.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantAdapterViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    private int mNumberItems;
    public RestaurantAdapter(Context context, Cursor cursor){

        this.mContext = context;
        this.mCursor = cursor;;

    }

    @Override
    public RestaurantAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.restaurant_recycler_view_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        RestaurantAdapterViewHolder viewHolder = new RestaurantAdapterViewHolder(view);

        return viewHolder;


    }

    @Override
    public void onBindViewHolder(RestaurantAdapterViewHolder holder, int position) {
        String name;
        name= mCursor.getString(mCursor.getColumnIndex("name"));
        holder.listItemNumberView.setText(name);
    }

    @Override
    public int getItemCount() {

        if(mCursor == null) return 0;
        return mCursor.getCount();
    }

    class RestaurantAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView listItemNumberView;

        public RestaurantAdapterViewHolder(View view) {
            super(view);
            listItemNumberView = (TextView) view.findViewById(R.id.tv_item_number);
        }
        void bind(int listIndex){
            listItemNumberView.setText(String.valueOf(listIndex));
        }
    }


}
