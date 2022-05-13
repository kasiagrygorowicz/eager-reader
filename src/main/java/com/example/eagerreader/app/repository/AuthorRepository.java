package com.example.eagerreader.app.repository;

import com.example.eagerreader.app.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    Optional<Author> findAuthorByFirstnameAndLastname(String firstname, String lastname);
}
