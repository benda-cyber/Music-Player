package com.example.musicplayer;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardCustomAdapter extends RecyclerView.Adapter<CardCustomAdapter.MyViewHolder> {

    private static final String TAG = "CardCustomAdapter";
    private ArrayList<Song> dataSet;
    private OnCardListener mOnCardListener;
    private Context context;

    public CardCustomAdapter(ArrayList<Song> data, OnCardListener onCardListener) {
        this.dataSet = data;
        this.mOnCardListener = onCardListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_card_view, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view,mOnCardListener);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int listPosition) {

        Song song = dataSet.get(listPosition);
        TextView textViewName = holder.textViewName;
        TextView textViewLink = holder.textViewLink;
        CardView cardView = holder.cardView;

        textViewName.setText(song.getSongName());
        textViewLink.setText(song.getSongLink());


    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CardView cardView;
        public TextView textViewName;
        public TextView textViewLink;
        public View itemView;
        public OnCardListener onCardListener;
        ClipData.Item currentItem;

        public MyViewHolder(@NonNull View itemView, OnCardListener onCardListener) {
            super(itemView);

            this.cardView = (CardView) itemView.findViewById(R.id.card_view);
            this.textViewName = (TextView) itemView.findViewById(R.id.songName);

            this.itemView = itemView;
            this.onCardListener = onCardListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onCardListener.onCardClick(getAdapterPosition());
        }


    }


    public interface OnCardListener{

        void onCardClick(int position);

    }



    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void removeAt(int position) {
        dataSet.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataSet.size());
    }


}