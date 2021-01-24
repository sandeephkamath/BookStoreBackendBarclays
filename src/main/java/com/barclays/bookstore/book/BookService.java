package com.barclays.bookstore.book;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Page<Book> fetchBooks(String searchQuery, Pageable pageable) {
        return bookRepository.findAllByTitleContainsIgnoreCase(searchQuery, pageable);
    }

}
