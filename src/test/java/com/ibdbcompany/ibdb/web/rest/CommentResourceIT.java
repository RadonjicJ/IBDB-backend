package com.ibdbcompany.ibdb.web.rest;

import com.ibdbcompany.ibdb.IbdbApp;
import com.ibdbcompany.ibdb.domain.Comment;
import com.ibdbcompany.ibdb.domain.Book;
import com.ibdbcompany.ibdb.domain.User;
import com.ibdbcompany.ibdb.repository.CommentRepository;
import com.ibdbcompany.ibdb.service.CommentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CommentResource} REST controller.
 */
@SpringBootTest(classes = IbdbApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommentResourceIT {

    private static final String DEFAULT_COMMENT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_TEXT = "BBBBBBBBBB";

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommentMockMvc;

    private Comment comment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comment createEntity(EntityManager em) {
        Comment comment = new Comment()
            .commentText(DEFAULT_COMMENT_TEXT);
        // Add required entity
        Book book;
        if (TestUtil.findAll(em, Book.class).isEmpty()) {
            book = BookResourceIT.createEntity(em);
            em.persist(book);
            em.flush();
        } else {
            book = TestUtil.findAll(em, Book.class).get(0);
        }
        comment.setBook(book);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        comment.setUser(user);
        return comment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comment createUpdatedEntity(EntityManager em) {
        Comment comment = new Comment()
            .commentText(UPDATED_COMMENT_TEXT);
        // Add required entity
        Book book;
        if (TestUtil.findAll(em, Book.class).isEmpty()) {
            book = BookResourceIT.createUpdatedEntity(em);
            em.persist(book);
            em.flush();
        } else {
            book = TestUtil.findAll(em, Book.class).get(0);
        }
        comment.setBook(book);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        comment.setUser(user);
        return comment;
    }

    @BeforeEach
    public void initTest() {
        comment = createEntity(em);
    }

    @Test
    @Transactional
    public void createComment() throws Exception {
        int databaseSizeBeforeCreate = commentRepository.findAll().size();
        // Create the Comment
        restCommentMockMvc.perform(post("/api/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comment)))
            .andExpect(status().isCreated());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate + 1);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getCommentText()).isEqualTo(DEFAULT_COMMENT_TEXT);
    }

    @Test
    @Transactional
    public void createCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentRepository.findAll().size();

        // Create the Comment with an existing ID
        comment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentMockMvc.perform(post("/api/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comment)))
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCommentTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = commentRepository.findAll().size();
        // set the field null
        comment.setCommentText(null);

        // Create the Comment, which fails.


        restCommentMockMvc.perform(post("/api/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comment)))
            .andExpect(status().isBadRequest());

        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComments() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList
        restCommentMockMvc.perform(get("/api/comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comment.getId().intValue())))
            .andExpect(jsonPath("$.[*].commentText").value(hasItem(DEFAULT_COMMENT_TEXT)));
    }
    
    @Test
    @Transactional
    public void getComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get the comment
        restCommentMockMvc.perform(get("/api/comments/{id}", comment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comment.getId().intValue()))
            .andExpect(jsonPath("$.commentText").value(DEFAULT_COMMENT_TEXT));
    }
    @Test
    @Transactional
    public void getNonExistingComment() throws Exception {
        // Get the comment
        restCommentMockMvc.perform(get("/api/comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComment() throws Exception {
        // Initialize the database
        commentService.save(comment);

        int databaseSizeBeforeUpdate = commentRepository.findAll().size();

        // Update the comment
        Comment updatedComment = commentRepository.findById(comment.getId()).get();
        // Disconnect from session so that the updates on updatedComment are not directly saved in db
        em.detach(updatedComment);
        updatedComment
            .commentText(UPDATED_COMMENT_TEXT);

        restCommentMockMvc.perform(put("/api/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedComment)))
            .andExpect(status().isOk());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getCommentText()).isEqualTo(UPDATED_COMMENT_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingComment() throws Exception {
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentMockMvc.perform(put("/api/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comment)))
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComment() throws Exception {
        // Initialize the database
        commentService.save(comment);

        int databaseSizeBeforeDelete = commentRepository.findAll().size();

        // Delete the comment
        restCommentMockMvc.perform(delete("/api/comments/{id}", comment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
