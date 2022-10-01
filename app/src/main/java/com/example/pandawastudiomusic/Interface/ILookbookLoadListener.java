package com.example.pandawastudiomusic.Interface;

import com.example.pandawastudiomusic.Model.Banner;

import java.util.List;

public interface ILookbookLoadListener {
    void onLookbookLoadSucces(List<Banner> banners);
    void onLookbookLoadFailed (String message);
}
