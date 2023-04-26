package com.bulyha.bookstore.service;

import com.bulyha.bookstore.dto.OrderDto;
import com.bulyha.bookstore.model.Order;

import java.util.List;

public interface OrderService {
    OrderDto addOrder(OrderDto orderDto);

    OrderDto getOrder(Long orderId);

    List<OrderDto> getAllOrders();

    OrderDto updateOrder(OrderDto orderDto, Long orderId);

    void deleteOrder(Long orderId);

}
