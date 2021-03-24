package com.ibdbcompany.ibdb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Quote.
 */
@Entity
@Table(name = "quote")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Quote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "quote_text", nullable = false, unique = true)
    private String quoteText;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "quotes", allowSetters = true)
    private Author author;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "quotes", allowSetters = true)
    private Book book;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public Quote quoteText(String quoteText) {
        this.quoteText = quoteText;
        return this;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    public Author getAuthor() {
        return author;
    }

    public Quote author(Author author) {
        this.author = author;
        return this;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }

    public Quote book(Book book) {
        this.book = book;
        return this;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quote)) {
            return false;
        }
        return id != null && id.equals(((Quote) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Quote{" +
            "id=" + getId() +
            ", quoteText='" + getQuoteText() + "'" +
            "}";
    }
}
