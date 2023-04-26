package com.bulyha.bookstore.dto;

import com.bulyha.bookstore.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private OrderStatus status;
    private LocalDateTime creationDate;
    private LocalDateTime completingDate;
    @NotNull(message = "userId is required")
    private Long userId;
    @NotNull(message = "storeId is required")
    private Long storeId;
    private List<BookDto> bookList;
    private double totalPrice;
}
