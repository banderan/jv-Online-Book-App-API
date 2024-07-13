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

    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(JvSpringBootFirstBookApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            public void run(String... args) throws Exception {
                Book firstBook = new Book();
                firstBook.setTitle("1984");
                firstBook.setAuthor("George Orwell");
                firstBook.setIsbn("1234");
                firstBook.setPrice(BigDecimal.valueOf(11.99));
                firstBook.setCoverImage("hand with eye");

                Book secondBook = new Book();
                secondBook.setTitle("Folwark zwierzęcy");
                secondBook.setAuthor("George Orwell");
                secondBook.setIsbn("4321");
                secondBook.setPrice(BigDecimal.valueOf(11.99));
                secondBook.setDescription("Book about animals");

                bookService.save(firstBook);
                bookService.save(secondBook);

                System.out.println(bookService.findAll());
            }
        };
    }
}
