package com.ssg.mmm.cart.dto;

import com.ssg.mmm.cart.entity.Cart;
import lombok.Getter;

@Getter
public class CartRequest {
    private Integer itemId;

    public Cart toEntity(Integer memberId) {
        return new Cart(memberId, itemId);
    }
}
