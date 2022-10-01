package com.example.pandawastudiomusic.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pandawastudiomusic.Adapter.MyStudioAdapter;
import com.example.pandawastudiomusic.Common.Common;
import com.example.pandawastudiomusic.Common.SpacesItemDecoration;
import com.example.pandawastudiomusic.Interface.IAllStudioLoadListener;
import com.example.pandawastudiomusic.Interface.IBranchLoadListener;
import com.example.pandawastudiomusic.Model.Studio;
import com.example.pandawastudiomusic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class BookingStep1Fragment extends Fragment implements IAllStudioLoadListener, IBranchLoadListener {

    //Variable
    CollectionReference allStudioRef;
    CollectionReference branchRef;

    IAllStudioLoadListener iAllStudioLoadListener;
    IBranchLoadListener iBranchLoadListener;


    @BindView(R.id.spinner)
    MaterialSpinner spinner;
    @BindView(R.id.recycler_studio)
    RecyclerView recycler_studio;

    Unbinder unbinder;

    //AlertDialog dialog;

    static BookingStep1Fragment instance;

    public static BookingStep1Fragment getInstance() {
        if (instance == null)
            instance = new BookingStep1Fragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        allStudioRef = FirebaseFirestore.getInstance().collection("AllStudio");
        iAllStudioLoadListener = this;
        iBranchLoadListener = this;


        //dialog = new SpotsDialog.Builder().setContext(getActivity()).setCancelable(false).build();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View itemView = inflater.inflate(R.layout.fragment_booking_step_one,container,false);
        unbinder = ButterKnife.bind(this,itemView);

        initView();
        loadAllStudio();

        return itemView;
    }

    private void initView() {
        recycler_studio.setHasFixedSize(true);
        recycler_studio.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recycler_studio.addItemDecoration(new SpacesItemDecoration(4));
    }

    private void loadAllStudio() {
        allStudioRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            List<String> list = new ArrayList<>();
                            list.add("Silahkan pilih kota");
                            for (QueryDocumentSnapshot documentSnapshot:task.getResult())
                                list.add(documentSnapshot.getId());
                            iAllStudioLoadListener.onAllStudioLoadSuccess(list);

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iAllStudioLoadListener.onAllStudioLoadFailed(e.getMessage());
            }
        });
    }

    @Override
    public void onAllStudioLoadSuccess(List<String> areaNameList) {
        spinner.setItems(areaNameList);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position > 0)
                {
                    loadBranchOfCity(item.toString());
                }
                else
                    recycler_studio.setVisibility(View.GONE);
            }
        });
    }

    private void loadBranchOfCity(String cityName) {
        //dialog.show();

        Common.city = cityName;

        branchRef = FirebaseFirestore.getInstance()
                .collection("AllStudio")
                .document(cityName)
                .collection("Branch");

        branchRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Studio> list = new ArrayList<>();
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot documentSnapshot:task.getResult())
                    {
                        Studio studio = documentSnapshot.toObject(Studio.class);
                        studio.setStudioID(documentSnapshot.getId());
                        list.add(studio);
                    }
                    iBranchLoadListener.onBranchLoadSucces(list);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iBranchLoadListener.onBranchLoadFailed(e.getMessage());
            }
        });
    }

    @Override
    public void onAllStudioLoadFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBranchLoadSucces(List<Studio> studioList) {
        MyStudioAdapter adapter = new MyStudioAdapter(getActivity(),studioList);
        recycler_studio.setAdapter(adapter);
        recycler_studio.setVisibility(View.VISIBLE);


        //dialog.dismiss();
    }

    @Override
    public void onBranchLoadFailed(String message) {
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
        //dialog.dismiss();
    }
}
