package com.barclays.bookstore.book;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(("/books"))
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    Page<Book> fetchBooks(@RequestParam(required = false, defaultValue = "") String searchQuery,
        Pageable pageable) {
        Page<Book> books = bookService.fetchBooks(searchQuery, pageable);
        if (books == null) {
            throw new BookNotFoundException();
        }
        return books;
    }

}
