package com.example.eagerreader.app.dto.bookstore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateBookstoreDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String link;

}
