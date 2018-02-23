package it.etianus.pratiche.web.rest;

import it.etianus.pratiche.Pratiche01App;

import it.etianus.pratiche.domain.RequestRicorsoTributario;
import it.etianus.pratiche.repository.RequestRicorsoTributarioRepository;
import it.etianus.pratiche.service.RequestRicorsoTributarioService;
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

import it.etianus.pratiche.domain.enumeration.TipologiaAttoEnum;
/**
 * Test class for the RequestRicorsoTributarioResource REST controller.
 *
 * @see RequestRicorsoTributarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Pratiche01App.class)
public class RequestRicorsoTributarioResourceIntTest {

    private static final ZonedDateTime DEFAULT_NOTIFICA_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_NOTIFICA_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_EMISSIONE_RUOLO_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EMISSIONE_RUOLO_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final TipologiaAttoEnum DEFAULT_TIPOLOGIA_ATTO = TipologiaAttoEnum.VERBALEACCERTAMENTO;
    private static final TipologiaAttoEnum UPDATED_TIPOLOGIA_ATTO = TipologiaAttoEnum.VERBALECOSTATAZIONE;

    @Autowired
    private RequestRicorsoTributarioRepository requestRicorsoTributarioRepository;

    @Autowired
    private RequestRicorsoTributarioService requestRicorsoTributarioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRequestRicorsoTributarioMockMvc;

    private RequestRicorsoTributario requestRicorsoTributario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequestRicorsoTributarioResource requestRicorsoTributarioResource = new RequestRicorsoTributarioResource(requestRicorsoTributarioService);
        this.restRequestRicorsoTributarioMockMvc = MockMvcBuilders.standaloneSetup(requestRicorsoTributarioResource)
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
    public static RequestRicorsoTributario createEntity(EntityManager em) {
        RequestRicorsoTributario requestRicorsoTributario = new RequestRicorsoTributario()
            .notificaDate(DEFAULT_NOTIFICA_DATE)
            .emissioneRuoloDate(DEFAULT_EMISSIONE_RUOLO_DATE)
            .tipologiaAtto(DEFAULT_TIPOLOGIA_ATTO);
        return requestRicorsoTributario;
    }

    @Before
    public void initTest() {
        requestRicorsoTributario = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequestRicorsoTributario() throws Exception {
        int databaseSizeBeforeCreate = requestRicorsoTributarioRepository.findAll().size();

        // Create the RequestRicorsoTributario
        restRequestRicorsoTributarioMockMvc.perform(post("/api/request-ricorso-tributarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestRicorsoTributario)))
            .andExpect(status().isCreated());

        // Validate the RequestRicorsoTributario in the database
        List<RequestRicorsoTributario> requestRicorsoTributarioList = requestRicorsoTributarioRepository.findAll();
        assertThat(requestRicorsoTributarioList).hasSize(databaseSizeBeforeCreate + 1);
        RequestRicorsoTributario testRequestRicorsoTributario = requestRicorsoTributarioList.get(requestRicorsoTributarioList.size() - 1);
        assertThat(testRequestRicorsoTributario.getNotificaDate()).isEqualTo(DEFAULT_NOTIFICA_DATE);
        assertThat(testRequestRicorsoTributario.getEmissioneRuoloDate()).isEqualTo(DEFAULT_EMISSIONE_RUOLO_DATE);
        assertThat(testRequestRicorsoTributario.getTipologiaAtto()).isEqualTo(DEFAULT_TIPOLOGIA_ATTO);
    }

    @Test
    @Transactional
    public void createRequestRicorsoTributarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requestRicorsoTributarioRepository.findAll().size();

        // Create the RequestRicorsoTributario with an existing ID
        requestRicorsoTributario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestRicorsoTributarioMockMvc.perform(post("/api/request-ricorso-tributarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestRicorsoTributario)))
            .andExpect(status().isBadRequest());

        // Validate the RequestRicorsoTributario in the database
        List<RequestRicorsoTributario> requestRicorsoTributarioList = requestRicorsoTributarioRepository.findAll();
        assertThat(requestRicorsoTributarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNotificaDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestRicorsoTributarioRepository.findAll().size();
        // set the field null
        requestRicorsoTributario.setNotificaDate(null);

        // Create the RequestRicorsoTributario, which fails.

        restRequestRicorsoTributarioMockMvc.perform(post("/api/request-ricorso-tributarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestRicorsoTributario)))
            .andExpect(status().isBadRequest());

        List<RequestRicorsoTributario> requestRicorsoTributarioList = requestRicorsoTributarioRepository.findAll();
        assertThat(requestRicorsoTributarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmissioneRuoloDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestRicorsoTributarioRepository.findAll().size();
        // set the field null
        requestRicorsoTributario.setEmissioneRuoloDate(null);

        // Create the RequestRicorsoTributario, which fails.

        restRequestRicorsoTributarioMockMvc.perform(post("/api/request-ricorso-tributarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestRicorsoTributario)))
            .andExpect(status().isBadRequest());

        List<RequestRicorsoTributario> requestRicorsoTributarioList = requestRicorsoTributarioRepository.findAll();
        assertThat(requestRicorsoTributarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRequestRicorsoTributarios() throws Exception {
        // Initialize the database
        requestRicorsoTributarioRepository.saveAndFlush(requestRicorsoTributario);

        // Get all the requestRicorsoTributarioList
        restRequestRicorsoTributarioMockMvc.perform(get("/api/request-ricorso-tributarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestRicorsoTributario.getId().intValue())))
            .andExpect(jsonPath("$.[*].notificaDate").value(hasItem(sameInstant(DEFAULT_NOTIFICA_DATE))))
            .andExpect(jsonPath("$.[*].emissioneRuoloDate").value(hasItem(sameInstant(DEFAULT_EMISSIONE_RUOLO_DATE))))
            .andExpect(jsonPath("$.[*].tipologiaAtto").value(hasItem(DEFAULT_TIPOLOGIA_ATTO.toString())));
    }

    @Test
    @Transactional
    public void getRequestRicorsoTributario() throws Exception {
        // Initialize the database
        requestRicorsoTributarioRepository.saveAndFlush(requestRicorsoTributario);

        // Get the requestRicorsoTributario
        restRequestRicorsoTributarioMockMvc.perform(get("/api/request-ricorso-tributarios/{id}", requestRicorsoTributario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requestRicorsoTributario.getId().intValue()))
            .andExpect(jsonPath("$.notificaDate").value(sameInstant(DEFAULT_NOTIFICA_DATE)))
            .andExpect(jsonPath("$.emissioneRuoloDate").value(sameInstant(DEFAULT_EMISSIONE_RUOLO_DATE)))
            .andExpect(jsonPath("$.tipologiaAtto").value(DEFAULT_TIPOLOGIA_ATTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRequestRicorsoTributario() throws Exception {
        // Get the requestRicorsoTributario
        restRequestRicorsoTributarioMockMvc.perform(get("/api/request-ricorso-tributarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequestRicorsoTributario() throws Exception {
        // Initialize the database
        requestRicorsoTributarioService.save(requestRicorsoTributario);

        int databaseSizeBeforeUpdate = requestRicorsoTributarioRepository.findAll().size();

        // Update the requestRicorsoTributario
        RequestRicorsoTributario updatedRequestRicorsoTributario = requestRicorsoTributarioRepository.findOne(requestRicorsoTributario.getId());
        // Disconnect from session so that the updates on updatedRequestRicorsoTributario are not directly saved in db
        em.detach(updatedRequestRicorsoTributario);
        updatedRequestRicorsoTributario
            .notificaDate(UPDATED_NOTIFICA_DATE)
            .emissioneRuoloDate(UPDATED_EMISSIONE_RUOLO_DATE)
            .tipologiaAtto(UPDATED_TIPOLOGIA_ATTO);

        restRequestRicorsoTributarioMockMvc.perform(put("/api/request-ricorso-tributarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRequestRicorsoTributario)))
            .andExpect(status().isOk());

        // Validate the RequestRicorsoTributario in the database
        List<RequestRicorsoTributario> requestRicorsoTributarioList = requestRicorsoTributarioRepository.findAll();
        assertThat(requestRicorsoTributarioList).hasSize(databaseSizeBeforeUpdate);
        RequestRicorsoTributario testRequestRicorsoTributario = requestRicorsoTributarioList.get(requestRicorsoTributarioList.size() - 1);
        assertThat(testRequestRicorsoTributario.getNotificaDate()).isEqualTo(UPDATED_NOTIFICA_DATE);
        assertThat(testRequestRicorsoTributario.getEmissioneRuoloDate()).isEqualTo(UPDATED_EMISSIONE_RUOLO_DATE);
        assertThat(testRequestRicorsoTributario.getTipologiaAtto()).isEqualTo(UPDATED_TIPOLOGIA_ATTO);
    }

    @Test
    @Transactional
    public void updateNonExistingRequestRicorsoTributario() throws Exception {
        int databaseSizeBeforeUpdate = requestRicorsoTributarioRepository.findAll().size();

        // Create the RequestRicorsoTributario

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRequestRicorsoTributarioMockMvc.perform(put("/api/request-ricorso-tributarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestRicorsoTributario)))
            .andExpect(status().isCreated());

        // Validate the RequestRicorsoTributario in the database
        List<RequestRicorsoTributario> requestRicorsoTributarioList = requestRicorsoTributarioRepository.findAll();
        assertThat(requestRicorsoTributarioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRequestRicorsoTributario() throws Exception {
        // Initialize the database
        requestRicorsoTributarioService.save(requestRicorsoTributario);

        int databaseSizeBeforeDelete = requestRicorsoTributarioRepository.findAll().size();

        // Get the requestRicorsoTributario
        restRequestRicorsoTributarioMockMvc.perform(delete("/api/request-ricorso-tributarios/{id}", requestRicorsoTributario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RequestRicorsoTributario> requestRicorsoTributarioList = requestRicorsoTributarioRepository.findAll();
        assertThat(requestRicorsoTributarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestRicorsoTributario.class);
        RequestRicorsoTributario requestRicorsoTributario1 = new RequestRicorsoTributario();
        requestRicorsoTributario1.setId(1L);
        RequestRicorsoTributario requestRicorsoTributario2 = new RequestRicorsoTributario();
        requestRicorsoTributario2.setId(requestRicorsoTributario1.getId());
        assertThat(requestRicorsoTributario1).isEqualTo(requestRicorsoTributario2);
        requestRicorsoTributario2.setId(2L);
        assertThat(requestRicorsoTributario1).isNotEqualTo(requestRicorsoTributario2);
        requestRicorsoTributario1.setId(null);
        assertThat(requestRicorsoTributario1).isNotEqualTo(requestRicorsoTributario2);
    }
}
