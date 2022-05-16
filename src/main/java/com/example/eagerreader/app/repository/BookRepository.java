package com.example.eagerreader.app.repository;

import com.example.eagerreader.app.entity.Author;
import com.example.eagerreader.app.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    Optional<Book> findByTitle(String title);
    List<Book> findByOrderByReviewScoreAsc();
    List<Book> findByOrderByReviewScoreDesc();
    List<Book> findBooksByReviewScoreIsLessThanEqual(Long reviewScore);
    List<Book> findBooksByReviewScoreIsGreaterThan(Long reviewScore);

}
