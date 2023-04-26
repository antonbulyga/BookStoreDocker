package com.bulyha.bookstore.mapper;

import com.bulyha.bookstore.dto.OrderDto;
import com.bulyha.bookstore.model.Order;
import com.bulyha.bookstore.model.Store;
import com.bulyha.bookstore.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderConverter {
    private final BookConverter bookConverter;

    public OrderConverter(BookConverter bookConverter) {
        this.bookConverter = bookConverter;
    }

    public OrderDto orderToOrderDtoConverter(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .completingDate(order.getCompletingDate())
                .creationDate(order.getCreationDate())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .userId(order.getUser().getId())
                .storeId(order.getStore().getId())
                .bookList(order.getBookList().stream().map(bookConverter::setBookIdFromBookToBookDto).toList())
                .build();
    }

    public Order orderDtoToOrderConverter(OrderDto orderDto) {

        Order order = Order.builder()
                .id(orderDto.getId())
                .completingDate(orderDto.getCompletingDate())
                .creationDate(orderDto.getCreationDate())
                .status(orderDto.getStatus())
                .totalPrice(orderDto.getTotalPrice())
                .bookList(orderDto.getBookList().stream().map(bookConverter::setBookIdFromBookDtoToBook).toList())
                .build();
        Store store = new Store();
        store.setId(orderDto.getStoreId());
        order.setStore(store);
        User user = new User();
        user.setId(orderDto.getUserId());
        order.setUser(user);
        return order;
    }

    public Order orderDtoToOrderUpdater(Optional<Order> order, OrderDto orderDto) {
        order.ifPresent(o -> o.setId(orderDto.getId()));
        order.ifPresent(o -> o.setStatus(orderDto.getStatus()));
        order.ifPresent(o -> o.setCompletingDate(orderDto.getCompletingDate()));
        order.ifPresent(o -> o.setCreationDate(orderDto.getCreationDate()));
        order.ifPresent(o -> o.setTotalPrice(orderDto.getTotalPrice()));
        return order.get();
    }
}
