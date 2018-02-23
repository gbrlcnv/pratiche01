package it.etianus.pratiche.web.rest;

import it.etianus.pratiche.Pratiche01App;

import it.etianus.pratiche.domain.RequestStatus;
import it.etianus.pratiche.repository.RequestStatusRepository;
import it.etianus.pratiche.service.RequestStatusService;
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
import java.util.List;

import static it.etianus.pratiche.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.etianus.pratiche.domain.enumeration.RequestStatusEnum;
/**
 * Test class for the RequestStatusResource REST controller.
 *
 * @see RequestStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Pratiche01App.class)
public class RequestStatusResourceIntTest {

    private static final RequestStatusEnum DEFAULT_CODE = RequestStatusEnum.PROPOSED;
    private static final RequestStatusEnum UPDATED_CODE = RequestStatusEnum.OPEN;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RequestStatusRepository requestStatusRepository;

    @Autowired
    private RequestStatusService requestStatusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRequestStatusMockMvc;

    private RequestStatus requestStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequestStatusResource requestStatusResource = new RequestStatusResource(requestStatusService);
        this.restRequestStatusMockMvc = MockMvcBuilders.standaloneSetup(requestStatusResource)
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
    public static RequestStatus createEntity(EntityManager em) {
        RequestStatus requestStatus = new RequestStatus()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return requestStatus;
    }

    @Before
    public void initTest() {
        requestStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequestStatus() throws Exception {
        int databaseSizeBeforeCreate = requestStatusRepository.findAll().size();

        // Create the RequestStatus
        restRequestStatusMockMvc.perform(post("/api/request-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestStatus)))
            .andExpect(status().isCreated());

        // Validate the RequestStatus in the database
        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeCreate + 1);
        RequestStatus testRequestStatus = requestStatusList.get(requestStatusList.size() - 1);
        assertThat(testRequestStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRequestStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRequestStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requestStatusRepository.findAll().size();

        // Create the RequestStatus with an existing ID
        requestStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestStatusMockMvc.perform(post("/api/request-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestStatus)))
            .andExpect(status().isBadRequest());

        // Validate the RequestStatus in the database
        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestStatusRepository.findAll().size();
        // set the field null
        requestStatus.setCode(null);

        // Create the RequestStatus, which fails.

        restRequestStatusMockMvc.perform(post("/api/request-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestStatus)))
            .andExpect(status().isBadRequest());

        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestStatusRepository.findAll().size();
        // set the field null
        requestStatus.setDescription(null);

        // Create the RequestStatus, which fails.

        restRequestStatusMockMvc.perform(post("/api/request-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestStatus)))
            .andExpect(status().isBadRequest());

        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRequestStatuses() throws Exception {
        // Initialize the database
        requestStatusRepository.saveAndFlush(requestStatus);

        // Get all the requestStatusList
        restRequestStatusMockMvc.perform(get("/api/request-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getRequestStatus() throws Exception {
        // Initialize the database
        requestStatusRepository.saveAndFlush(requestStatus);

        // Get the requestStatus
        restRequestStatusMockMvc.perform(get("/api/request-statuses/{id}", requestStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requestStatus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRequestStatus() throws Exception {
        // Get the requestStatus
        restRequestStatusMockMvc.perform(get("/api/request-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequestStatus() throws Exception {
        // Initialize the database
        requestStatusService.save(requestStatus);

        int databaseSizeBeforeUpdate = requestStatusRepository.findAll().size();

        // Update the requestStatus
        RequestStatus updatedRequestStatus = requestStatusRepository.findOne(requestStatus.getId());
        // Disconnect from session so that the updates on updatedRequestStatus are not directly saved in db
        em.detach(updatedRequestStatus);
        updatedRequestStatus
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restRequestStatusMockMvc.perform(put("/api/request-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRequestStatus)))
            .andExpect(status().isOk());

        // Validate the RequestStatus in the database
        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeUpdate);
        RequestStatus testRequestStatus = requestStatusList.get(requestStatusList.size() - 1);
        assertThat(testRequestStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRequestStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingRequestStatus() throws Exception {
        int databaseSizeBeforeUpdate = requestStatusRepository.findAll().size();

        // Create the RequestStatus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRequestStatusMockMvc.perform(put("/api/request-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestStatus)))
            .andExpect(status().isCreated());

        // Validate the RequestStatus in the database
        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRequestStatus() throws Exception {
        // Initialize the database
        requestStatusService.save(requestStatus);

        int databaseSizeBeforeDelete = requestStatusRepository.findAll().size();

        // Get the requestStatus
        restRequestStatusMockMvc.perform(delete("/api/request-statuses/{id}", requestStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestStatus.class);
        RequestStatus requestStatus1 = new RequestStatus();
        requestStatus1.setId(1L);
        RequestStatus requestStatus2 = new RequestStatus();
        requestStatus2.setId(requestStatus1.getId());
        assertThat(requestStatus1).isEqualTo(requestStatus2);
        requestStatus2.setId(2L);
        assertThat(requestStatus1).isNotEqualTo(requestStatus2);
        requestStatus1.setId(null);
        assertThat(requestStatus1).isNotEqualTo(requestStatus2);
    }
}
