package com.example.eagerreader.app.service.user;

import com.example.eagerreader.app.dto.CreateUserDto;
import com.example.eagerreader.app.dto.book.BookDTO;
import com.example.eagerreader.app.entity.Book;
import com.example.eagerreader.app.entity.Role;
import com.example.eagerreader.app.entity.User;
import com.example.eagerreader.app.repository.BookRepository;
import com.example.eagerreader.app.repository.UserRepository;
import com.example.eagerreader.app.service.book.BookServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final BookRepository bookRepository;

    @Override
    public void addUser(CreateUserDto user) {
        log.info("New user email :{} password {}", user.getEmail(), user.getPassword());
        User newUser = Mapper.map(user);
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
    }

    @Override
    public void addToFavorites(Long id) {
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Book book = bookRepository.getById(id);
        user.addBook(book);
        userRepository.save(user);

    }

    @Override
    public void deleteFromFavorites(Long id) {
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Book book = bookRepository.getById(id);
//        book.deleteFan(user);
        user.deleteBook(book);
        userRepository.save(user);


    }

    @Override
    public List<BookDTO> getFavorites() {
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Book> f =user.getFavorites();
        return f.stream().map(BookServiceImpl.BookMapper::map).collect(Collectors.toList());
    }

    private class Mapper {
        private static User map(CreateUserDto user) {
            return new User(
                    user.getEmail(),
                    user.getPassword(),
                    Role.user
            );
        }
    }
}
