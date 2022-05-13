package com.example.eagerreader.app.repository;

import com.example.eagerreader.app.entity.Bookstore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookstoreRepository extends JpaRepository<Bookstore,Long> {

    Optional<Bookstore> findByName(String name);
}
