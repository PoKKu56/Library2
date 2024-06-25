package LocalLibrary.dao;

import LocalLibrary.models.Book;
import LocalLibrary.models.Person;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {

    private final EntityManager entityManager;

    @Autowired
    public PersonDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Book> getBooksByPersonId(int person_id) {
        Session session = entityManager.unwrap(Session.class);

        return session.createQuery("select b from Book b WHERE b.owner.id = :person_id", Book.class)
                .setParameter("person_id", person_id).getResultList();
    }


}
