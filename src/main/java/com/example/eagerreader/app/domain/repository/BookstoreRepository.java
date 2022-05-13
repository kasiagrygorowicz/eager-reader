package com.example.eagerreader.app.domain.repository;

import com.example.eagerreader.app.domain.entity.Bookstore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookstoreRepository extends JpaRepository<Bookstore,Long> {
}
