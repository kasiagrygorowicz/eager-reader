package com.example.eagerreader.app.service.book;


import com.example.eagerreader.app.dto.book.BookDetailsDTO;
import com.example.eagerreader.app.dto.bookstore.BookstoreDTO;
import com.example.eagerreader.app.dto.publisher.PublisherDTO;
import com.example.eagerreader.app.dto.review.ReviewDTO;
import com.example.eagerreader.app.entity.*;
import com.example.eagerreader.app.exception.authorException.publisher.PublisherNotFoundException;
import com.example.eagerreader.app.repository.*;
import com.example.eagerreader.app.dto.author.AuthorDTO;
import com.example.eagerreader.app.dto.book.BookDTO;
import com.example.eagerreader.app.dto.book.CreateBookDTO;
import com.example.eagerreader.app.dto.book.EditBookDTO;
import com.example.eagerreader.app.exception.authorException.author.AuthorNotFoundException;
import com.example.eagerreader.app.exception.bookException.BookNotFoundException;
import com.example.eagerreader.app.exception.bookException.DuplicateBookException;
import com.example.eagerreader.app.service.author.AuthorServiceImpl;
import com.example.eagerreader.app.service.bookstore.BookstoreServiceImpl;
import com.example.eagerreader.app.service.publisher.PublisherServiceImpl;
import com.example.eagerreader.app.service.review.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final BookstoreRepository bookstoreRepository;
    private final ReviewRepository reviewRepository;


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
        return BookMapper.map2(book);
    }

    @Override
    public void editBook(EditBookDTO book,Long id) {
        Book b = bookRepository.getById(id);
        b.setTitle(book.getTitle());
        b.setDescription(book.getDescription());
        b.setPublished(Long.parseLong(book.getPublished()));
        b.setPublisher(publisherRepository.findByName(book.getPublisher()).orElseThrow());
        List<String> items = Arrays.asList(book.getAuthor().split("\\s*,\\s*"));
        List<Author> authors = new ArrayList<>();
        items.stream().forEach(a -> {
            authors.add(authorRepository.findAuthorByFirstnameAndLastname(a.split(" ")[0], a.split(" ")[1]).orElseThrow(() -> new AuthorNotFoundException("Author not found")));
        });
        b.setAuthors(authors);

        List<String> pubs = Arrays.asList(book.getBookstores().split("\\s*,\\s*"));
        List<Bookstore> bookstores = new ArrayList<>();
        pubs.stream().forEach(a -> {
            System.out.println(a);
            bookstores.add(bookstoreRepository.findByName(a).orElseThrow(() -> new RuntimeException("Bookstore not found")));
        });
        b.setStores(bookstores);
        bookRepository.save(b);

    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(
                b -> BookMapper.map(b)
        ).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getAllBooksByFilter(String filter) {
                if(filter.equals("highest_rating")) return bookRepository.findByOrderByReviewScoreDesc().stream().map(
                b -> BookMapper.map(b)
        ).collect(Collectors.toList());

        if(filter.equals("lowest_rating")) return bookRepository.findByOrderByReviewScoreAsc().stream().map(
                b -> BookMapper.map(b)
        ).collect(Collectors.toList());

        throw new RuntimeException("Filter "+filter+" not found");
    }

    @Override
    public void deleteBook(Long id) {
        Book book =findBookById(id);
        List<Review> reviews = book.getReviews();
        for(Review r : reviews)
            reviewRepository.delete(r);
        book.deleteFavorites();
        bookRepository.delete(book);
    }

    @Override
    public BookDetailsDTO getBookDetails(Long id) {
        Book book = findBookById(id);
        return BookMapper.map3(book);
    }

    @Override
    public List<ReviewDTO> getReviews(Long id) {
        return findBookById(id).getReviews().stream().map(ReviewServiceImpl.ReviewMapper::map).collect(Collectors.toList());
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

    public static class BookMapper {



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

        public static BookDTO map(Book book) {
            List<AuthorDTO> authors = new ArrayList<>();
            authors = book.getAuthors().stream().map(a -> AuthorServiceImpl.AuthorMapper.map2(a)).collect(Collectors.toList());
            return new BookDTO(book.getTitle(), book.getId(), authors, book.getReviewScore());
        }

        private static BookDetailsDTO map3(Book book){
            Long rating = book.getReviews().size()!=0 ? book.getReviewScore()/book.getReviews().size() :null;
            List<AuthorDTO> authors = book.getAuthors().stream().map(a -> AuthorServiceImpl.AuthorMapper.map2(a)).collect(Collectors.toList());
            List<BookstoreDTO> stores = book.getStores().stream().map(b-> BookstoreServiceImpl.BookstoreMapper.map(b)).collect(Collectors.toList());
            boolean isFavorite =false;
            try {
                User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                List<Book> list = user.getFavorites();
                for (Book f : list) {
                    if (f.getId() == book.getId()) {
                        isFavorite = true;
                        break;
                    }
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
                }

            return new BookDetailsDTO(book.getTitle(), book.getDescription(), book.getPublished().toString(), PublisherServiceImpl.PublisherMapper.map(book.getPublisher()),authors,stores,isFavorite,rating);
        }


    }
}
