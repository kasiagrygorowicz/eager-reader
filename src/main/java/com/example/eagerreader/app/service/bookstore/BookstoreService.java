package com.example.eagerreader.app.service.bookstore;

import com.example.eagerreader.app.domain.entity.Bookstore;
import com.example.eagerreader.app.dto.bookstore.BookstoreDTO;

import java.util.List;

public interface BookstoreService {

    List<BookstoreDTO> getAllBookstores();
}
