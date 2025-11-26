package com.ssg.mmm.order.service;

import com.ssg.mmm.order.dto.OrderRead;
import com.ssg.mmm.order.dto.OrderRequest;
import java.util.List;

public interface OrderService {

    List<OrderRead> findAll(Integer memberId); // ①

    OrderRead find(Integer id, Integer memberId); // ②

    void order(OrderRequest orderReq, Integer memberId); // ③
}
