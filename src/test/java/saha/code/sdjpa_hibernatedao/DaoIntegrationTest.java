package saha.code.sdjpa_hibernatedao;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import saha.code.sdjpa_hibernatedao.dao.AuthorDao;
import saha.code.sdjpa_hibernatedao.dao.BookDao;
import saha.code.sdjpa_hibernatedao.domain.Author;
import saha.code.sdjpa_hibernatedao.domain.Book;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ComponentScan(basePackages = {"saha.code.sdjpa_hibernatedao.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DaoIntegrationTest {

        @Autowired
        AuthorDao authorDao;

    @Autowired
    BookDao bookDao;

    @Test
    void testDeleteBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        Book saved = bookDao.saveNewBook(book);

        bookDao.deleteBookById(saved.getId());

        Book deleted = bookDao.getById(saved.getId());

        assertThat(deleted).isNull();
    }

    @Test
    void updateBookTest() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");

        Author author = new Author();
        author.setId(3L);

        book.setAuthorId(author.getId());
        Book saved = bookDao.saveNewBook(book);

        saved.setTitle("New Book");
        bookDao.updateBook(saved);

        Book fetched = bookDao.getById(saved.getId());

        assertThat(fetched.getTitle()).isEqualTo("New Book");
    }

    @Test
    void testSaveBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");

        Author author = new Author();
        author.setId(3L);

        book.setAuthorId(author.getId());
        Book saved = bookDao.saveNewBook(book);

        assertThat(saved).isNotNull();
    }

    @Test
    void testGetBookByName() {
        Book book = bookDao.findBookByTitle("Clean Code");
        assertThat(book).isNotNull();
    }
    @Test
    void testGetBookByNameCriteria() {
        Book book = bookDao.findBookByTitleCriteria("Clean Code");
        assertThat(book).isNotNull();
    }

    @Test
    void testGetBookByNameNative() {
        Book book = bookDao.findBookByTitleNative("Clean Code");
        assertThat(book).isNotNull();
    }


    @Test
    void testGetBook() {
        Book book = bookDao.getById(3L);
        assertThat(book.getId()).isNotNull();
    }

    @Test
    public void testFindBookByISBN(){
        Book book = new Book();
        book.setIsbn("1234"+ RandomString.make());
        book.setTitle("ISBN TEST");

        Book savedBook = bookDao.saveNewBook(book);
        Book fetched = bookDao.findByISBN(book.getIsbn());
        assertThat(fetched).isNotNull();
    }

    @Test
    public void testFindAllBooks(){
        List<Book> books = bookDao.findAll();
        books.forEach(book -> {
            System.out.println(book.getTitle()+ " : "+book.getPublisher());
        });
        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThan(0);
    }

    @Test
    public void testFindAllAuthors(){
        List<Author>  authors = authorDao.findAll();
        authors.forEach(author -> {
            System.out.println(author.getFirstName()+ " "+author.getLastName());
        });
         assertThat(authors).isNotNull();
         assertThat(authors.size()).isGreaterThan(0);

    }

        @Test
        void testDeleteAuthor() {
            Author author = new Author();
            author.setFirstName("john");
            author.setLastName("t");

            Author saved = authorDao.saveNewAuthor(author);

            authorDao.deleteAuthorById(saved.getId());
                Author deleted = authorDao.getById(saved.getId());
                assertThat(deleted).isNull();


        }

        @Test
        void testUpdateAuthor() {
            Author author = new Author();
            author.setFirstName("john");
            author.setLastName("t");

            Author saved = authorDao.saveNewAuthor(author);

            saved.setLastName("Thompson updated");
            Author updated = authorDao.updateAuthor(saved);

            assertThat(updated.getLastName()).isEqualTo("Thompson updated");
        }

        @Test
        void testSaveAuthor() {
            Author author = new Author();
            author.setFirstName("John");
            author.setLastName("Thompson");
            Author saved = authorDao.saveNewAuthor(author);
            System.out.println("saved author ID : "+saved.getId());
            System.out.println(author);
            System.out.println(saved);
            assertThat(saved).isNotNull();
            assertThat(saved.getId()).isNotNull();
        }

        @Test
        void testGetAuthorByName() {
            Author author = authorDao.findAuthorByName("Craig", "Walls");
            assertThat(author).isNotNull();
        }

        @Test
        public void testGetAuthorByNameCriteria(){
        Author author = authorDao.findAuthorByNameNative("Craig","Walls");
        assertThat(author).isNotNull();
        }

    @Test
    public void testGetAuthorByNameNative(){
        Author author = authorDao.findAuthorByNameCriteria("Craig","Walls");
        assertThat(author).isNotNull();
    }

        @Test
        void testGetAuthor() {
            Author author = authorDao.getById(1L);
            assertThat(author).isNotNull();
        }


        @Test
    public void testListAuthorByLastNameLike(){
            List<Author> authors = authorDao.listAuthorByLastNameLike("Wall");
            assertThat(authors).isNotNull();
            assertThat(authors.size()).isGreaterThan(0);
        }
    }

