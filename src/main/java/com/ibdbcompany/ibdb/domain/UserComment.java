package com.ibdbcompany.ibdb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CollectionId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="user_comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "positive_voice", nullable = false, unique = false)
    private boolean positiveVoice;

    @NotNull
    @Column(name = "negative_voice", nullable = false, unique = false)
    private boolean negativeVoice;

    @ManyToOne()
    @NotNull
    @JsonIgnoreProperties(value = "usercomments", allowSetters = true)
    private User user;

    @ManyToOne()
    @NotNull
    @JsonIgnoreProperties(value = "usercomments", allowSetters = true)
    private Comment comment;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserComment)) {
            return false;
        }
        return id != null && id.equals(((UserComment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPositiveVoice() {
        return positiveVoice;
    }

    public void setPositiveVoice(boolean positiveVoice) {
        this.positiveVoice = positiveVoice;
    }

    public boolean isNegativeVoice() {
        return negativeVoice;
    }

    public void setNegativeVoice(boolean negativeVoice) {
        this.negativeVoice = negativeVoice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void SetComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "UserComment{" +
            "id=" + id +
            ", positiveVoice=" + positiveVoice +
            ", negativeVoice=" + negativeVoice +
            ", user=" + user +
            ", comment=" + comment +
            '}';
    }
}
