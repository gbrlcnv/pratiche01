package it.etianus.pratiche.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.etianus.pratiche.domain.DocStore;
import it.etianus.pratiche.service.DocStoreService;
import it.etianus.pratiche.web.rest.errors.BadRequestAlertException;
import it.etianus.pratiche.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DocStore.
 */
@RestController
@RequestMapping("/api")
public class DocStoreResource {

    private final Logger log = LoggerFactory.getLogger(DocStoreResource.class);

    private static final String ENTITY_NAME = "docStore";

    private final DocStoreService docStoreService;

    public DocStoreResource(DocStoreService docStoreService) {
        this.docStoreService = docStoreService;
    }

    /**
     * POST  /doc-stores : Create a new docStore.
     *
     * @param docStore the docStore to create
     * @return the ResponseEntity with status 201 (Created) and with body the new docStore, or with status 400 (Bad Request) if the docStore has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/doc-stores")
    @Timed
    public ResponseEntity<DocStore> createDocStore(@Valid @RequestBody DocStore docStore) throws URISyntaxException {
        log.debug("REST request to save DocStore : {}", docStore);
        if (docStore.getId() != null) {
            throw new BadRequestAlertException("A new docStore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocStore result = docStoreService.save(docStore);
        return ResponseEntity.created(new URI("/api/doc-stores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /doc-stores : Updates an existing docStore.
     *
     * @param docStore the docStore to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated docStore,
     * or with status 400 (Bad Request) if the docStore is not valid,
     * or with status 500 (Internal Server Error) if the docStore couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/doc-stores")
    @Timed
    public ResponseEntity<DocStore> updateDocStore(@Valid @RequestBody DocStore docStore) throws URISyntaxException {
        log.debug("REST request to update DocStore : {}", docStore);
        if (docStore.getId() == null) {
            return createDocStore(docStore);
        }
        DocStore result = docStoreService.save(docStore);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, docStore.getId().toString()))
            .body(result);
    }

    /**
     * GET  /doc-stores : get all the docStores.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of docStores in body
     */
    @GetMapping("/doc-stores")
    @Timed
    public List<DocStore> getAllDocStores() {
        log.debug("REST request to get all DocStores");
        return docStoreService.findAll();
        }

    /**
     * GET  /doc-stores/:id : get the "id" docStore.
     *
     * @param id the id of the docStore to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the docStore, or with status 404 (Not Found)
     */
    @GetMapping("/doc-stores/{id}")
    @Timed
    public ResponseEntity<DocStore> getDocStore(@PathVariable Long id) {
        log.debug("REST request to get DocStore : {}", id);
        DocStore docStore = docStoreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(docStore));
    }

    /**
     * DELETE  /doc-stores/:id : delete the "id" docStore.
     *
     * @param id the id of the docStore to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/doc-stores/{id}")
    @Timed
    public ResponseEntity<Void> deleteDocStore(@PathVariable Long id) {
        log.debug("REST request to delete DocStore : {}", id);
        docStoreService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
