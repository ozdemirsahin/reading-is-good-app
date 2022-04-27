package com.example.controller;

import com.example.controller.request.AddBookRequest;
import com.example.controller.response.BookDto;
import com.example.service.BookService;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BookControllerTest {
    private static final String NAME = "Test Book";
    private static final String WRITER = "Test Writer";
    private static final int PUBLISH_YEAR = 2021;
    private static final int STOCK = 100;

    private MockMvc mockMvc;

    @Autowired
    protected Gson gson;

    @MockBean
    private BookService bookService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach()
    public void setup()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void afterTest()
    {
         verifyNoMoreInteractions(bookService);
    }

    @Test
    void createBook() throws Exception {

        BookDto createdBook = BookDto.builder()
                .name(NAME)
                .writer(WRITER)
                .publishYear(PUBLISH_YEAR)
                .stock(STOCK)
                .build();

        AddBookRequest request = new AddBookRequest(NAME, WRITER, PUBLISH_YEAR, 10, 5.0);
        when(bookService.addBook(any(AddBookRequest.class))).thenReturn(createdBook);

        String url = "/book/add";
        this.mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(bookService).addBook(any(AddBookRequest.class));
    }

    @Test
    void updateBook() throws Exception {
        String bookId = "b1L";
        int newStock = 10;

        BookDto updatedBook = BookDto.builder()
                .stock(STOCK)
                .build();
        when(bookService.updateBookStock(bookId, newStock)).thenReturn(updatedBook);

        this.mockMvc.perform(put("/book/update/{bookId}", bookId)
                .param("stockCount", String.valueOf(newStock)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(bookService).updateBookStock(bookId, newStock);
    }
}
