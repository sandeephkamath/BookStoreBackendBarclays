package com.barclays.bookstore.book;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void fetchBooks_expectBooksFromService_withProvidedArgs() throws Exception {
        String searchQuery = "the";
        int page = 1;
        int size = 5;
        String sortString = "averageRating,ASC";
        Sort sort = Sort.by("averageRating").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        List<Book> books = getBooks();
        PageImpl<Book> pagedBooks = new PageImpl<>(books);

        when(bookService.fetchBooks(searchQuery, pageable)).thenReturn(pagedBooks);

        mockMvc.perform(get("/books")
            .content(MediaType.APPLICATION_JSON_VALUE)
            .param("searchQuery", searchQuery)
            .param("page", Integer.toString(page))
            .param("size", Integer.toString(size))
            .param("sort", sortString))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(pagedBooks)));
    }

    @Test
    void fetchBooks_expectBookNotFoundException_whenContentsNotPresent() {
        String searchQuery = "the";
        when(bookService.fetchBooks(any(), any())).thenReturn(null);

        assertThrows(BookNotFoundException.class,()->{
            mockMvc.perform(get("/books")
                .content(MediaType.APPLICATION_JSON_VALUE)
                .param("searchQuery", searchQuery));
        });
    }

    private List<Book> getBooks() {
        return singletonList(
            Book.builder().bookId(1L).averageRating(3.2).title("Harry Potter").build());
    }
}