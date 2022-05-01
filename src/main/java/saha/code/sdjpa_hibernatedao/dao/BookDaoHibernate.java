package saha.code.sdjpa_hibernatedao.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import saha.code.sdjpa_hibernatedao.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Component(value = "bookDaoHibernate")
public class BookDaoHibernate implements BookDao {

    private final EntityManagerFactory emf;

    public BookDaoHibernate(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Book saveNewBook(Book book) {
        return new Book();
    }

    @Override
    public void deleteBookById(Long id) {
    }

    @Override
    public Book getById(Long id) {
        return new Book();
    }

    @Override
    public void updateBook(Book saved) {

    }

    @Override
    public Book findBookByTitle(String clean_code) {
        return new Book();
    }

    @Override
    public Book findBookByTitleCriteria(String title) {
        return new Book();
    }

    @Override
    public Book findBookByTitleNative(String title) {
        return new Book();
    }

    @Override
    public Book findByISBN(String isbn) {
        return new Book();
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>();
    }

    @Override
    public List<Book> findAllBooksSortByTitle(Pageable pageable) {
        EntityManager em = getEntityManager();

        try {
            String hql = "SELECT b FROM Book b order by b.title "
                    +pageable.getSort().getOrderFor("title").getDirection().name();
            TypedQuery<Book> query = em.createQuery(hql, Book.class);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));// for setting the offset or page Number
            query.setMaxResults(pageable.getPageSize());
            return query.getResultList();
        }finally {
            em.close();
        }
    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {

        EntityManager em = getEntityManager();

        try {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));// for setting the offset or page Number
            query.setMaxResults(pageable.getPageSize());
            return query.getResultList();
        }finally {
            em.close();
        }
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        return null;
    }

    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}
