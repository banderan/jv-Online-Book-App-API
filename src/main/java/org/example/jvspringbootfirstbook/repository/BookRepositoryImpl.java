package org.example.jvspringbootfirstbook.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.jvspringbootfirstbook.exception.DataProcessingException;
import org.example.jvspringbootfirstbook.model.Book;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {
    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Book save(Book book) {
        EntityManager session = null;
        EntityTransaction transaction = null;
        try {
            session = entityManagerFactory.createEntityManager();
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(book);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Unable to save book", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        try (EntityManager session = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
            Root<Book> root = query.from(Book.class);

            query.select(root);
            return session.createQuery(query).getResultList();
        } catch (HibernateException e) {
            throw new DataProcessingException("Unable to find all books", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (EntityManager session = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
            Root<Book> root = query.from(Book.class);

            Predicate idPredicate = criteriaBuilder
                    .equal(root.get("id"), id);
            query.where(idPredicate);
            Book singleResult = session.createQuery(query)
                    .getSingleResult();
            return singleResult == null ? Optional.empty()
                    : Optional.of(singleResult);
        }
    }
}
