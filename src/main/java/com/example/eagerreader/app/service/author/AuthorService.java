package com.example.eagerreader.app.service.author;

import com.example.eagerreader.app.dto.author.AuthorDTO;
import com.example.eagerreader.app.dto.author.CreateAuthorDTO;
import com.example.eagerreader.app.dto.author.EditAuthorDTO;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface AuthorService {

    void addAuthor(CreateAuthorDTO author);
    EditAuthorDTO getAuthorToEdit(Long id);
    void editAuthor(EditAuthorDTO author, Long id);
    List<AuthorDTO> getAllAuthors();
    void deleteAuthor(Long id);


}
