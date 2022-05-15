package com.example.eagerreader.app.dto.review;

import com.example.eagerreader.app.entity.User;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReviewDTO {

    @NotNull
    private String review;

    @NotNull
    private Long rating;

    @Nullable
    private User author;

    @NotNull
    private boolean isCurrentUserOwner;

    @NotNull
    private String lastEdited;





}
