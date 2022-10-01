package com.example.pandawastudiomusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pandawastudiomusic.Common.Common;
import com.example.pandawastudiomusic.Interface.IRecyclerItemSelectedListener;
import com.example.pandawastudiomusic.Model.Studio;
import com.example.pandawastudiomusic.R;

import java.util.ArrayList;
import java.util.List;

public class MyStudioAdapter extends RecyclerView.Adapter<MyStudioAdapter.MyViewHolder> {

    Context context;
    List<Studio> studioList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;

    public MyStudioAdapter(Context context, List<Studio> studioList) {
        this.context = context;
        this.studioList = studioList;
        cardViewList = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_studio,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.txt_studio_name.setText(studioList.get(i).getName());
        myViewHolder.txt_studio_address.setText(studioList.get(i).getAddress());

        if (!cardViewList.contains(myViewHolder.card_studio))
            cardViewList.add(myViewHolder.card_studio);

        myViewHolder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {
                //Set white background for all card not be selected
                for (CardView cardView:cardViewList)
                    cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

                //Set selected BG for only selected item
                myViewHolder.card_studio.setCardBackgroundColor(context.getResources()
                        .getColor(android.R.color.holo_orange_dark));

                //Send Broadcast to tell Booking Activity enable Button next
                Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                intent.putExtra(Common.KEY_STUDIO_STORE, studioList.get(pos));
                intent.putExtra(Common.KEY_STEP,1);
                localBroadcastManager.sendBroadcast(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studioList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_studio_name, txt_studio_address;
        CardView card_studio;

        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card_studio = (CardView)itemView.findViewById(R.id.card_studio);
            txt_studio_address = (TextView) itemView.findViewById(R.id.txt_studio_address);
            txt_studio_name = (TextView) itemView.findViewById(R.id.txt_studio_name);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iRecyclerItemSelectedListener.onItemSelectedListener(view,getAdapterPosition());
        }
    }
}
