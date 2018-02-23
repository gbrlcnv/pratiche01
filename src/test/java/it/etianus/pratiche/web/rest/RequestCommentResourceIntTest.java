package it.etianus.pratiche.web.rest;

import it.etianus.pratiche.Pratiche01App;

import it.etianus.pratiche.domain.RequestComment;
import it.etianus.pratiche.repository.RequestCommentRepository;
import it.etianus.pratiche.service.RequestCommentService;
import it.etianus.pratiche.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static it.etianus.pratiche.web.rest.TestUtil.sameInstant;
import static it.etianus.pratiche.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.etianus.pratiche.domain.enumeration.InOutEnum;
/**
 * Test class for the RequestCommentResource REST controller.
 *
 * @see RequestCommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Pratiche01App.class)
public class RequestCommentResourceIntTest {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_COMMENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_COMMENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final InOutEnum DEFAULT_IN_OUT = InOutEnum.IN;
    private static final InOutEnum UPDATED_IN_OUT = InOutEnum.OUT;

    @Autowired
    private RequestCommentRepository requestCommentRepository;

    @Autowired
    private RequestCommentService requestCommentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRequestCommentMockMvc;

    private RequestComment requestComment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequestCommentResource requestCommentResource = new RequestCommentResource(requestCommentService);
        this.restRequestCommentMockMvc = MockMvcBuilders.standaloneSetup(requestCommentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestComment createEntity(EntityManager em) {
        RequestComment requestComment = new RequestComment()
            .text(DEFAULT_TEXT)
            .title(DEFAULT_TITLE)
            .commentDate(DEFAULT_COMMENT_DATE)
            .inOut(DEFAULT_IN_OUT);
        return requestComment;
    }

    @Before
    public void initTest() {
        requestComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequestComment() throws Exception {
        int databaseSizeBeforeCreate = requestCommentRepository.findAll().size();

        // Create the RequestComment
        restRequestCommentMockMvc.perform(post("/api/request-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestComment)))
            .andExpect(status().isCreated());

        // Validate the RequestComment in the database
        List<RequestComment> requestCommentList = requestCommentRepository.findAll();
        assertThat(requestCommentList).hasSize(databaseSizeBeforeCreate + 1);
        RequestComment testRequestComment = requestCommentList.get(requestCommentList.size() - 1);
        assertThat(testRequestComment.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testRequestComment.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testRequestComment.getCommentDate()).isEqualTo(DEFAULT_COMMENT_DATE);
        assertThat(testRequestComment.getInOut()).isEqualTo(DEFAULT_IN_OUT);
    }

    @Test
    @Transactional
    public void createRequestCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requestCommentRepository.findAll().size();

        // Create the RequestComment with an existing ID
        requestComment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestCommentMockMvc.perform(post("/api/request-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestComment)))
            .andExpect(status().isBadRequest());

        // Validate the RequestComment in the database
        List<RequestComment> requestCommentList = requestCommentRepository.findAll();
        assertThat(requestCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestCommentRepository.findAll().size();
        // set the field null
        requestComment.setText(null);

        // Create the RequestComment, which fails.

        restRequestCommentMockMvc.perform(post("/api/request-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestComment)))
            .andExpect(status().isBadRequest());

        List<RequestComment> requestCommentList = requestCommentRepository.findAll();
        assertThat(requestCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRequestComments() throws Exception {
        // Initialize the database
        requestCommentRepository.saveAndFlush(requestComment);

        // Get all the requestCommentList
        restRequestCommentMockMvc.perform(get("/api/request-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].commentDate").value(hasItem(sameInstant(DEFAULT_COMMENT_DATE))))
            .andExpect(jsonPath("$.[*].inOut").value(hasItem(DEFAULT_IN_OUT.toString())));
    }

    @Test
    @Transactional
    public void getRequestComment() throws Exception {
        // Initialize the database
        requestCommentRepository.saveAndFlush(requestComment);

        // Get the requestComment
        restRequestCommentMockMvc.perform(get("/api/request-comments/{id}", requestComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requestComment.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.commentDate").value(sameInstant(DEFAULT_COMMENT_DATE)))
            .andExpect(jsonPath("$.inOut").value(DEFAULT_IN_OUT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRequestComment() throws Exception {
        // Get the requestComment
        restRequestCommentMockMvc.perform(get("/api/request-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequestComment() throws Exception {
        // Initialize the database
        requestCommentService.save(requestComment);

        int databaseSizeBeforeUpdate = requestCommentRepository.findAll().size();

        // Update the requestComment
        RequestComment updatedRequestComment = requestCommentRepository.findOne(requestComment.getId());
        // Disconnect from session so that the updates on updatedRequestComment are not directly saved in db
        em.detach(updatedRequestComment);
        updatedRequestComment
            .text(UPDATED_TEXT)
            .title(UPDATED_TITLE)
            .commentDate(UPDATED_COMMENT_DATE)
            .inOut(UPDATED_IN_OUT);

        restRequestCommentMockMvc.perform(put("/api/request-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRequestComment)))
            .andExpect(status().isOk());

        // Validate the RequestComment in the database
        List<RequestComment> requestCommentList = requestCommentRepository.findAll();
        assertThat(requestCommentList).hasSize(databaseSizeBeforeUpdate);
        RequestComment testRequestComment = requestCommentList.get(requestCommentList.size() - 1);
        assertThat(testRequestComment.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testRequestComment.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testRequestComment.getCommentDate()).isEqualTo(UPDATED_COMMENT_DATE);
        assertThat(testRequestComment.getInOut()).isEqualTo(UPDATED_IN_OUT);
    }

    @Test
    @Transactional
    public void updateNonExistingRequestComment() throws Exception {
        int databaseSizeBeforeUpdate = requestCommentRepository.findAll().size();

        // Create the RequestComment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRequestCommentMockMvc.perform(put("/api/request-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestComment)))
            .andExpect(status().isCreated());

        // Validate the RequestComment in the database
        List<RequestComment> requestCommentList = requestCommentRepository.findAll();
        assertThat(requestCommentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRequestComment() throws Exception {
        // Initialize the database
        requestCommentService.save(requestComment);

        int databaseSizeBeforeDelete = requestCommentRepository.findAll().size();

        // Get the requestComment
        restRequestCommentMockMvc.perform(delete("/api/request-comments/{id}", requestComment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RequestComment> requestCommentList = requestCommentRepository.findAll();
        assertThat(requestCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestComment.class);
        RequestComment requestComment1 = new RequestComment();
        requestComment1.setId(1L);
        RequestComment requestComment2 = new RequestComment();
        requestComment2.setId(requestComment1.getId());
        assertThat(requestComment1).isEqualTo(requestComment2);
        requestComment2.setId(2L);
        assertThat(requestComment1).isNotEqualTo(requestComment2);
        requestComment1.setId(null);
        assertThat(requestComment1).isNotEqualTo(requestComment2);
    }
}
