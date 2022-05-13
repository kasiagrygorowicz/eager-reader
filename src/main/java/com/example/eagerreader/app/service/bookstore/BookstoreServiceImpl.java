package com.example.eagerreader.app.service.bookstore;

import com.example.eagerreader.app.domain.entity.Bookstore;
import com.example.eagerreader.app.domain.repository.BookRepository;
import com.example.eagerreader.app.domain.repository.BookstoreRepository;
import com.example.eagerreader.app.dto.bookstore.BookstoreDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookstoreServiceImpl implements BookstoreService {

    private final BookstoreRepository bookstoreRepository;

    @Override
    public List<BookstoreDTO> getAllBookstores() {
        return bookstoreRepository.findAll().stream().map(b->BookstoreMapper.map(b)).collect(Collectors.toList());
    }


    private class BookstoreMapper{
        private static BookstoreDTO map(Bookstore bookstore){
            return new BookstoreDTO(bookstore.getName(), bookstore.getLink());
        }
    }
}
