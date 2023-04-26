package com.bulyha.bookstore.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private Long id;
    private String author;
    private String publisherName;
    private String title;
    private LocalDate dateOfPublishing;
    private LocalDate dateOfWriting;
    private double priceOfBook;
    @NotNull(message = "storeId is required")
    private Long storeId;
    @NotNull(message = "orderId is required")
    private Long orderId;

}
