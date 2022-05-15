package com.example.eagerreader.app.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateReviewDTO {

    @NotNull
    private String review;

    @NotNull
    private Long rating;


}
