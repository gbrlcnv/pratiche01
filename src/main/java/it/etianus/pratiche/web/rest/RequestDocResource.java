package it.etianus.pratiche.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.etianus.pratiche.domain.RequestDoc;
import it.etianus.pratiche.service.RequestDocService;
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
 * REST controller for managing RequestDoc.
 */
@RestController
@RequestMapping("/api")
public class RequestDocResource {

    private final Logger log = LoggerFactory.getLogger(RequestDocResource.class);

    private static final String ENTITY_NAME = "requestDoc";

    private final RequestDocService requestDocService;

    public RequestDocResource(RequestDocService requestDocService) {
        this.requestDocService = requestDocService;
    }

    /**
     * POST  /request-docs : Create a new requestDoc.
     *
     * @param requestDoc the requestDoc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new requestDoc, or with status 400 (Bad Request) if the requestDoc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/request-docs")
    @Timed
    public ResponseEntity<RequestDoc> createRequestDoc(@Valid @RequestBody RequestDoc requestDoc) throws URISyntaxException {
        log.debug("REST request to save RequestDoc : {}", requestDoc);
        if (requestDoc.getId() != null) {
            throw new BadRequestAlertException("A new requestDoc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestDoc result = requestDocService.save(requestDoc);
        return ResponseEntity.created(new URI("/api/request-docs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /request-docs : Updates an existing requestDoc.
     *
     * @param requestDoc the requestDoc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated requestDoc,
     * or with status 400 (Bad Request) if the requestDoc is not valid,
     * or with status 500 (Internal Server Error) if the requestDoc couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/request-docs")
    @Timed
    public ResponseEntity<RequestDoc> updateRequestDoc(@Valid @RequestBody RequestDoc requestDoc) throws URISyntaxException {
        log.debug("REST request to update RequestDoc : {}", requestDoc);
        if (requestDoc.getId() == null) {
            return createRequestDoc(requestDoc);
        }
        RequestDoc result = requestDocService.save(requestDoc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requestDoc.getId().toString()))
            .body(result);
    }

    /**
     * GET  /request-docs : get all the requestDocs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of requestDocs in body
     */
    @GetMapping("/request-docs")
    @Timed
    public List<RequestDoc> getAllRequestDocs() {
        log.debug("REST request to get all RequestDocs");
        return requestDocService.findAll();
        }

    /**
     * GET  /request-docs/:id : get the "id" requestDoc.
     *
     * @param id the id of the requestDoc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the requestDoc, or with status 404 (Not Found)
     */
    @GetMapping("/request-docs/{id}")
    @Timed
    public ResponseEntity<RequestDoc> getRequestDoc(@PathVariable Long id) {
        log.debug("REST request to get RequestDoc : {}", id);
        RequestDoc requestDoc = requestDocService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(requestDoc));
    }

    /**
     * DELETE  /request-docs/:id : delete the "id" requestDoc.
     *
     * @param id the id of the requestDoc to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/request-docs/{id}")
    @Timed
    public ResponseEntity<Void> deleteRequestDoc(@PathVariable Long id) {
        log.debug("REST request to delete RequestDoc : {}", id);
        requestDocService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
