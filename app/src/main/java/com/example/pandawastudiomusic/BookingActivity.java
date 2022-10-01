package com.example.pandawastudiomusic;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.pandawastudiomusic.Adapter.MyViewPagerAdapter;
import com.example.pandawastudiomusic.Common.Common;
import com.example.pandawastudiomusic.Model.Studios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class BookingActivity extends AppCompatActivity {

    LocalBroadcastManager localBroadcastManager;
    AlertDialog dialog;
    CollectionReference studiosRef;

    @BindView(R.id.step_view)
    StepView stepView;
    @BindView(R.id.view_pager)
    ViewPager2 viewPager2;
    @BindView(R.id.btn_previous_step)
    Button btn_previous_step;
    @BindView(R.id.btn_next_step)
    Button btn_next_step;

    //Event
    @OnClick(R.id.btn_previous_step)
    void previousStep(){
        if (Common.step == 3 || Common.step > 0)
        {
            Common.step--;
            viewPager2.setCurrentItem(Common.step);
            if (Common.step < 3 ) // always enable NEXT when step 3
            {
                btn_next_step.setEnabled(true);
                setColorButton();
            }
        }
    }

    @OnClick(R.id.btn_next_step)
    void nextClick(){
        if (Common.step < 3 || Common.step == 0)
        {
            Common.step++;//increase
            if (Common.step == 1) // After choose studio
            {
                if (Common.currentStudio != null)
                    loadStudiosByStudio(Common.currentStudio.getStudioID());
            }
            else if (Common.step == 2) //pick time slot
            {
                if(Common.currentStudios != null)
                    loadTimeSlotOfStudios(Common.currentStudios.getStudiosID());
            }
            else if (Common.step == 3) //Confirm
            {
                if(Common.currentTimeSlot != -1)
                    confirmBooking();
            }
            viewPager2.setCurrentItem(Common.step);
        }
    }

    private void confirmBooking() {
        //send broadcast to fragment step four
        Intent intent = new Intent(Common.KEY_CONFIRM_BOOKING);
        localBroadcastManager.sendBroadcast(intent);
    }

    private void loadTimeSlotOfStudios(String studiosID) {
        //send local broadcast to fragment step 3
        Intent intent = new Intent(Common.KEY_DISPLAY_TIME_SLOT);
        localBroadcastManager.sendBroadcast(intent);
    }

    private void loadStudiosByStudio(String studioID) {
        dialog.show();


        //Now, select all studios of studios
        //      /AllStudio/KJ/Branch/3L9TkgawC3ZBBDmJKVCY/Studios
        if (!TextUtils.isEmpty(Common.city))
        {
            studiosRef = FirebaseFirestore.getInstance()
                    .collection("AllStudio")
                    .document(Common.city)
                    .collection("Branch")
                    .document(studioID)
                    .collection("Studios");

            studiosRef.get().addOnCompleteListener(task -> {
                ArrayList<Studios> studioss = new ArrayList<>();
                for (QueryDocumentSnapshot studiosSnapShot:task.getResult())
                {
                    Studios studios = studiosSnapShot.toObject(Studios.class);
                    studios.setPassword(""); //remove password because in client app
                    studios.setStudiosID(studiosSnapShot.getId());//Get id of studios

                    studioss.add(studios);
                }
                //Send Broadcast to BookingStep2Fragment to Load Recycler
                Intent intent = new Intent(Common.KEY_STUDIOS_LOAD_DONE);
                intent.putParcelableArrayListExtra(Common.KEY_STUDIOS_LOAD_DONE, studioss);
                localBroadcastManager.sendBroadcast(intent);

                dialog.dismiss();
            })
                    .addOnFailureListener(e -> dialog.dismiss());
        }

    }

    //Broadcast Receiver
    private final BroadcastReceiver buttonNextReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int step = intent.getIntExtra(Common.KEY_STEP,0);
            if(step == 1)
                Common.currentStudio = intent.getParcelableExtra(Common.KEY_STUDIO_STORE);
            else if (step == 2)
                Common.currentStudios = intent.getParcelableExtra(Common.KEY_STUDIOS_SELECTED);
            else if (step == 3)
                Common.currentTimeSlot = intent.getIntExtra(Common.KEY_TIME_SLOT,-1);


            btn_next_step.setEnabled(true);
            setColorButton();
        }
    };

    @Override
    protected void onDestroy() {
        localBroadcastManager.unregisterReceiver(buttonNextReceiver);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        ButterKnife.bind(BookingActivity.this);

        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(buttonNextReceiver,new IntentFilter(Common.KEY_ENABLE_BUTTON_NEXT));

        setupStepView();
        setColorButton();



        //view
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(this);
        viewPager2.setAdapter(myViewPagerAdapter);

        viewPager2.setOffscreenPageLimit(4);//we have 4 steps, so we need keep state of this 4 screen page
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                viewPager2.setUserInputEnabled(false);
            }

            @Override
            public void onPageSelected(int i) {

                //show step
                stepView.go(i,true);
                if (i == 0)
                    btn_previous_step.setEnabled(false);
                else
                    btn_previous_step.setEnabled(true);

                //set disable button next here
                btn_next_step.setEnabled(false);
                setColorButton();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });

    }

    private void setColorButton() {
        if (btn_next_step.isEnabled())
        {
            btn_next_step.setBackgroundResource(R.color.colorButton);
        }
        else
        {
            btn_next_step.setBackgroundResource(android.R.color.darker_gray);
        }

        if (btn_previous_step.isEnabled())
        {
            btn_previous_step.setBackgroundResource(R.color.colorButton);
        }
        else
        {
            btn_previous_step.setBackgroundResource(android.R.color.darker_gray);
        }

    }

    private void setupStepView() {
        List<String> stepList = new ArrayList<String>(){{
            add("Studio");
            add("Pandawa Studio");
            add("Time");
            add("Confirm");
        }};
        stepView.setSteps(stepList);
    }
}