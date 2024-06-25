package LocalLibrary.services;

import LocalLibrary.models.Book;
import LocalLibrary.models.Person;
import LocalLibrary.repositories.BooksRepository;
import LocalLibrary.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> showAllBooks(int page, int booksPerPage) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
    }

    public Book findById(int id) {
        Optional<Book> book = booksRepository.findById(id);

        return book.orElse(null);
    }

    @Transactional
    public Book save(Book book) {
        return booksRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void releaseBook(Integer id){
        Book book = findById(id);
        book.setOwner(null);
    }

    @Transactional
    public void assignBook(int id, Person person){
        Book assignedBook = findById(id);
        assignedBook.setOwner(person);
        booksRepository.save(assignedBook);
    }

    public Person getOwner(int id){
        return this.findById(id).getOwner();
    }

}
