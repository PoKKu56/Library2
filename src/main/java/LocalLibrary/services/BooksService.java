package LocalLibrary.services;

import LocalLibrary.models.Book;
import LocalLibrary.models.Person;
import LocalLibrary.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    public List<Book> showAllBooks() {
        return booksRepository.findAll();
    }

    public Optional<Book> findById(int id) {
        return booksRepository.findById(id);
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
        Optional<Book> book = findById(id);
        if (book.isPresent()) {
            book.get().setOwner(null);
            book.get().setDateOfTake(null);
            booksRepository.save(book.get());
        }
    }

    @Transactional
    public void assignBook(int id, Person person){
        Optional<Book> assignedBook = findById(id);
        if (assignedBook.isPresent()) {
            assignedBook.get().setOwner(person);
            assignedBook.get().setDateOfTake(new Date());
            booksRepository.save(assignedBook.get());
        }
    }

    public Person getOwner(int id){
        Optional<Book> book = findById(id);
        if (book.isPresent()) {
            return book.get().getOwner();
        }
        return null;
    }

    public List<Book> searchBooksByTitle(String startsWith){
        return booksRepository.findByTitleStartingWith(startsWith);
    }

}
