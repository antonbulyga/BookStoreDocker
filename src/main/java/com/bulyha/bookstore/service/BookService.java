package com.bulyha.bookstore.service;

import com.bulyha.bookstore.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto addBook(BookDto bookDto);

    BookDto getBook(Long bookId);

    List<BookDto> getAllBooks();

    BookDto updateBook(BookDto bookDto, Long bookId);

    void deleteBook(Long bookId);
}
