package com.example.eagerreader.app.dto.bookstore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookstoreDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String link;

}
