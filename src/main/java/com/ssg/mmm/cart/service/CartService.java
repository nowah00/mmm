package com.ssg.mmm.cart.service;

import com.ssg.mmm.cart.dto.CartRead;
import com.ssg.mmm.cart.entity.Cart;
import java.util.List;

public interface CartService {

    List<CartRead> findAll(Integer memberId);

    CartRead find(Integer memberId, Integer itemId);

    void remove(Integer memberId, Integer itemId);

    void save(Cart cart);
}
