package LocalLibrary.controllers;

import LocalLibrary.dao.BookDAO;
import LocalLibrary.models.Book;
import LocalLibrary.models.Person;
import LocalLibrary.services.BooksService;
import LocalLibrary.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;
    private final BookDAO bookDAO;

    @Autowired
    public BooksController(BooksService booksService, BookDAO bookDAO, PeopleService peopleService) {
        this.booksService = booksService;
        this.bookDAO = bookDAO;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String index(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "books_per_page", defaultValue = "2") int booksPerPage) {
        model.addAttribute("books", booksService.showAllBooks(page, booksPerPage));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String getBook(Model model, @PathVariable("id") int id, @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.findById(id));

        Person owner = booksService.getOwner(id);

        if (owner != null) {
            model.addAttribute("owner", owner);
        }
        else{
            model.addAttribute("people", peopleService.getAllPeople());
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") Book book) {
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute("book") Book updatedbook) {
        booksService.update(id, updatedbook);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        booksService.releaseBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id, @ModelAttribute("people") Person selectedPerson) {
        booksService.assignBook(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String search() {
        return "books/search";
    }

    @PostMapping("/search")
    public String searchBook(@RequestParam(value = "title") String titleStartWith, Model model) {
        List<Book> books = booksService.searchBooksByTitle(titleStartWith);
        model.addAttribute("books", books);
        return "books/search";
    }



}
