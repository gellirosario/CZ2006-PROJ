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

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LbAllTimeViewHolder> {


    private Context mContext;
    private Cursor mCursor;
    public int i =1;

    public LeaderboardAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;
    }

    public class LbAllTimeViewHolder extends RecyclerView.ViewHolder{

        public TextView namealltimeTV;
        public TextView rankalltimeTV;
        public TextView ptsalltimeTV;




        public LbAllTimeViewHolder(@NonNull View itemView) {
            super(itemView);

            namealltimeTV = itemView.findViewById(R.id.namealltimeTV);
            rankalltimeTV = itemView.findViewById(R.id.rankalltimeTV);
            ptsalltimeTV= itemView.findViewById(R.id.ptsalltimeTV);


        }
    }

    @NonNull
    @Override
    public LbAllTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(mContext);
        View view = inflator.inflate(R.layout.leaderboardalltime_item,parent , false);
        return new LbAllTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LbAllTimeViewHolder holder, int position) {

        if(!mCursor.moveToPosition(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(AccountManager.TABLE_ACCOUNT_COLUMN_USERNAME));
        int pts = mCursor.getInt(mCursor.getColumnIndex(AccountManager.TABLE_ACCOUNT_COLUMN_POINTS));

        holder.namealltimeTV.setText(name);
        holder.ptsalltimeTV.setText(String.valueOf(pts));

       holder.rankalltimeTV.setText(String.valueOf(i));
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
