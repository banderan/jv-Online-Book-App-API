package org.example.jvspringbootfirstbook;

import java.math.BigDecimal;
import org.example.jvspringbootfirstbook.model.Book;
import org.example.jvspringbootfirstbook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JvSpringBootFirstBookApplication {
    private static final String AUTHOR_GEORGE_ORWELL = "George Orwell";
    private static final BigDecimal PRICE_11_99 = BigDecimal.valueOf(11.99);

    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(JvSpringBootFirstBookApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            public void run(String... args) throws Exception {
                Book firstBook = bookCreation("1984", AUTHOR_GEORGE_ORWELL,
                        "1234", PRICE_11_99,
                        "hand with eye", null);
                Book secondBook = bookCreation("Folwark zwierzęcy", AUTHOR_GEORGE_ORWELL,
                        "4321", PRICE_11_99,
                        null, "Book about animals");
                bookService.save(firstBook);
                bookService.save(secondBook);

                System.out.println(bookService.findAll());
            }
        };
    }

    private static Book bookCreation(String title, String author,
                                     String isbn, BigDecimal price,
                                     String coverImage, String description) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPrice(price);
        book.setCoverImage(coverImage);
        book.setDescription(description);
        return book;
    }
}
