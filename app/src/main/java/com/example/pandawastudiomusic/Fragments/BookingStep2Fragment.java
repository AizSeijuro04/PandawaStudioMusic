package com.example.pandawastudiomusic.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pandawastudiomusic.Adapter.MyStudiosAdapter;
import com.example.pandawastudiomusic.Common.Common;
import com.example.pandawastudiomusic.Common.SpacesItemDecoration;
import com.example.pandawastudiomusic.Model.Studios;
import com.example.pandawastudiomusic.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookingStep2Fragment extends Fragment {

    Unbinder unbinder;
    LocalBroadcastManager localBroadcastManager;


    @BindView(R.id.recycler_studios)
    RecyclerView recycler_studios;


    private final BroadcastReceiver studiosDoneReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Studios> studiosArrayList = intent.getParcelableArrayListExtra(Common.KEY_STUDIOS_LOAD_DONE);
            //Create adapter late
            MyStudiosAdapter adapter = new MyStudiosAdapter(getContext(),studiosArrayList);
            recycler_studios.setAdapter(adapter);
        }
    };

    static BookingStep2Fragment instance;
    public static BookingStep2Fragment getInstance(){
        if (instance == null)
            instance = new BookingStep2Fragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(studiosDoneReceiver, new IntentFilter(Common.KEY_STUDIOS_LOAD_DONE));
    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(studiosDoneReceiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

         View itemView = inflater.inflate(R.layout.fragment_booking_step_two,container,false);

         unbinder = ButterKnife.bind(this,itemView);

         initView();
        return itemView;
    }

    private void initView() {
        recycler_studios.setHasFixedSize(true);
        recycler_studios.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recycler_studios.addItemDecoration(new SpacesItemDecoration(4));
    }
}
