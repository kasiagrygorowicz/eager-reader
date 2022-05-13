package com.example.eagerreader.app.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookDTO {

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



}
