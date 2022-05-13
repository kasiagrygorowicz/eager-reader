package com.example.eagerreader.app.repository;

import com.example.eagerreader.app.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher,Long> {

    Optional<Publisher> findByName(String name);
}
