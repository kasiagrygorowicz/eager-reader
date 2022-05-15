package com.example.eagerreader.app.service.review;

import com.example.eagerreader.app.dto.review.CreateReviewDTO;
import com.example.eagerreader.app.dto.review.ReviewDTO;
import com.example.eagerreader.app.entity.Book;
import com.example.eagerreader.app.entity.Review;
import com.example.eagerreader.app.entity.User;
import com.example.eagerreader.app.exception.bookException.BookNotFoundException;
import com.example.eagerreader.app.repository.BookRepository;
import com.example.eagerreader.app.repository.ReviewRepository;
import com.example.eagerreader.app.repository.UserRepository;
import com.example.eagerreader.app.service.user.UserServiceImpl;
import com.example.eagerreader.app.service.user.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;



    @Override
    public void addNewReview(CreateReviewDTO review, Long bookId) {
        Book book=findBookById(bookId);
        book.addRating(review.getRating());
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Review rev=ReviewMapper.map(review,book,user);
        reviewRepository.save(rev);
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new BookNotFoundException("Book with the id " + id + " does not exists")
        );
    }




    public class ReviewMapper {

        private static Review map(CreateReviewDTO review, Book book,User user){

            return new Review(
                    review.getRating(),
                    review.getReview(),
                    user,
                    book,
                    new Date(System.currentTimeMillis())
            );
        }

        public static ReviewDTO map(Review review){

            boolean isOwner =false;
            return new ReviewDTO(review.getReview(), review.getReviewScore(), review.getAuthor(),isOwner,review.getLastEdited().toString());
        }
    }
}
