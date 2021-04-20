package com.ibdbcompany.ibdb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "comment_text", nullable = false)
    private String commentText;
/**
    @NotNull
    @Column(name = "date", nullable = false)
    private ZonedDateTime date;
*/
    @Column(name = "positive_voice")
    private Long positiveVoice;

    @Column(name = "negative_voice")
    private Long negativeVoice;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "comments", allowSetters = true)
    private Book book;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "comments", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public Comment commentText(String commentText) {
        this.commentText = commentText;
        return this;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
/*
    public ZonedDateTime getDate() {
        return date;
    }

    public Comment date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }
*/
    public Long getPositiveVoice() {
        return positiveVoice;
    }

    public Comment positiveVoice(Long positiveVoice) {
        this.positiveVoice = positiveVoice;
        return this;
    }

    public void setPositiveVoice(Long positiveVoice) {
        this.positiveVoice = positiveVoice;
    }

    public Long getNegativeVoice() {
        return negativeVoice;
    }

    public Comment negativeVoice(Long negativeVoice) {
        this.negativeVoice = negativeVoice;
        return this;
    }

    public void setNegativeVoice(Long negativeVoice) {
        this.negativeVoice = negativeVoice;
    }

    public Book getBook() {
        return book;
    }

    public Comment book(Book book) {
        this.book = book;
        return this;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public Comment user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }
        return id != null && id.equals(((Comment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", commentText='" + getCommentText() + "'" +
     //       ", date='" + getDate() + "'" +
            ", positiveVoice=" + getPositiveVoice() +
            ", negativeVoice=" + getNegativeVoice() +
            "}";
    }
}
