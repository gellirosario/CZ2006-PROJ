package com.cz2006.helloworld.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.models.MapDetail;

import java.util.ArrayList;
import java.util.List;

public class DisplayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private Activity activity;
    private List<MapDetail> detailListOriginal;
    private List<MapDetail> detailListFiltered;
    private ItemFilter mFilter = new ItemFilter();

    public DisplayAdapter(ArrayList<MapDetail> arrayList, Activity activity) {
        this.activity = activity;
        this.detailListOriginal = arrayList;
        this.detailListFiltered = arrayList;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ListViewHolder lvh = (ListViewHolder) holder;

        String address = detailListFiltered.get(position).getAddressBlockHouseNumber() + " " +
                detailListFiltered.get(position).getAddressStreetName() + " " +
                detailListFiltered.get(position).getAddressPostalCode();

        lvh.detailTitle.setText(detailListFiltered.get(position).getName());
        lvh.detailAddress.setText(address);
        lvh.detailDescription.setText(detailListFiltered.get(position).getDescription());

    }

    @Override
    public int getItemCount() {

        if(detailListFiltered == null || detailListFiltered.size() == 0)
            return 0;

        return detailListFiltered.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView detailTitle;
        TextView detailAddress;
        TextView detailDescription;

        private ListViewHolder(View view) {
            super(view);

            detailTitle = view.findViewById(R.id.detailTitle);
            detailAddress = view.findViewById(R.id.detailAddress);
            detailDescription = view.findViewById(R.id.detailDescription);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            //Create Activity to go to

            //DetailActivity.detail = detailListFiltered.get(getAdapterPosition());
            //activity.startActivity(new Intent(getActivity(), DetailActivity.class));
        }

    }


    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            ArrayList<MapDetail> matches = new ArrayList<>();

            if (filterString.equalsIgnoreCase(""))
            {
                results.values = detailListOriginal;
                results.count = detailListOriginal.size();
            }
            else
            {

                for (int i = 0; i < detailListOriginal.size(); i++) {
                    String name, address;

                    if (detailListOriginal.get(i).getName() == null)
                        name = "";
                    else
                        name = detailListOriginal.get(i).getName().toLowerCase();

                    if (detailListOriginal.get(i).getAddressBlockHouseNumber() == null)
                        address = "";
                    else
                        address = detailListOriginal.get(i).getAddressBlockHouseNumber().toLowerCase();

                    if (detailListOriginal.get(i).getAddressStreetName()== null)
                        address = "";
                    else
                        address += " " + detailListOriginal.get(i).getAddressStreetName().toLowerCase();

                    if (detailListOriginal.get(i).getAddressPostalCode()== null)
                        address = "";
                    else
                        address += " " + detailListOriginal.get(i).getAddressPostalCode().toLowerCase();

                    if (name.contains(filterString) || address.contains(filterString)) {
                        matches.add(detailListOriginal.get(i));
                    }
                }

                results.values = matches;
                results.count = matches.size();

                Log.e("Size", results.count + "");
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            detailListFiltered = (ArrayList<MapDetail>) results.values;
            notifyDataSetChanged();
        }

    }
}
