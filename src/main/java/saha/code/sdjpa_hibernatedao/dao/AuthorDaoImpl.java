package saha.code.sdjpa_hibernatedao.dao;

import org.springframework.stereotype.Component;
import saha.code.sdjpa_hibernatedao.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Component(value = "authorDaoImpl")
public class AuthorDaoImpl implements AuthorDao {

    private final EntityManagerFactory emf;

    public AuthorDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        EntityManager em = getEntityManager();
//        em.joinTransaction();
        try {
            em.getTransaction().begin();
            em.persist(author); // object not yet persisted here
            em.flush(); // forces hibernate to flush transaction to the DB
            em.getTransaction().commit();
            return author;
        } finally {
            em.close();
        }
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        EntityManager em = getEntityManager();
        try {
//            TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a WHERE " +
//                    "a.firstName = :first_name and a.lastName = :last_name", Author.class);
            TypedQuery<Author> query = em.createNamedQuery("find_by_name",Author.class);
            query.setParameter("first_name", firstName);
            query.setParameter("last_name", lastName);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    @Override
    public Author findAuthorByNameCriteria(String firstName, String lastName) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Author>criteriaQuery = criteriaBuilder.createQuery(Author.class);
            Root<Author> root = criteriaQuery.from(Author.class);
            ParameterExpression<String > firstNameParam = criteriaBuilder.parameter(String.class);
            ParameterExpression<String > lastNameParam = criteriaBuilder.parameter(String.class);

            // Bind the parameters for the objects
            Predicate firstNamePred = criteriaBuilder.equal(root.get("firstName"),firstNameParam);
            Predicate lastNamePred = criteriaBuilder.equal(root.get("lastName"),lastNameParam);

            criteriaQuery.select(root).where(criteriaBuilder.and(firstNamePred, lastNamePred));
            TypedQuery<Author> query = em.createQuery(criteriaQuery);
            // bind the arguments of the method to the query
            query.setParameter(firstNameParam,firstName);
            query.setParameter(lastNameParam,lastName);
            return query.getSingleResult();
        }finally {
            em.close();
        }
    }
    @Override
    public Author findAuthorByNameNative(String firstName, String lastName) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery("SELECT * FROM author a WHERE a.first_name = ? and " +
                    "a.last_name = ?");
            query.setParameter(1,firstName);
            query.setParameter(2, lastName);
        return (Author) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Author getById(Long id) {
        EntityManager em = getEntityManager();
        Author author = getEntityManager().find(Author.class,id);
        em.close();
        return author;
    }

    @Override
    public void deleteAuthorById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
       Author author =  em.find(Author.class,id);
       em.remove(author);
       em.getTransaction().commit();
       em.close();
    }

    @Override
    public Author updateAuthor(Author author) {
        EntityManager em = getEntityManager();
        try {
            em.joinTransaction();
            em.merge(author);
            em.flush();
            em.clear();
            return em.find(Author.class, author.getId());
        } finally {
            em.close();
        }
    }

    // wild card matching
    @Override
    public List<Author> listAuthorByLastNameLike(String lastName) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT a FROM Author a WHERE a.lastName LIKE :last_name");
            query.setParameter("last_name",lastName + "%");// % 0, 1 or many matching characters for lastname
           return  query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Author> findAll() {
        EntityManager em = getEntityManager();
        try{
            TypedQuery<Author> query = em.createNamedQuery("author_find_all", Author.class);
        return query.getResultList();
        } finally {
            em.close();
        }
    }



    //Factory Design pattern
    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}
