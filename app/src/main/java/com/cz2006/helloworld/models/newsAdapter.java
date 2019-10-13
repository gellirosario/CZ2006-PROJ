package com.cz2006.helloworld.models;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cz2006.helloworld.R;

import java.util.List;

public class newsAdapter extends RecyclerView.Adapter<newsAdapter.MyViewHolder> {


    private List<Article> articles;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public newsAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.newsitem, parent, false);

        return new MyViewHolder(view, (AdapterView.OnItemClickListener) onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder = holders;
        Article model = articles.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Newsutils.getRandomDrawableColor());
        requestOptions.error(Newsutils.getRandomDrawableColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(model.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);

        holder.title.setText(model.getTitle());
        holder.desc.setText(model.getDescription());
        holder.source.setText(model.getSource().getName());
        holder.time.setText(" \u2022 " + Newsutils.DateToTimeFormat(model.getPublishedAt()));
        holder.published_ad.setText(Newsutils.DateFormat(model.getPublishedAt()));
        holder.author.setText(model.getAuthor());






    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface  OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView title, desc,author , published_ad, source,time ;
        ImageView imageView;
        ProgressBar progressBar;
        OnItemClickListener onItemClickListener;



        public MyViewHolder(View itemView, AdapterView.OnItemClickListener onItemClickListener){
            super(itemView);

            itemView.setOnClickListener(this);
            title= itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            published_ad = itemView.findViewById(R.id.publishedAt);
            source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.img);
            progressBar= itemView.findViewById(R.id.prograss_load_photo);

            this.onItemClickListener = (OnItemClickListener) onItemClickListener;




        }

        @Override
        public void onClick(View v){

            onItemClickListener.onItemClick(v,getAdapterPosition());
        }

    }


}