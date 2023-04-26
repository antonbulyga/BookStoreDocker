package com.bulyha.bookstore.service.impl;

import com.bulyha.bookstore.dto.BookDto;
import com.bulyha.bookstore.dto.OrderDto;
import com.bulyha.bookstore.mapper.BookConverter;
import com.bulyha.bookstore.mapper.OrderConverter;
import com.bulyha.bookstore.model.Book;
import com.bulyha.bookstore.model.Order;
import com.bulyha.bookstore.model.OrderStatus;
import com.bulyha.bookstore.model.Store;
import com.bulyha.bookstore.model.User;
import com.bulyha.bookstore.repository.BookRepository;
import com.bulyha.bookstore.repository.OrderRepository;
import com.bulyha.bookstore.repository.StoreRepository;
import com.bulyha.bookstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderConverter orderConverter;
    @Mock
    private BookConverter bookConverter;
    @Mock
    private BookRepository bookRepository;
    private List<Book> bookList = new ArrayList<>();
    private List<BookDto> bookDtoList = new ArrayList<>();
    private List<Order> orderList = new ArrayList<>();
    private Order order;
    private OrderDto orderDto;
    private Store store;
    private User user;

    @BeforeEach
    void setUp() {
        order = Order.builder()
                .id(262L)
                .totalPrice(200)
                .creationDate(generateDateForTest())
                .status(OrderStatus.IN_PROGRESS)
                .user(new User(1L))
                .store(new Store(1L))
                .bookList(bookList)
                .build();

        store = Store.builder()
                .id(1L)
                .orders(orderList)
                .bookList(bookList)
                .name("Store")
                .build();

        user = User.builder()
                .id(1L)
                .surname("Bond")
                .firstName("James")
                .orderList(orderList)
                .build();


        orderDto = OrderDto.builder()
                .id(262L)
                .totalPrice(200)
                .creationDate(generateDateForTest())
                .status(OrderStatus.IN_PROGRESS)
                .userId(user.getId())
                .storeId(store.getId())
                .bookList(bookDtoList)
                .build();

    }


    @Test
    void getAllOrders_returnAllOrders() {
        when(orderRepository.findAll()).thenReturn(List.of(order));
        when(orderConverter.orderToOrderDtoConverter(order)).thenReturn(orderDto);
        var results = orderService.getAllOrders();

        verify(orderRepository, times(1)).findAll();

        assertEquals(1, results.size());
        assertEquals(262L, results.get(0).getId());
        assertEquals(OrderStatus.IN_PROGRESS, results.get(0).getStatus());
        assertEquals(generateDateForTest(), results.get(0).getCreationDate());
        assertEquals(200, results.get(0).getTotalPrice());
        assertNotNull(results.get(0).getUserId());
        assertNotNull(results.get(0).getBookList());
        assertNotNull(results.get(0).getStoreId());
    }

    @Test
    void deleteOrder() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.ofNullable(order));
        orderService.deleteOrder(order.getId());
        verify(orderRepository, times(1)).deleteById(order.getId());
    }

    @Test
    void updateOrder() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.ofNullable(order));
        orderService.deleteOrder(order.getId());
        verify(orderRepository, times(1)).deleteById(order.getId());
    }

    @Test
    void getOrderById_returnOrder() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.ofNullable(order));
        when(orderConverter.orderToOrderDtoConverter(order)).thenReturn(orderDto);

        var result = orderService.getOrder(order.getId());

        verify(orderRepository, times(1)).findById(order.getId());

        assertEquals(262L, result.getId());
        assertEquals(OrderStatus.IN_PROGRESS, result.getStatus());
        assertEquals(generateDateForTest(), result.getCreationDate());
        assertEquals(200, result.getTotalPrice());
        assertNotNull(result.getUserId());
        assertNotNull(result.getBookList());
        assertNotNull(result.getStoreId());
    }

    @Test
    void addOrder_returnOrder() {
        when(storeRepository.findById(store.getId())).thenReturn(Optional.ofNullable(store));
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        when(orderRepository.save(any(Order.class))).thenAnswer(o -> o.getArguments()[0]);
        when(orderConverter.orderToOrderDtoConverter(any(Order.class))).thenReturn(orderDto);
        when(bookConverter.setBookIdFromBookDtoToBook(any(BookDto.class))).thenReturn(new Book(1L));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(new Book(1L)));

        orderService.addOrder(orderDto);

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    private LocalDateTime generateDateForTest() {
        String str = "2023-04-08 12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(str, formatter);
    }

}
