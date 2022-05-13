package com.example.eagerreader.app.domain.repository;

import com.example.eagerreader.app.domain.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    Optional<Book> findByTitle(String title);
    List<Book> findByOrderByReviewScoreAsc();
    List<Book> findByOrderByReviewScoreDesc();
}
