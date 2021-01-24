package com.barclays.bookstore.book;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    BookService bookService;

    @Mock
    BookRepository bookRepository;

    @Test
    void fetchBooks_expectBooks_withArgs() {
        String searchQuery = "potter";
        Pageable pageable = PageRequest.of(1, 10);
        List<Book> books = getBooks();

        PageImpl<Book> pagedBooks = new PageImpl<>(books);
        when(bookRepository.findAllByTitleContainsIgnoreCase(searchQuery, pageable))
            .thenReturn(pagedBooks);

        Page<Book> actualBooks = bookService.fetchBooks(searchQuery, pageable);
        assertThat(actualBooks).isEqualTo(pagedBooks);
    }

    private List<Book> getBooks() {
        return singletonList(Book.builder().bookId(1L).averageRating(3.2).title("Harry Potter").build());
    }
}