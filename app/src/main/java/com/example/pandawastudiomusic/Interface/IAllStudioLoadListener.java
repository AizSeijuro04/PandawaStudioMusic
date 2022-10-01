package com.example.pandawastudiomusic.Interface;

import java.util.List;

public interface IAllStudioLoadListener {

    void onAllStudioLoadSuccess(List<String> areaNameList);
    void onAllStudioLoadFailed(String message);


}
