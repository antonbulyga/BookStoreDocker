package com.bulyha.bookstore.service.impl;

import com.bulyha.bookstore.dto.BookDto;
import com.bulyha.bookstore.exception.ResourceNotFoundException;
import com.bulyha.bookstore.mapper.BookConverter;
import com.bulyha.bookstore.model.Book;
import com.bulyha.bookstore.repository.BookRepository;
import com.bulyha.bookstore.repository.OrderRepository;
import com.bulyha.bookstore.repository.StoreRepository;
import com.bulyha.bookstore.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final BookConverter bookConverter;


    public BookServiceImpl(BookRepository bookRepository, OrderRepository orderRepository, StoreRepository storeRepository, BookConverter bookConverter) {
        this.bookRepository = bookRepository;
        this.orderRepository = orderRepository;
        this.storeRepository = storeRepository;
        this.bookConverter = bookConverter;
    }


    @Override
    public BookDto addBook(BookDto bookDto) {
        Book book = bookConverter.bookDtoToBookConverter(bookDto);
        book.setStore(storeRepository.findById(bookDto.getStoreId()).get());
        book.setOrder(orderRepository.findById(bookDto.getOrderId()).get());
        Book savedBook = bookRepository.save(book);
        return bookConverter.bookToBookDtoConverter(savedBook);
    }

    @Override
    public BookDto getBook(Long bookId) {
        Optional<Book> book = Optional.ofNullable(bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book", "Id", bookId)));
        return bookConverter.bookToBookDtoConverter(book.get());
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(bookConverter::bookToBookDtoConverter).toList();
    }

    @Override
    public BookDto updateBook(BookDto bookDto, Long bookId) {
        Optional<Book> book = Optional.ofNullable(bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book", "Id", bookId)));
        Book bookAfterUpdate = bookConverter.bookDtoToBookUpdater(book,bookDto);
        bookRepository.save(bookAfterUpdate);
        return bookConverter.bookToBookDtoConverter(bookAfterUpdate);
    }

    @Override
    public void deleteBook(Long bookId) {
        if (bookRepository.findById(bookId).isPresent()) {
            bookRepository.deleteById(bookId);
        } else {
            throw new ResourceNotFoundException("We don't have such book in the database");
        }

    }


}
