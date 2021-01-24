package com.barclays.bookstore.etl;

import com.barclays.bookstore.book.Book;
import com.barclays.bookstore.book.BookRepository;
import com.barclays.bookstore.image.Image;
import com.barclays.bookstore.image.ImageClient;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookETL {

    private final BookRepository bookRepository;
    private final ImageClient imageClient;
    private final BookClient bookClient;

    @EventListener(ApplicationReadyEvent.class)
    public void importBooksIfNotPresent() {
        if (bookRepository.count() == 0) {
            List<Image> images = imageClient.getAllImages();
            List<Book> books = bookClient.getAllBooks();
            Random random = new Random();
            books.forEach(book -> {
                Image image = images.get(random.nextInt(images.size()));
                book.setImage(image);
            });
            bookRepository.saveAll(books);
        }
    }

}
