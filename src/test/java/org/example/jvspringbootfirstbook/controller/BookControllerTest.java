package org.example.jvspringbootfirstbook.controller;

import org.example.jvspringbootfirstbook.dto.book.BookDtoWithoutCategoryIds;
import org.example.jvspringbootfirstbook.dto.book.CreateBookRequestDto;
import org.example.jvspringbootfirstbook.model.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Set;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    public static final String TITLE = "Title";
    public static final String AUTHOR = "author";
    public static final String ISBN = "1231231";
    public static final BigDecimal PRICE = BigDecimal.valueOf(123.12);
    public static final String DESCRIPTION = "ok book";
    public static final String COVER_IMAGE = "image";
    public static final Set<Long> CATEGORIES_ID = Set.of();
    public static final Set<Category> CATEGORIES = Set.of();
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "user", roles = {"ADMIN"})
    @Test
    public void createBook_ValidCreateBookRequestDto_Success() throws Exception {
        //Given
        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto(
                TITLE, AUTHOR, ISBN, PRICE, DESCRIPTION, COVER_IMAGE, CATEGORIES_ID
        );
        BookDtoWithoutCategoryIds expected = new BookDtoWithoutCategoryIds(
                1L, TITLE, AUTHOR, ISBN, PRICE, DESCRIPTION, COVER_IMAGE
        );

        String jsonRequest = objectMapper.writeValueAsString(createBookRequestDto);
        //When
        MvcResult result = mockMvc.perform(
                        post("/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        //Then
    }
}
