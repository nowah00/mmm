package com.ssg.mmm.order.service;

import com.ssg.mmm.cart.service.CartService;
import com.ssg.mmm.item.dto.ItemRead;
import com.ssg.mmm.item.service.ItemService;
import com.ssg.mmm.order.dto.OrderRead;
import com.ssg.mmm.order.dto.OrderRequest;
import com.ssg.mmm.order.entity.Order;
import com.ssg.mmm.order.entity.OrderItem;
import com.ssg.mmm.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final ItemService itemService;
    private final CartService cartService;


    @Override
    public List<OrderRead> findAll(Integer memberId) {
        return orderRepository.findAllByMemberIdOrderByIdDesc(memberId).stream().map(Order::toRead).toList();
    }


    @Override
    public OrderRead find(Integer id, Integer memberId) {
        Optional<Order> orderOptional = orderRepository.findByIdAndMemberId(id, memberId);

        if (orderOptional.isPresent()) {
            OrderRead order = orderOptional.get().toRead();
            List<OrderItem> orderItems = orderItemService.findAll(order.getId());

            List<Integer> orderItemIds = orderItems.stream().map(OrderItem::getItemId).toList();

            List<ItemRead> items = itemService.findAll(orderItemIds);

            order.setItems(items);

            return order;
        }

        return null;
    }

    @Override
    @Transactional
    public void order(OrderRequest orderReq, Integer memberId) {
        List<ItemRead> items = itemService.findAll(orderReq.getItemIds());
        long amount = 0L;

        for (ItemRead item : items) {
            amount += item.getPrice() - item.getPrice().longValue() * item.getDiscountPer() / 100;
        }

        orderReq.setAmount(amount);

        Order order = orderRepository.save(orderReq.toEntity(memberId));

        List<OrderItem> newOrderItems = new ArrayList<>();

        orderReq.getItemIds().forEach((itemId) -> {
            OrderItem newOrderItem = new OrderItem(order.getId(), itemId);
            newOrderItems.add(newOrderItem);
        });

        orderItemService.saveAll(newOrderItems);
    }
}
