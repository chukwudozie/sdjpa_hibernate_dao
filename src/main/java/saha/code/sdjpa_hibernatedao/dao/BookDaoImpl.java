package saha.code.sdjpa_hibernatedao.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import saha.code.sdjpa_hibernatedao.domain.Author;
import saha.code.sdjpa_hibernatedao.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Component(value = "bookDaoImpl")
public class BookDaoImpl implements BookDao{
    private final EntityManagerFactory emf;

    public BookDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Book saveNewBook(Book book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(book);
        em.flush();
        em.getTransaction().commit();
        em.close();
        return book;
    }

    @Override
    public void deleteBookById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Book book = em.find(Book.class, id);
        em.remove(book);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Book getById(Long id) {
        EntityManager em = getEntityManager();
        Book book = getEntityManager().find(Book.class,id);
//        em.close();
        return book;
    }

    @Override
    public void updateBook(Book saved) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(saved);
        em.flush();
        em.clear();
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Book findBookByTitle(String clean_code) {

        EntityManager em = getEntityManager();
        try {
//            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE " +
//                    "b.title = :title", Book.class);
            TypedQuery<Book> query = em.createNamedQuery("find_by_title",Book.class);
            query.setParameter("title", clean_code);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Book findByISBN(String isbn) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn");
            query.setParameter("isbn",isbn);
             return  (Book) query.getSingleResult(); // it's cleaner to use TypedQuery and avoid type casting

        } finally {
            em.close();
        }
    }

    @Override
    public List<Book> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Book> query = em.createNamedQuery("find_all_books",Book.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Book> findAllBooksSortByTitle(Pageable pageable) {
        return new ArrayList<>();
    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        return new ArrayList<>();
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        return new ArrayList<>();
    }

    @Override
    public Book findBookByTitleCriteria(String title) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
            Root<Book> root = criteriaQuery.from(Book.class);
            ParameterExpression<String> titleParam = criteriaBuilder.parameter(String.class);
            Predicate titlePredicate = criteriaBuilder.equal(root.get("title"),titleParam);
            criteriaQuery.select(root).where(titlePredicate);
            TypedQuery<Book> query = em.createQuery(criteriaQuery);
            query.setParameter(titleParam, title);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Book findBookByTitleNative(String title) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery("SELECT * FROM book  WHERE title = :title",Book.class);
            query.setParameter("title", title);
            return (Book) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}
