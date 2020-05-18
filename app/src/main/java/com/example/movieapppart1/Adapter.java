package com.example.movieapppart1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<MyMovie> myMovies;
    OnItemClickListener listener;

    public Adapter(List<MyMovie> myMovies, OnItemClickListener listener) {
        this.myMovies = myMovies;
        this.listener = listener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.item_poster, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(myMovies.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return myMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView poster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.iv_movie_item);
        }

        public void onBind(final MyMovie mymovie, final OnItemClickListener listener) {
            Glide.with(itemView.getContext())
                    .load(imagePath(mymovie.getPosterPath()))
                    .into(poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(mymovie);
                }
            });
        }
    }

    private static String imagePath(String posterPath) {
        return "https://image.tmdb.org/t/p/" + "w500" + posterPath;
    }
}
