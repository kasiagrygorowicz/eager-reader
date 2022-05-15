package com.example.eagerreader.app.service.user;

import com.example.eagerreader.app.dto.CreateUserDto;
import com.example.eagerreader.app.dto.book.BookDTO;

import java.util.List;


public interface UserService {
    void addUser(CreateUserDto user);
    void addToFavorites(Long id);
    void deleteFromFavorites(Long id);
    List<BookDTO> getFavorites();
}
