package com.example.eagerreader.app.dto.author;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorDetailsDTO {


    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @Nullable
    private String info;

    @Nullable
    List<AuthorBookDTO> books;


}
