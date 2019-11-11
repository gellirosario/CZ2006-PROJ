package com.cz2006.helloworld.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.managers.AccountManager;

public class Leaderboard_MonthAdapter extends RecyclerView.Adapter<Leaderboard_MonthAdapter.LbMonthViewHolder> {


    private Context mContext;
    private Cursor mCursor;
    public int i =1;

    public Leaderboard_MonthAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;
    }

    public class LbMonthViewHolder extends RecyclerView.ViewHolder{

        public TextView namemonthTV;
        public TextView rankmonthTV;
        public TextView ptsmonthTV;




        public LbMonthViewHolder(@NonNull View itemView) {
            super(itemView);

            namemonthTV = itemView.findViewById(R.id.namemonthTV);
            rankmonthTV = itemView.findViewById(R.id.rankmonthTV);
            ptsmonthTV= itemView.findViewById(R.id.ptsmonthTV);


        }
    }

    @NonNull
    @Override
    public LbMonthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(mContext);
        View view = inflator.inflate(R.layout.leaderboardmonthly_item,parent , false);
        return new LbMonthViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LbMonthViewHolder holder, int position) {

        if(!mCursor.moveToPosition(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(AccountManager.TABLE_ACCOUNT_COLUMN_USERNAME));
        int pts = mCursor.getInt(mCursor.getColumnIndex(AccountManager.TABLE_ACCOUNT_COLUMN_POINTS));

        holder.namemonthTV.setText(name);
        holder.ptsmonthTV.setText(String.valueOf(pts));

        holder.rankmonthTV.setText(String.valueOf(i));
        i++;

    }

    @Override
    public int getItemCount() {

        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(mCursor!=null){

            mCursor.close();
        }


        mCursor = newCursor;

        if(newCursor!=null){
            notifyDataSetChanged();
        }






    }
}
