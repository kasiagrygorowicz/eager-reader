package com.example.eagerreader.app.domain.repository;

import com.example.eagerreader.app.domain.entity.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    Optional<Author> findAuthorByFirstnameAndLastname(String firstname, String lastname);
}
