package com.bulyha.bookstore.service.impl;

import com.bulyha.bookstore.dto.OrderDto;
import com.bulyha.bookstore.exception.ResourceNotFoundException;
import com.bulyha.bookstore.mapper.BookConverter;
import com.bulyha.bookstore.mapper.OrderConverter;
import com.bulyha.bookstore.model.*;
import com.bulyha.bookstore.repository.BookRepository;
import com.bulyha.bookstore.repository.OrderRepository;
import com.bulyha.bookstore.repository.StoreRepository;
import com.bulyha.bookstore.repository.UserRepository;
import com.bulyha.bookstore.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final OrderConverter orderConverter;
    private final BookConverter bookConverter;

    public OrderServiceImpl(OrderRepository orderRepository, StoreRepository storeRepository, BookRepository bookRepository, UserRepository userRepository, OrderConverter orderConverter, BookConverter bookConverter) {
        this.orderRepository = orderRepository;
        this.storeRepository = storeRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.orderConverter = orderConverter;
        this.bookConverter = bookConverter;
    }

    @Override
    public OrderDto addOrder(OrderDto orderDto) {
        Order savedOrder;
        Order processedOrder = processOrderBeforeSave(orderDto);
        if (checkBooksAvailability(processedOrder, Optional.ofNullable(processedOrder.getStore()))) {
            double totalPriceFromTHeOrder = calculateSumPriceOfTheOrder(processedOrder);
            processedOrder.setTotalPrice(totalPriceFromTHeOrder);
            processedOrder.setCreationDate(LocalDateTime.now());
            savedOrder = orderRepository.save(processedOrder);
        } else {
            throw new ResourceNotFoundException("We don't have all of the books in the store, sorry");
        }
        return orderConverter.orderToOrderDtoConverter(savedOrder);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        Optional<Order> order = Optional.ofNullable(orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "Id", orderId)));
        return orderConverter.orderToOrderDtoConverter(order.get());
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderConverter::orderToOrderDtoConverter).toList();
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto, Long orderId) {
        Optional<Order> order = Optional.ofNullable(orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "Id", orderId)));
        Order orderAfterUpdate = orderConverter.orderDtoToOrderUpdater(order, orderDto);
        orderRepository.save(orderAfterUpdate);
        return orderConverter.orderToOrderDtoConverter(orderAfterUpdate);
    }

    @Override
    public void deleteOrder(Long orderId) {
        if (orderRepository.findById(orderId).isPresent()) {
            orderRepository.deleteById(orderId);
        } else {
            throw new ResourceNotFoundException("We don't have such order in the database");
        }
    }

    private double calculateSumPriceOfTheOrder(Order order) {
        List<Optional<Book>> listOfBooksFromTheOrder = order.getBookList().stream().map(Book::getId).map(bookRepository::findById).toList();
        return listOfBooksFromTheOrder.stream().map(b -> b.get()).map(Book::getPriceOfBook).mapToDouble(i -> i).sum();
    }

    private boolean checkBooksAvailability(Order order, Optional<Store> store) {
        boolean flag = false;
        if (store.isPresent()) {
            List<Long> listOfBookFromTheStore = store.get().getBookList().stream().map(Book::getId).toList();
            List<Long> listOfBookFromOrder = order.getBookList().stream().map(Book::getId).toList();
            flag = listOfBookFromTheStore.containsAll(listOfBookFromOrder);
        }
        return flag;
    }

    private Order processOrderBeforeSave(OrderDto orderDto) {
        Optional<Store> storeFromOrderDto = storeRepository.findById(orderDto.getStoreId());
        Optional<User> userFromDto = userRepository.findById(orderDto.getUserId());

        Order order = new Order();
        order.setStore(storeFromOrderDto.get());
        order.setUser(userFromDto.get());
        order.setStatus(OrderStatus.IN_PROGRESS);
        order.setCreationDate(LocalDateTime.now());
        order.setBookList(orderDto.getBookList().stream().map(bookConverter::setBookIdFromBookDtoToBook).toList());
        return order;
    }

    private Map<OrderStatus, Double> groupOrdersByOrderStatus() {
        List<Order> orderList = orderRepository.findAll();
        Map<OrderStatus, Double> groupedOrder = orderList.stream().collect(Collectors.groupingBy(Order::getStatus, Collectors.summingDouble(Order::getTotalPrice)));
        return groupedOrder;
    }

}
