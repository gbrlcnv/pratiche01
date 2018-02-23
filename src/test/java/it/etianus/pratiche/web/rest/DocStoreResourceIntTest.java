package it.etianus.pratiche.web.rest;

import it.etianus.pratiche.Pratiche01App;

import it.etianus.pratiche.domain.DocStore;
import it.etianus.pratiche.repository.DocStoreRepository;
import it.etianus.pratiche.service.DocStoreService;
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
import org.springframework.util.Base64Utils;

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

import it.etianus.pratiche.domain.enumeration.LocaleEnum;
/**
 * Test class for the DocStoreResource REST controller.
 *
 * @see DocStoreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Pratiche01App.class)
public class DocStoreResourceIntTest {

    private static final LocaleEnum DEFAULT_LOCALE = LocaleEnum.EN;
    private static final LocaleEnum UPDATED_LOCALE = LocaleEnum.IT;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CONTENT_BINARY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT_BINARY = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CONTENT_BINARY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_BINARY_CONTENT_TYPE = "image/png";

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final byte[] DEFAULT_CONTENT_TEXT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT_TEXT = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CONTENT_TEXT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_TEXT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_MIME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MIME_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    @Autowired
    private DocStoreRepository docStoreRepository;

    @Autowired
    private DocStoreService docStoreService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDocStoreMockMvc;

    private DocStore docStore;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocStoreResource docStoreResource = new DocStoreResource(docStoreService);
        this.restDocStoreMockMvc = MockMvcBuilders.standaloneSetup(docStoreResource)
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
    public static DocStore createEntity(EntityManager em) {
        DocStore docStore = new DocStore()
            .locale(DEFAULT_LOCALE)
            .code(DEFAULT_CODE)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .contentBinary(DEFAULT_CONTENT_BINARY)
            .contentBinaryContentType(DEFAULT_CONTENT_BINARY_CONTENT_TYPE)
            .creationDate(DEFAULT_CREATION_DATE)
            .contentText(DEFAULT_CONTENT_TEXT)
            .contentTextContentType(DEFAULT_CONTENT_TEXT_CONTENT_TYPE)
            .mimeType(DEFAULT_MIME_TYPE)
            .path(DEFAULT_PATH);
        return docStore;
    }

    @Before
    public void initTest() {
        docStore = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocStore() throws Exception {
        int databaseSizeBeforeCreate = docStoreRepository.findAll().size();

        // Create the DocStore
        restDocStoreMockMvc.perform(post("/api/doc-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docStore)))
            .andExpect(status().isCreated());

        // Validate the DocStore in the database
        List<DocStore> docStoreList = docStoreRepository.findAll();
        assertThat(docStoreList).hasSize(databaseSizeBeforeCreate + 1);
        DocStore testDocStore = docStoreList.get(docStoreList.size() - 1);
        assertThat(testDocStore.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testDocStore.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDocStore.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDocStore.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDocStore.getContentBinary()).isEqualTo(DEFAULT_CONTENT_BINARY);
        assertThat(testDocStore.getContentBinaryContentType()).isEqualTo(DEFAULT_CONTENT_BINARY_CONTENT_TYPE);
        assertThat(testDocStore.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testDocStore.getContentText()).isEqualTo(DEFAULT_CONTENT_TEXT);
        assertThat(testDocStore.getContentTextContentType()).isEqualTo(DEFAULT_CONTENT_TEXT_CONTENT_TYPE);
        assertThat(testDocStore.getMimeType()).isEqualTo(DEFAULT_MIME_TYPE);
        assertThat(testDocStore.getPath()).isEqualTo(DEFAULT_PATH);
    }

    @Test
    @Transactional
    public void createDocStoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = docStoreRepository.findAll().size();

        // Create the DocStore with an existing ID
        docStore.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocStoreMockMvc.perform(post("/api/doc-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docStore)))
            .andExpect(status().isBadRequest());

        // Validate the DocStore in the database
        List<DocStore> docStoreList = docStoreRepository.findAll();
        assertThat(docStoreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLocaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = docStoreRepository.findAll().size();
        // set the field null
        docStore.setLocale(null);

        // Create the DocStore, which fails.

        restDocStoreMockMvc.perform(post("/api/doc-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docStore)))
            .andExpect(status().isBadRequest());

        List<DocStore> docStoreList = docStoreRepository.findAll();
        assertThat(docStoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = docStoreRepository.findAll().size();
        // set the field null
        docStore.setCode(null);

        // Create the DocStore, which fails.

        restDocStoreMockMvc.perform(post("/api/doc-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docStore)))
            .andExpect(status().isBadRequest());

        List<DocStore> docStoreList = docStoreRepository.findAll();
        assertThat(docStoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = docStoreRepository.findAll().size();
        // set the field null
        docStore.setTitle(null);

        // Create the DocStore, which fails.

        restDocStoreMockMvc.perform(post("/api/doc-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docStore)))
            .andExpect(status().isBadRequest());

        List<DocStore> docStoreList = docStoreRepository.findAll();
        assertThat(docStoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = docStoreRepository.findAll().size();
        // set the field null
        docStore.setDescription(null);

        // Create the DocStore, which fails.

        restDocStoreMockMvc.perform(post("/api/doc-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docStore)))
            .andExpect(status().isBadRequest());

        List<DocStore> docStoreList = docStoreRepository.findAll();
        assertThat(docStoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = docStoreRepository.findAll().size();
        // set the field null
        docStore.setCreationDate(null);

        // Create the DocStore, which fails.

        restDocStoreMockMvc.perform(post("/api/doc-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docStore)))
            .andExpect(status().isBadRequest());

        List<DocStore> docStoreList = docStoreRepository.findAll();
        assertThat(docStoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocStores() throws Exception {
        // Initialize the database
        docStoreRepository.saveAndFlush(docStore);

        // Get all the docStoreList
        restDocStoreMockMvc.perform(get("/api/doc-stores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(docStore.getId().intValue())))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].contentBinaryContentType").value(hasItem(DEFAULT_CONTENT_BINARY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].contentBinary").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT_BINARY))))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].contentTextContentType").value(hasItem(DEFAULT_CONTENT_TEXT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].contentText").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT_TEXT))))
            .andExpect(jsonPath("$.[*].mimeType").value(hasItem(DEFAULT_MIME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())));
    }

    @Test
    @Transactional
    public void getDocStore() throws Exception {
        // Initialize the database
        docStoreRepository.saveAndFlush(docStore);

        // Get the docStore
        restDocStoreMockMvc.perform(get("/api/doc-stores/{id}", docStore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(docStore.getId().intValue()))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.contentBinaryContentType").value(DEFAULT_CONTENT_BINARY_CONTENT_TYPE))
            .andExpect(jsonPath("$.contentBinary").value(Base64Utils.encodeToString(DEFAULT_CONTENT_BINARY)))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.contentTextContentType").value(DEFAULT_CONTENT_TEXT_CONTENT_TYPE))
            .andExpect(jsonPath("$.contentText").value(Base64Utils.encodeToString(DEFAULT_CONTENT_TEXT)))
            .andExpect(jsonPath("$.mimeType").value(DEFAULT_MIME_TYPE.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDocStore() throws Exception {
        // Get the docStore
        restDocStoreMockMvc.perform(get("/api/doc-stores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocStore() throws Exception {
        // Initialize the database
        docStoreService.save(docStore);

        int databaseSizeBeforeUpdate = docStoreRepository.findAll().size();

        // Update the docStore
        DocStore updatedDocStore = docStoreRepository.findOne(docStore.getId());
        // Disconnect from session so that the updates on updatedDocStore are not directly saved in db
        em.detach(updatedDocStore);
        updatedDocStore
            .locale(UPDATED_LOCALE)
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .contentBinary(UPDATED_CONTENT_BINARY)
            .contentBinaryContentType(UPDATED_CONTENT_BINARY_CONTENT_TYPE)
            .creationDate(UPDATED_CREATION_DATE)
            .contentText(UPDATED_CONTENT_TEXT)
            .contentTextContentType(UPDATED_CONTENT_TEXT_CONTENT_TYPE)
            .mimeType(UPDATED_MIME_TYPE)
            .path(UPDATED_PATH);

        restDocStoreMockMvc.perform(put("/api/doc-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocStore)))
            .andExpect(status().isOk());

        // Validate the DocStore in the database
        List<DocStore> docStoreList = docStoreRepository.findAll();
        assertThat(docStoreList).hasSize(databaseSizeBeforeUpdate);
        DocStore testDocStore = docStoreList.get(docStoreList.size() - 1);
        assertThat(testDocStore.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testDocStore.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDocStore.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDocStore.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDocStore.getContentBinary()).isEqualTo(UPDATED_CONTENT_BINARY);
        assertThat(testDocStore.getContentBinaryContentType()).isEqualTo(UPDATED_CONTENT_BINARY_CONTENT_TYPE);
        assertThat(testDocStore.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testDocStore.getContentText()).isEqualTo(UPDATED_CONTENT_TEXT);
        assertThat(testDocStore.getContentTextContentType()).isEqualTo(UPDATED_CONTENT_TEXT_CONTENT_TYPE);
        assertThat(testDocStore.getMimeType()).isEqualTo(UPDATED_MIME_TYPE);
        assertThat(testDocStore.getPath()).isEqualTo(UPDATED_PATH);
    }

    @Test
    @Transactional
    public void updateNonExistingDocStore() throws Exception {
        int databaseSizeBeforeUpdate = docStoreRepository.findAll().size();

        // Create the DocStore

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDocStoreMockMvc.perform(put("/api/doc-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docStore)))
            .andExpect(status().isCreated());

        // Validate the DocStore in the database
        List<DocStore> docStoreList = docStoreRepository.findAll();
        assertThat(docStoreList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDocStore() throws Exception {
        // Initialize the database
        docStoreService.save(docStore);

        int databaseSizeBeforeDelete = docStoreRepository.findAll().size();

        // Get the docStore
        restDocStoreMockMvc.perform(delete("/api/doc-stores/{id}", docStore.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DocStore> docStoreList = docStoreRepository.findAll();
        assertThat(docStoreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocStore.class);
        DocStore docStore1 = new DocStore();
        docStore1.setId(1L);
        DocStore docStore2 = new DocStore();
        docStore2.setId(docStore1.getId());
        assertThat(docStore1).isEqualTo(docStore2);
        docStore2.setId(2L);
        assertThat(docStore1).isNotEqualTo(docStore2);
        docStore1.setId(null);
        assertThat(docStore1).isNotEqualTo(docStore2);
    }
}
