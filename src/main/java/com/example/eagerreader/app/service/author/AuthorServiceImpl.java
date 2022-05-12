package com.example.eagerreader.app.service.author;

import com.example.eagerreader.app.domain.entity.Author;
import com.example.eagerreader.app.domain.repository.AuthorRepository;
import com.example.eagerreader.app.dto.author.AuthorDTO;
import com.example.eagerreader.app.dto.author.CreateAuthorDTO;
import com.example.eagerreader.app.dto.author.EditAuthorDTO;
import com.example.eagerreader.app.exception.authorException.author.AuthorNotFoundException;
import com.example.eagerreader.app.exception.authorException.author.DuplicateAuthorException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

    @Override
    public void addAuthor(CreateAuthorDTO author) throws DuplicateAuthorException {
        Author newAuthor = AuthorMapper.map(author);
        findAuthorByFirstnameAndLastname(author.getFirstname(), author.getLastname());
        authorRepository.save(newAuthor);
    }

    @Override
    public EditAuthorDTO getAuthorToEdit(Long id) {
        Author author = findAuthorById(id);
        return AuthorMapper.map(author);
    }

    @Override
    public void editAuthor(EditAuthorDTO author, Long id) throws DuplicateAuthorException {
        Author oldAuthor = findAuthorById(id);
        boolean theSame = author.getFirstname().equals(oldAuthor.getFirstname()) && author.getLastname().equals(oldAuthor.getLastname());
        log.debug("Old name anf lastname are the same as new: " + theSame);
        if (!theSame) {
            log.debug("Checking if not duplicate...");
//          find duplicate
            findAuthorByFirstnameAndLastname(author.getFirstname(), author.getLastname());
        }
        log.debug("Saving publisher");
        oldAuthor.setFirstname(author.getFirstname());
        oldAuthor.setLastname(author.getLastname());
        oldAuthor.setInfo(author.getInfo());
        authorRepository.save(oldAuthor);
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
       return authorRepository.findAll().stream().map(a -> AuthorMapper.map2(a)).collect(Collectors.toList());
    }

    @Override
    public void deleteAuthor(Long id) {
        Author author = findAuthorById(id);
        authorRepository.delete(author);
    }

    private Author findAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(
                () -> new AuthorNotFoundException("Author with the id " + id + " does not exists")
        );
    }

    private void findAuthorByFirstnameAndLastname(String firstname, String lastname) {
        authorRepository.findAuthorByFirstnameAndLastname(firstname, lastname).ifPresent(
                author -> {
                    throw new DuplicateAuthorException(author.getFirstname() + " " + author.getLastname() + " already exists");
                }
        );
    }


    private class AuthorMapper {

        private static Author map(CreateAuthorDTO author) {
            return new Author(author.getFirstname(), author.getLastname(), author.getInfo());
        }

        private static EditAuthorDTO map(Author author) {
            return new EditAuthorDTO(author.getFirstname(), author.getLastname(), author.getInfo());
        }

        private static AuthorDTO map2(Author author){
            return new AuthorDTO(author.getId(), author.getFirstname(),author.getLastname(),author.getInfo());
        }
    }
}
