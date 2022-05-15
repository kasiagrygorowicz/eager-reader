package com.example.eagerreader.app.service.bookstore;

import com.example.eagerreader.app.dto.bookstore.CreateBookstoreDTO;
import com.example.eagerreader.app.entity.Book;
import com.example.eagerreader.app.entity.Bookstore;
import com.example.eagerreader.app.exception.bookstoreException.DuplicateBookstoreException;
import com.example.eagerreader.app.repository.BookstoreRepository;
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
    public void addBookstore(CreateBookstoreDTO bookstore) {
        findByName(bookstore.getName());
        bookstoreRepository.save(BookstoreMapper.map(bookstore));
    }

    @Override
    public List<BookstoreDTO> getAllBookstores() {
        return bookstoreRepository.findAll().stream().map(b -> BookstoreMapper.map(b)).collect(Collectors.toList());
    }

    @Override
    public void deleteBookstore(Long id) {
        Bookstore bookstore = findBookstoreById(id);
        bookstore.removeBookstore();
        bookstoreRepository.delete(findBookstoreById(id));
    }

    @Override
    public CreateBookstoreDTO getBookstoreToEdit(Long id) {
        return BookstoreMapper.map2(findBookstoreById(id));
    }

    @Override
    public void editBookstore(CreateBookstoreDTO bookstore, Long id) {
        findByName(bookstore.getName());
        Bookstore oldBookstore = findBookstoreById(id);
        oldBookstore.setName(bookstore.getName());
        oldBookstore.setLink(bookstore.getLink());
        bookstoreRepository.save(oldBookstore);
    }

    private void findByName(String name) {
        bookstoreRepository.findByName(name).ifPresent((b) -> {
            throw new DuplicateBookstoreException("Bookstore with the name " + name + " already exists");
        });
    }

    private Bookstore findBookstoreById(Long id) {
        return bookstoreRepository.findById(id).orElseThrow(() ->
            new DuplicateBookstoreException("Bookstore with the id " + id + " does not exists")
        );
    }


    public class BookstoreMapper {
        public static BookstoreDTO map(Bookstore bookstore) {
            return new BookstoreDTO(bookstore.getId(), bookstore.getName(), bookstore.getLink());
        }

        public static Bookstore map(CreateBookstoreDTO bookstore) {
            return new Bookstore(bookstore.getName(), bookstore.getLink());
        }

        public static CreateBookstoreDTO map2(Bookstore bookstore) {
            return new CreateBookstoreDTO(bookstore.getName(), bookstore.getLink());
        }

    }
}
