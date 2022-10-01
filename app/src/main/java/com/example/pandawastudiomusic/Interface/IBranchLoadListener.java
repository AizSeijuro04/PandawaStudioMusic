package com.example.pandawastudiomusic.Interface;

import com.example.pandawastudiomusic.Model.Studio;

import java.util.List;

public interface IBranchLoadListener {
    void onBranchLoadSucces(List<Studio> studioList) ;

    void onBranchLoadFailed(String message);
}
