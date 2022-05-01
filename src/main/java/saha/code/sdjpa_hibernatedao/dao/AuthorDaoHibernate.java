package saha.code.sdjpa_hibernatedao.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import saha.code.sdjpa_hibernatedao.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Component(value = "authorDaoHibernate")
public class AuthorDaoHibernate implements AuthorDao {

    private final EntityManagerFactory emf;

    public AuthorDaoHibernate(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Author> findAllAuthorsByLastName(String lastName, Pageable pageable) {
        EntityManager em = getEntityManager();
        try {
            String sql = "SELECT b FROM Author b WHERE b.lastName = :lastName ";
            if(pageable.getSort().getOrderFor("firstname") != null ){
                sql += "order by b.firstName " +pageable.getSort().getOrderFor("firstname").getDirection().name();
            }
            TypedQuery<Author> query = em.createQuery(sql,Author.class);
            query.setParameter("lastName", lastName);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return new Author(); //method stub
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return new Author(); //method stub
    }

    @Override
    public Author findAuthorByNameCriteria(String firstName, String lastName) {
        return new Author(); //method stub
    }

    @Override
    public Author findAuthorByNameNative(String firstName, String lastName) {
        return new Author(); //method stub
    }

    @Override
    public Author getById(Long id) {
        return new Author(); //method stub
    }

    @Override
    public void deleteAuthorById(Long id) {

    }

    @Override
    public Author updateAuthor(Author author) {
        return new Author(); //method stub
    }

    @Override
    public List<Author> listAuthorByLastNameLike(String lastName) {
        return new ArrayList<>(); //method stub
    }

    @Override
    public List<Author> findAll() {
        return new ArrayList<>(); //method stub
    }

    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}
