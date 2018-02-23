package it.etianus.pratiche.web.rest;

import it.etianus.pratiche.Pratiche01App;

import it.etianus.pratiche.domain.RequestStatusLog;
import it.etianus.pratiche.repository.RequestStatusLogRepository;
import it.etianus.pratiche.service.RequestStatusLogService;
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

/**
 * Test class for the RequestStatusLogResource REST controller.
 *
 * @see RequestStatusLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Pratiche01App.class)
public class RequestStatusLogResourceIntTest {

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_STATUS_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STATUS_FROM_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_STATUS_CHANGE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STATUS_CHANGE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RequestStatusLogRepository requestStatusLogRepository;

    @Autowired
    private RequestStatusLogService requestStatusLogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRequestStatusLogMockMvc;

    private RequestStatusLog requestStatusLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequestStatusLogResource requestStatusLogResource = new RequestStatusLogResource(requestStatusLogService);
        this.restRequestStatusLogMockMvc = MockMvcBuilders.standaloneSetup(requestStatusLogResource)
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
    public static RequestStatusLog createEntity(EntityManager em) {
        RequestStatusLog requestStatusLog = new RequestStatusLog()
            .note(DEFAULT_NOTE)
            .statusFromDate(DEFAULT_STATUS_FROM_DATE)
            .statusChangeDate(DEFAULT_STATUS_CHANGE_DATE);
        return requestStatusLog;
    }

    @Before
    public void initTest() {
        requestStatusLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequestStatusLog() throws Exception {
        int databaseSizeBeforeCreate = requestStatusLogRepository.findAll().size();

        // Create the RequestStatusLog
        restRequestStatusLogMockMvc.perform(post("/api/request-status-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestStatusLog)))
            .andExpect(status().isCreated());

        // Validate the RequestStatusLog in the database
        List<RequestStatusLog> requestStatusLogList = requestStatusLogRepository.findAll();
        assertThat(requestStatusLogList).hasSize(databaseSizeBeforeCreate + 1);
        RequestStatusLog testRequestStatusLog = requestStatusLogList.get(requestStatusLogList.size() - 1);
        assertThat(testRequestStatusLog.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testRequestStatusLog.getStatusFromDate()).isEqualTo(DEFAULT_STATUS_FROM_DATE);
        assertThat(testRequestStatusLog.getStatusChangeDate()).isEqualTo(DEFAULT_STATUS_CHANGE_DATE);
    }

    @Test
    @Transactional
    public void createRequestStatusLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requestStatusLogRepository.findAll().size();

        // Create the RequestStatusLog with an existing ID
        requestStatusLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestStatusLogMockMvc.perform(post("/api/request-status-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestStatusLog)))
            .andExpect(status().isBadRequest());

        // Validate the RequestStatusLog in the database
        List<RequestStatusLog> requestStatusLogList = requestStatusLogRepository.findAll();
        assertThat(requestStatusLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatusFromDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestStatusLogRepository.findAll().size();
        // set the field null
        requestStatusLog.setStatusFromDate(null);

        // Create the RequestStatusLog, which fails.

        restRequestStatusLogMockMvc.perform(post("/api/request-status-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestStatusLog)))
            .andExpect(status().isBadRequest());

        List<RequestStatusLog> requestStatusLogList = requestStatusLogRepository.findAll();
        assertThat(requestStatusLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusChangeDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestStatusLogRepository.findAll().size();
        // set the field null
        requestStatusLog.setStatusChangeDate(null);

        // Create the RequestStatusLog, which fails.

        restRequestStatusLogMockMvc.perform(post("/api/request-status-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestStatusLog)))
            .andExpect(status().isBadRequest());

        List<RequestStatusLog> requestStatusLogList = requestStatusLogRepository.findAll();
        assertThat(requestStatusLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRequestStatusLogs() throws Exception {
        // Initialize the database
        requestStatusLogRepository.saveAndFlush(requestStatusLog);

        // Get all the requestStatusLogList
        restRequestStatusLogMockMvc.perform(get("/api/request-status-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestStatusLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].statusFromDate").value(hasItem(sameInstant(DEFAULT_STATUS_FROM_DATE))))
            .andExpect(jsonPath("$.[*].statusChangeDate").value(hasItem(sameInstant(DEFAULT_STATUS_CHANGE_DATE))));
    }

    @Test
    @Transactional
    public void getRequestStatusLog() throws Exception {
        // Initialize the database
        requestStatusLogRepository.saveAndFlush(requestStatusLog);

        // Get the requestStatusLog
        restRequestStatusLogMockMvc.perform(get("/api/request-status-logs/{id}", requestStatusLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requestStatusLog.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.statusFromDate").value(sameInstant(DEFAULT_STATUS_FROM_DATE)))
            .andExpect(jsonPath("$.statusChangeDate").value(sameInstant(DEFAULT_STATUS_CHANGE_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingRequestStatusLog() throws Exception {
        // Get the requestStatusLog
        restRequestStatusLogMockMvc.perform(get("/api/request-status-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequestStatusLog() throws Exception {
        // Initialize the database
        requestStatusLogService.save(requestStatusLog);

        int databaseSizeBeforeUpdate = requestStatusLogRepository.findAll().size();

        // Update the requestStatusLog
        RequestStatusLog updatedRequestStatusLog = requestStatusLogRepository.findOne(requestStatusLog.getId());
        // Disconnect from session so that the updates on updatedRequestStatusLog are not directly saved in db
        em.detach(updatedRequestStatusLog);
        updatedRequestStatusLog
            .note(UPDATED_NOTE)
            .statusFromDate(UPDATED_STATUS_FROM_DATE)
            .statusChangeDate(UPDATED_STATUS_CHANGE_DATE);

        restRequestStatusLogMockMvc.perform(put("/api/request-status-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRequestStatusLog)))
            .andExpect(status().isOk());

        // Validate the RequestStatusLog in the database
        List<RequestStatusLog> requestStatusLogList = requestStatusLogRepository.findAll();
        assertThat(requestStatusLogList).hasSize(databaseSizeBeforeUpdate);
        RequestStatusLog testRequestStatusLog = requestStatusLogList.get(requestStatusLogList.size() - 1);
        assertThat(testRequestStatusLog.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testRequestStatusLog.getStatusFromDate()).isEqualTo(UPDATED_STATUS_FROM_DATE);
        assertThat(testRequestStatusLog.getStatusChangeDate()).isEqualTo(UPDATED_STATUS_CHANGE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRequestStatusLog() throws Exception {
        int databaseSizeBeforeUpdate = requestStatusLogRepository.findAll().size();

        // Create the RequestStatusLog

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRequestStatusLogMockMvc.perform(put("/api/request-status-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestStatusLog)))
            .andExpect(status().isCreated());

        // Validate the RequestStatusLog in the database
        List<RequestStatusLog> requestStatusLogList = requestStatusLogRepository.findAll();
        assertThat(requestStatusLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRequestStatusLog() throws Exception {
        // Initialize the database
        requestStatusLogService.save(requestStatusLog);

        int databaseSizeBeforeDelete = requestStatusLogRepository.findAll().size();

        // Get the requestStatusLog
        restRequestStatusLogMockMvc.perform(delete("/api/request-status-logs/{id}", requestStatusLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RequestStatusLog> requestStatusLogList = requestStatusLogRepository.findAll();
        assertThat(requestStatusLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestStatusLog.class);
        RequestStatusLog requestStatusLog1 = new RequestStatusLog();
        requestStatusLog1.setId(1L);
        RequestStatusLog requestStatusLog2 = new RequestStatusLog();
        requestStatusLog2.setId(requestStatusLog1.getId());
        assertThat(requestStatusLog1).isEqualTo(requestStatusLog2);
        requestStatusLog2.setId(2L);
        assertThat(requestStatusLog1).isNotEqualTo(requestStatusLog2);
        requestStatusLog1.setId(null);
        assertThat(requestStatusLog1).isNotEqualTo(requestStatusLog2);
    }
}
