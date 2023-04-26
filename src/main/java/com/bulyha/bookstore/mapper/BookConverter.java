package com.bulyha.bookstore.mapper;

import com.bulyha.bookstore.dto.BookDto;
import com.bulyha.bookstore.model.Book;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookConverter {
    public Book bookDtoToBookUpdater(Optional<Book> book, BookDto bookDto) {
        book.ifPresent(b -> b.setId(bookDto.getId()));
        book.ifPresent(b -> b.setPriceOfBook(bookDto.getPriceOfBook()));
        book.ifPresent(b -> b.setAuthor(bookDto.getAuthor()));
        book.ifPresent(b -> b.setDateOfPublishing(bookDto.getDateOfPublishing()));
        book.ifPresent(b -> b.setDateOfWriting(bookDto.getDateOfWriting()));
        return book.get();
    }

    public BookDto bookToBookDtoConverter(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setPriceOfBook(book.getPriceOfBook());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setDateOfPublishing(book.getDateOfPublishing());
        bookDto.setDateOfWriting(book.getDateOfWriting());
        bookDto.setStoreId(book.getStore().getId());
        bookDto.setOrderId(book.getOrder().getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setPublisherName(book.getPublisherName());
        return bookDto;
    }

    public Book bookDtoToBookConverter(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setPriceOfBook(bookDto.getPriceOfBook());
        book.setAuthor(bookDto.getAuthor());
        book.setDateOfPublishing(bookDto.getDateOfPublishing());
        book.setDateOfWriting(bookDto.getDateOfWriting());
        book.setTitle(bookDto.getTitle());
        book.setPublisherName(bookDto.getPublisherName());
        return book;
    }

    public Book setBookIdFromBookDtoToBook(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        return book;
    }

    public BookDto setBookIdFromBookToBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        return bookDto;
    }
}
