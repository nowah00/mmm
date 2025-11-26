package com.ssg.mmm.order.service;

import com.ssg.mmm.order.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    List<OrderItem> findAll(Integer orderId);

    void saveAll(List<OrderItem> orderItems);
}
