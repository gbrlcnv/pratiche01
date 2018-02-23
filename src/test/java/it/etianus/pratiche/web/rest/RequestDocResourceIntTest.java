package it.etianus.pratiche.web.rest;

import it.etianus.pratiche.Pratiche01App;

import it.etianus.pratiche.domain.RequestDoc;
import it.etianus.pratiche.repository.RequestDocRepository;
import it.etianus.pratiche.service.RequestDocService;
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
 * Test class for the RequestDocResource REST controller.
 *
 * @see RequestDocResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Pratiche01App.class)
public class RequestDocResourceIntTest {

    private static final ZonedDateTime DEFAULT_SUBMISSION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SUBMISSION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final InOutEnum DEFAULT_IN_OUT = InOutEnum.IN;
    private static final InOutEnum UPDATED_IN_OUT = InOutEnum.OUT;

    @Autowired
    private RequestDocRepository requestDocRepository;

    @Autowired
    private RequestDocService requestDocService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRequestDocMockMvc;

    private RequestDoc requestDoc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequestDocResource requestDocResource = new RequestDocResource(requestDocService);
        this.restRequestDocMockMvc = MockMvcBuilders.standaloneSetup(requestDocResource)
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
    public static RequestDoc createEntity(EntityManager em) {
        RequestDoc requestDoc = new RequestDoc()
            .submissionDate(DEFAULT_SUBMISSION_DATE)
            .updateDate(DEFAULT_UPDATE_DATE)
            .description(DEFAULT_DESCRIPTION)
            .inOut(DEFAULT_IN_OUT);
        return requestDoc;
    }

    @Before
    public void initTest() {
        requestDoc = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequestDoc() throws Exception {
        int databaseSizeBeforeCreate = requestDocRepository.findAll().size();

        // Create the RequestDoc
        restRequestDocMockMvc.perform(post("/api/request-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestDoc)))
            .andExpect(status().isCreated());

        // Validate the RequestDoc in the database
        List<RequestDoc> requestDocList = requestDocRepository.findAll();
        assertThat(requestDocList).hasSize(databaseSizeBeforeCreate + 1);
        RequestDoc testRequestDoc = requestDocList.get(requestDocList.size() - 1);
        assertThat(testRequestDoc.getSubmissionDate()).isEqualTo(DEFAULT_SUBMISSION_DATE);
        assertThat(testRequestDoc.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testRequestDoc.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRequestDoc.getInOut()).isEqualTo(DEFAULT_IN_OUT);
    }

    @Test
    @Transactional
    public void createRequestDocWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requestDocRepository.findAll().size();

        // Create the RequestDoc with an existing ID
        requestDoc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestDocMockMvc.perform(post("/api/request-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestDoc)))
            .andExpect(status().isBadRequest());

        // Validate the RequestDoc in the database
        List<RequestDoc> requestDocList = requestDocRepository.findAll();
        assertThat(requestDocList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestDocRepository.findAll().size();
        // set the field null
        requestDoc.setDescription(null);

        // Create the RequestDoc, which fails.

        restRequestDocMockMvc.perform(post("/api/request-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestDoc)))
            .andExpect(status().isBadRequest());

        List<RequestDoc> requestDocList = requestDocRepository.findAll();
        assertThat(requestDocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInOutIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestDocRepository.findAll().size();
        // set the field null
        requestDoc.setInOut(null);

        // Create the RequestDoc, which fails.

        restRequestDocMockMvc.perform(post("/api/request-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestDoc)))
            .andExpect(status().isBadRequest());

        List<RequestDoc> requestDocList = requestDocRepository.findAll();
        assertThat(requestDocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRequestDocs() throws Exception {
        // Initialize the database
        requestDocRepository.saveAndFlush(requestDoc);

        // Get all the requestDocList
        restRequestDocMockMvc.perform(get("/api/request-docs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestDoc.getId().intValue())))
            .andExpect(jsonPath("$.[*].submissionDate").value(hasItem(sameInstant(DEFAULT_SUBMISSION_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].inOut").value(hasItem(DEFAULT_IN_OUT.toString())));
    }

    @Test
    @Transactional
    public void getRequestDoc() throws Exception {
        // Initialize the database
        requestDocRepository.saveAndFlush(requestDoc);

        // Get the requestDoc
        restRequestDocMockMvc.perform(get("/api/request-docs/{id}", requestDoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requestDoc.getId().intValue()))
            .andExpect(jsonPath("$.submissionDate").value(sameInstant(DEFAULT_SUBMISSION_DATE)))
            .andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.inOut").value(DEFAULT_IN_OUT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRequestDoc() throws Exception {
        // Get the requestDoc
        restRequestDocMockMvc.perform(get("/api/request-docs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequestDoc() throws Exception {
        // Initialize the database
        requestDocService.save(requestDoc);

        int databaseSizeBeforeUpdate = requestDocRepository.findAll().size();

        // Update the requestDoc
        RequestDoc updatedRequestDoc = requestDocRepository.findOne(requestDoc.getId());
        // Disconnect from session so that the updates on updatedRequestDoc are not directly saved in db
        em.detach(updatedRequestDoc);
        updatedRequestDoc
            .submissionDate(UPDATED_SUBMISSION_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .description(UPDATED_DESCRIPTION)
            .inOut(UPDATED_IN_OUT);

        restRequestDocMockMvc.perform(put("/api/request-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRequestDoc)))
            .andExpect(status().isOk());

        // Validate the RequestDoc in the database
        List<RequestDoc> requestDocList = requestDocRepository.findAll();
        assertThat(requestDocList).hasSize(databaseSizeBeforeUpdate);
        RequestDoc testRequestDoc = requestDocList.get(requestDocList.size() - 1);
        assertThat(testRequestDoc.getSubmissionDate()).isEqualTo(UPDATED_SUBMISSION_DATE);
        assertThat(testRequestDoc.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testRequestDoc.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRequestDoc.getInOut()).isEqualTo(UPDATED_IN_OUT);
    }

    @Test
    @Transactional
    public void updateNonExistingRequestDoc() throws Exception {
        int databaseSizeBeforeUpdate = requestDocRepository.findAll().size();

        // Create the RequestDoc

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRequestDocMockMvc.perform(put("/api/request-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestDoc)))
            .andExpect(status().isCreated());

        // Validate the RequestDoc in the database
        List<RequestDoc> requestDocList = requestDocRepository.findAll();
        assertThat(requestDocList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRequestDoc() throws Exception {
        // Initialize the database
        requestDocService.save(requestDoc);

        int databaseSizeBeforeDelete = requestDocRepository.findAll().size();

        // Get the requestDoc
        restRequestDocMockMvc.perform(delete("/api/request-docs/{id}", requestDoc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RequestDoc> requestDocList = requestDocRepository.findAll();
        assertThat(requestDocList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestDoc.class);
        RequestDoc requestDoc1 = new RequestDoc();
        requestDoc1.setId(1L);
        RequestDoc requestDoc2 = new RequestDoc();
        requestDoc2.setId(requestDoc1.getId());
        assertThat(requestDoc1).isEqualTo(requestDoc2);
        requestDoc2.setId(2L);
        assertThat(requestDoc1).isNotEqualTo(requestDoc2);
        requestDoc1.setId(null);
        assertThat(requestDoc1).isNotEqualTo(requestDoc2);
    }
}
