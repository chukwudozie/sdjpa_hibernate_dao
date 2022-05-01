package saha.code.sdjpa_hibernatedao.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import saha.code.sdjpa_hibernatedao.domain.Author;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@ComponentScan(basePackages = {"saha.code.sdjpa_hibernatedao.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoHibernateTest {

    @Autowired
    @Qualifier(value = "authorDaoHibernate")
    private AuthorDao authorDao;

    @Test
    void findAllByLastName(){
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith",
            PageRequest.of(0, 10));
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(10);
    }

    @Test
    void findAllAuthorsByLastNameSortLastNameDesc() {
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith",
                PageRequest.of(0, 10, Sort.by(Sort.Order.desc("firstname"))));
        authors.forEach(author -> {
            System.out.printf("Name: %s, ID: %d \n",author.getFirstName(),author.getId());
        });
        Assertions.assertThat(authors).isNotNull();
        Assertions.assertThat(authors.size()).isEqualTo(10);
        Assertions.assertThat(authors.get(0).getFirstName()).isEqualTo("Yugal");
    }

    @Test
    void findAllAuthorsByLastNameSortLastNameAsc() {
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith",
                PageRequest.of(0, 10, Sort.by(Sort.Order.asc("firstname"))));

        Assertions.assertThat(authors).isNotNull();
        Assertions.assertThat(authors.size()).isEqualTo(10);
        Assertions.assertThat(authors.get(0).getFirstName()).isEqualTo("Ahmed");
    }

    @Test
    void findAllAuthorsByLastNameAllRecs() {
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith", PageRequest.of(0, 100));

        Assertions.assertThat(authors).isNotNull();
        Assertions.assertThat(authors.size()).isEqualTo(40);
    }

}
