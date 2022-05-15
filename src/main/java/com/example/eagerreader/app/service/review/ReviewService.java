package com.example.eagerreader.app.service.review;


import com.example.eagerreader.app.dto.review.CreateReviewDTO;

public interface ReviewService {

    void addNewReview(CreateReviewDTO review, Long bookId);
}
