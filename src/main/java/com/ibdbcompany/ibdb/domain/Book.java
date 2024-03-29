package com.ibdbcompany.ibdb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "positive_voice")
    private Long positiveVoice;

    @Column(name = "negative_voice")
    private Long negativeVoice;


    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @JoinTable(name = "book_category",
               joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<Category> categories = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "books", allowSetters = true)
    private Author author;

    @OneToMany(mappedBy = "book")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "book")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<UserBook> userbook = new HashSet<>();


    @ManyToOne
    @JsonIgnoreProperties(value = "books", allowSetters = true)
    private ImageModel imageModel;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Book title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Book description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Book categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Book addCategory(Category category) {
        this.categories.add(category);
        category.getBooks().add(this);
        return this;
    }

    public Book removeCategory(Category category) {
        this.categories.remove(category);
        category.getBooks().remove(this);
        return this;
    }
    public Set<UserBook> getUserbook() {
        return userbook;
    }

    public void setUserbook(Set<UserBook> userbook) {
        this.userbook = userbook;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Author getAuthor() {
        return author;
    }

    public Book author(Author author) {
        this.author = author;
        return this;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Book comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public void setNegativeVoice(Long negativeVoice) {
        this.negativeVoice = negativeVoice;
    }

    public Long getPositiveVoice() {
        return positiveVoice;
    }

    public Book positiveVoice(Long positiveVoice) {
        this.positiveVoice = positiveVoice;
        return this;
    }

    public void setPositiveVoice(Long positiveVoice) {
        this.positiveVoice = positiveVoice;
    }

    public Long getNegativeVoice() {
        return negativeVoice;
    }

    public Book negativeVoice(Long negativeVoice) {
        this.negativeVoice = negativeVoice;
        return this;
    }

    public Book addComment(Comment comment) {
        this.comments.add(comment);
        comment.setBook(this);
        return this;
    }

    public Book removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setBook(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public ImageModel getImageModel() {
        return imageModel;
    }

    public Book imageModel(ImageModel imageModel) {
        this.imageModel = imageModel;
        return this;
    }

    public void setImageModel(ImageModel imageModel) {
        this.imageModel = imageModel;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return id != null && id.equals(((Book) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", positiveVoice=" + getPositiveVoice() +
            ", negativeVoice=" + getNegativeVoice() +
            "}";
    }
}
