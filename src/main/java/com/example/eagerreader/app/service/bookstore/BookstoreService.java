package com.example.eagerreader.app.service.bookstore;

import com.example.eagerreader.app.dto.bookstore.BookstoreDTO;
import com.example.eagerreader.app.dto.bookstore.CreateBookstoreDTO;

import java.util.List;

public interface BookstoreService {

    void addBookstore(CreateBookstoreDTO bookstore);
    List<BookstoreDTO> getAllBookstores();
    void deleteBookstore(Long id);
    CreateBookstoreDTO getBookstoreToEdit(Long id);
    void editBookstore(CreateBookstoreDTO bookstore, Long id);
}
