package saha.code.sdjpa_hibernatedao.dao;


import org.springframework.data.domain.Pageable;
import saha.code.sdjpa_hibernatedao.domain.Author;

import java.util.List;

public interface AuthorDao {

    List<Author> findAllAuthorsByLastName(String lastName, Pageable pageable);
    Author saveNewAuthor(Author author);
    Author findAuthorByName(String firstName, String lastName);
    Author findAuthorByNameCriteria(String firstName, String lastName);
    Author findAuthorByNameNative(String firstName, String lastName);
    Author getById(Long id);
    void deleteAuthorById(Long id);
    Author updateAuthor(Author author);
    List<Author>listAuthorByLastNameLike(String lastName);
    List<Author> findAll();

}
