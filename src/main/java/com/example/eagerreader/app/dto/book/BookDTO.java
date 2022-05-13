package com.example.eagerreader.app.dto.book;

import com.example.eagerreader.app.dto.author.AuthorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDTO {

    @NotNull
    private String title;

    @NotNull
    private Long bookId;

    @NotNull
    List<AuthorDTO> authors;

    @Nullable
    private Long rating;



}
