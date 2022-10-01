package com.example.pandawastudiomusic.Database;


import static androidx.room.OnConflictStrategy.FAIL;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartDAO {

    @Query("SELECT SUM(productPrice*productQuantity) from Cart where userMail=:userMail")
    long sumPrice(String userMail);

    @Query("SELECT * FROM Cart WHERE userMail=:userMail")
    List<CartItem> getAllItemFromCart (String userMail);

    @Query("SELECT COUNT(*) from Cart where userMail=:userMail")
    int countItemInCart(String userMail);

    @Query("SELECT * from Cart where ProductId=:productId AND userMail=:userMail")
    CartItem getProductInCart (String productId, String userMail);

    @Insert(onConflict = FAIL)
    void insert(CartItem...carts);

    @Update(onConflict = FAIL)
    void update(CartItem cart);

    @Delete
    void delete(CartItem cartItem);

    @Query("DELETE FROM Cart WHERE userMail=:userMail")
    void clearCart(String userMail);

}
