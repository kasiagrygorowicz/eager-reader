package com.example.eagerreader.app.dto.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import javax.validation.constraints.NotNull;
@NoArgsConstructor
@Data
@AllArgsConstructor
public class AuthorBookDTO {

    @NotNull
    private Long id;

    @NotNull
    private String title;

}
