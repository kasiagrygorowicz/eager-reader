package com.example.eagerreader.app.dto.publisher;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDTO {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String location;

    @Nullable
    private String founded;
}
