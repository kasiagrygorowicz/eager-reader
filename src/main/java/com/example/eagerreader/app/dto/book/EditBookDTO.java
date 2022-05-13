package com.example.eagerreader.app.dto.book;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EditBookDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String published;

    @NotBlank(message = "Publisher filed cannot be blank")
    private String publisher;

    @NotBlank(message = "Author filed cannot be blank")
    private String author;

   @Nullable
    private String bookstores;
}
