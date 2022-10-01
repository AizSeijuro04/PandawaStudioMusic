package com.example.pandawastudiomusic.Interface;

import com.example.pandawastudiomusic.Database.CartItem;

import java.util.List;

public interface ICartItemLoadListener {
    void onGetAllItemFromCartSuccess(List<CartItem> cartItemList);
}
