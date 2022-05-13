package com.example.eagerreader.app.service.book;


import com.example.eagerreader.app.domain.entity.Author;
import com.example.eagerreader.app.domain.entity.Book;
import com.example.eagerreader.app.domain.entity.Publisher;
import com.example.eagerreader.app.domain.repository.AuthorRepository;
import com.example.eagerreader.app.domain.repository.BookRepository;
import com.example.eagerreader.app.domain.repository.PublisherRepository;
import com.example.eagerreader.app.dto.author.AuthorDTO;
import com.example.eagerreader.app.dto.book.BookDTO;
import com.example.eagerreader.app.dto.book.BookDetailsDTO;
import com.example.eagerreader.app.dto.book.CreateBookDTO;
import com.example.eagerreader.app.dto.book.EditBookDTO;
import com.example.eagerreader.app.exception.authorException.author.AuthorNotFoundException;
import com.example.eagerreader.app.exception.bookException.BookNotFoundException;
import com.example.eagerreader.app.exception.bookException.DuplicateBookException;
import com.example.eagerreader.app.service.author.AuthorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;


    @Override
    public void addBook(CreateBookDTO book) {
        findBookByName(book.getTitle());
        Publisher publisher = publisherRepository.findByName(book.getPublisher()).orElseThrow();
        List<String> items = Arrays.asList(book.getAuthor().split("\\s*,\\s*"));
        List<Author> authors = new ArrayList<>();
        items.stream().forEach(a -> {
            authors.add(authorRepository.findAuthorByFirstnameAndLastname(a.split(" ")[0], a.split(" ")[1]).orElseThrow(() -> new AuthorNotFoundException("Author not found")));
        });
        Book newBook = BookMapper.map(book, publisher, authors);
        bookRepository.save(newBook);
    }

    @Override
    public EditBookDTO getBookToEdit(Long id) {
        Book book = findBookById(id);
        return null;
    }

    @Override
    public void editBook(EditBookDTO book) {

    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(
                b -> BookMapper.map(b)
        ).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getAllBooksByFilter(String filter) {
                if(filter.equals("highest_ranking")) return bookRepository.findByOrderByReviewScoreAsc().stream().map(
                b -> BookMapper.map(b)
        ).collect(Collectors.toList());

        if(filter.equals("lowest_ranking")) return bookRepository.findByOrderByReviewScoreDesc().stream().map(
                b -> BookMapper.map(b)
        ).collect(Collectors.toList());

        throw new RuntimeException("Filter "+filter+" not found");
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.delete(findBookById(id));
    }

    private void findBookByName(String title) {
        bookRepository.findByTitle(title).ifPresent(
                publisher -> {
                    throw new DuplicateBookException("Book with the title " + title + " already exists");
                }
        );
    }

    private Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new BookNotFoundException("Book with the id " + id + " does not exists")
        );
    }

    private static class BookMapper {
        private static Book map(CreateBookDTO book, Publisher publisher, List<Author> authors) {
            return new Book(book.getTitle(), book.getDescription(), Long.parseLong(book.getPublished()), null, publisher, authors);
        }

        private static EditBookDTO map2(Book book) {
            List<String> authors = book.getAuthors().stream().map(a -> a.getFirstname() + " " + a.getLastname()).collect(Collectors.toList());
            List<String> bookstores = book.getStores().stream().map(a -> a.getName()).collect(Collectors.toList());
            return new EditBookDTO(book.getTitle(), book.getDescription(), book.getPublished().toString(), book.getPublisher().getName(), String.join(",", authors), String.join(",", bookstores));
        }

        private static Book map(EditBookDTO book, Publisher publisher, List<Author> authors) {
            return new Book(book.getTitle(), book.getDescription(), Long.parseLong(book.getPublished()), publisher, authors);
        }

        private static BookDTO map(Book book) {
            List<AuthorDTO> authors = new ArrayList<>();
            authors = book.getAuthors().stream().map(a -> AuthorServiceImpl.AuthorMapper.map2(a)).collect(Collectors.toList());
            return new BookDTO(book.getTitle(), book.getId(), authors, book.getReviewScore());
        }


    }
}
