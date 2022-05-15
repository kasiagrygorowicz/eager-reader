package com.example.eagerreader.app.dto.book;

import com.example.eagerreader.app.dto.author.AuthorDTO;
import com.example.eagerreader.app.dto.bookstore.BookstoreDTO;
import com.example.eagerreader.app.dto.publisher.PublisherDTO;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDetailsDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String published;

    @NotNull
    private PublisherDTO publisher;

    @NotNull
    private List<AuthorDTO> authors;

    @Nullable
    private List<BookstoreDTO> bookstores;

    @Nullable
    private boolean isFavorite;

    @Nullable
    private Long rating;
}