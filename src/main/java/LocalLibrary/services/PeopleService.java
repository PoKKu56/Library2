package LocalLibrary.services;

import LocalLibrary.models.Book;
import LocalLibrary.models.Person;
import LocalLibrary.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    public List<Person> getAllPeople(){
        return peopleRepository.findAll();
    }

    public Person getPeopleById(int id){
        Optional<Person> foundPerson = peopleRepository.findById(id);

        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public List<Book> getAllBooksByPersonId(int id){

        Optional<Person> foundPerson = peopleRepository.findById(id);

        if(foundPerson.isPresent()){
            Hibernate.initialize(foundPerson.get().getBooks());

            foundPerson.get().getBooks().forEach(book -> {
                long diffInMillies = Math.abs(book.getDate_of_take().getTime() - new Date().getTime());
                // 864000000 милисекунд = 10 суток
                if (diffInMillies > 864000000)
                    book.setExpired(true); // книга просрочена
            });
            return foundPerson.get().getBooks();
        }
        return Collections.emptyList();
    }
}
