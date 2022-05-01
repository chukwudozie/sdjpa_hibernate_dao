package saha.code.sdjpa_hibernatedao.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import saha.code.sdjpa_hibernatedao.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackages = {"saha.code.sdjpa_hibernatedao.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoHibernateTest {

    @Autowired
    @Qualifier(value = "bookDaoHibernate")
    private BookDao bookDao;


    @Test
    void testFindAllBooks_Page1(){
        List<Book> firstTenBooks = bookDao.findAllBooks(PageRequest.of(0,10));
        firstTenBooks.forEach(book -> {
            System.out.printf("Book title: %s, Book Publisher : %s \n",book.getTitle(), book.getPublisher());
        });
        assertThat(firstTenBooks).isNotNull();
        assertThat(firstTenBooks.size()).isEqualTo(10);
    }

    @Test
    void testFindAllBooksPage1_SortByTitle(){
        List<Book>sortedBooks = bookDao.findAllBooksSortByTitle(PageRequest.of(0,10,
                Sort.by(Sort.Order.desc("title"))));
        sortedBooks.forEach(book -> {
            System.out.printf("Title : %s   Publisher: %s  ID : %d \n",
                    book.getTitle(),book.getPublisher(),book.getId());
        });
        assertThat(sortedBooks).isNotNull();
        assertThat(sortedBooks.size()).isEqualTo(10);
    }
}
