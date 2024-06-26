package LocalLibrary.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 2, max = 100, message = "Название книги должно быть от 2 до 100 символов длиной")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Автор не должен быть пустым")
    @Size(min = 2, max = 100, message = "Имя автора должно быть от 2 до 100 символов длиной")
    @Column(name = "author")
    private String author;

    @Min(value = 1500, message = "Год должен быть больше, чем 1500")
    @Column(name = "year")
    private int year;

    @Column(name = "date_of_take")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfTake;

    @Transient
    private boolean expired;

    @ManyToOne()
    @JoinColumn(name = "person_id")
    private Person owner;


    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Book() {

    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getDateOfTake() {
        return dateOfTake;
    }

    public void setDateOfTake(Date dateOfTake) {
        this.dateOfTake = dateOfTake;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
