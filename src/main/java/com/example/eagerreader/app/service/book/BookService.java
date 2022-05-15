package com.example.eagerreader.app.service.book;


import com.example.eagerreader.app.dto.book.BookDTO;
import com.example.eagerreader.app.dto.book.BookDetailsDTO;
import com.example.eagerreader.app.dto.book.CreateBookDTO;
import com.example.eagerreader.app.dto.book.EditBookDTO;
import com.example.eagerreader.app.dto.review.ReviewDTO;

import java.util.List;

public interface BookService {
    void addBook(CreateBookDTO book);
    EditBookDTO getBookToEdit(Long id);
    void editBook(EditBookDTO book,Long id);
    List<BookDTO> getAllBooks();
    List<BookDTO> getAllBooksByFilter(String filter);
    void deleteBook(Long id);
    BookDetailsDTO getBookDetails(Long id);
    List<ReviewDTO> getReviews(Long id);

}
