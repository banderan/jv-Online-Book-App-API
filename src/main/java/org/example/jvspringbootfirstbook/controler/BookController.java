package org.example.jvspringbootfirstbook.controler;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.jvspringbootfirstbook.dto.BookDto;
import org.example.jvspringbootfirstbook.dto.CreateBookRequestDto;
import org.example.jvspringbootfirstbook.service.BookService;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    public BookDto createBook(@RequestBody
                                  CreateBookRequestDto createBookRequestDto) {
        return bookService.save(createBookRequestDto);
    }
}
