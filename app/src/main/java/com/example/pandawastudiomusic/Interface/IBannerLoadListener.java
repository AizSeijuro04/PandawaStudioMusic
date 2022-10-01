package com.example.pandawastudiomusic.Interface;

import com.example.pandawastudiomusic.Model.Banner;

import java.util.List;

public interface IBannerLoadListener {
    void onBannerLoadSucces(List<Banner> banners);
    void onBannerLoadFailed (String message);
}
