package com.example.eagerreader.app.dto.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthorFilter {

    @Nullable
    private String firstname;

    @Nullable
    private String lastname;

    @Nullable
    private String filter;
}
