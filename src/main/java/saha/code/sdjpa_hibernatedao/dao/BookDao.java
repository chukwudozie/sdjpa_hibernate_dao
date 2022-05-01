package saha.code.sdjpa_hibernatedao.dao;

import org.springframework.data.domain.Pageable;
import saha.code.sdjpa_hibernatedao.domain.Book;

import java.util.List;

public interface BookDao {
    Book saveNewBook(Book book);

    void deleteBookById(Long id);

    Book getById(Long id);

    void updateBook(Book saved);

    Book findBookByTitle(String clean_code);

    Book findBookByTitleCriteria(String title);

    Book findBookByTitleNative(String title);
    Book findByISBN(String isbn);

    List<Book> findAll();

    List<Book>findAllBooksSortByTitle(Pageable pageable);
    List<Book> findAllBooks(Pageable pageable);
    List<Book> findAllBooks(int pageSize, int offset);

}
