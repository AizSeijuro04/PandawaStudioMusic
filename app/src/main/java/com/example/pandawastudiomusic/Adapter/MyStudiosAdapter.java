package com.example.pandawastudiomusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pandawastudiomusic.Common.Common;
import com.example.pandawastudiomusic.Interface.IRecyclerItemSelectedListener;
import com.example.pandawastudiomusic.Model.Studios;
import com.example.pandawastudiomusic.R;

import java.util.ArrayList;
import java.util.List;

public class MyStudiosAdapter extends RecyclerView.Adapter<MyStudiosAdapter.MyViewHolder> {

    Context context;
    List<Studios> studiosList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;

    public MyStudiosAdapter(Context context, List<Studios> studiosList) {
        this.context = context;
        this.studiosList = studiosList;
        cardViewList = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_studios,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.txt_studios_name.setText(studiosList.get(i).getName());
        myViewHolder.ratingBar.setRating((float) studiosList.get(i).getRating());
        if (!cardViewList.contains(myViewHolder.card_studios))
            cardViewList.add(myViewHolder.card_studios);

        myViewHolder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {
                //Set background for all item not choice
                for (CardView cardView:cardViewList)
                {
                    cardView.setCardBackgroundColor(context.getResources()
                            .getColor(android.R.color.white));
                }

                //set background for choice
                myViewHolder.card_studios.setCardBackgroundColor(
                        context.getResources()
                        .getColor(android.R.color.holo_orange_dark)
                );

                //send Local broadcast to enable button next
                Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                intent.putExtra(Common.KEY_STUDIOS_SELECTED, studiosList.get(pos));
                intent.putExtra(Common.KEY_STEP, 2);
                localBroadcastManager.sendBroadcast(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studiosList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_studios_name;
        RatingBar ratingBar;
        CardView card_studios;

        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card_studios = (CardView) itemView.findViewById(R.id.card_studios);
            txt_studios_name = (TextView) itemView.findViewById(R.id.txt_studios_name);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rtb_studios);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iRecyclerItemSelectedListener.onItemSelectedListener(view,getAdapterPosition());
        }
    }
}
