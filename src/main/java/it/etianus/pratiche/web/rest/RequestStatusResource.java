package it.etianus.pratiche.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.etianus.pratiche.domain.RequestStatus;
import it.etianus.pratiche.service.RequestStatusService;
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
 * REST controller for managing RequestStatus.
 */
@RestController
@RequestMapping("/api")
public class RequestStatusResource {

    private final Logger log = LoggerFactory.getLogger(RequestStatusResource.class);

    private static final String ENTITY_NAME = "requestStatus";

    private final RequestStatusService requestStatusService;

    public RequestStatusResource(RequestStatusService requestStatusService) {
        this.requestStatusService = requestStatusService;
    }

    /**
     * POST  /request-statuses : Create a new requestStatus.
     *
     * @param requestStatus the requestStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new requestStatus, or with status 400 (Bad Request) if the requestStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/request-statuses")
    @Timed
    public ResponseEntity<RequestStatus> createRequestStatus(@Valid @RequestBody RequestStatus requestStatus) throws URISyntaxException {
        log.debug("REST request to save RequestStatus : {}", requestStatus);
        if (requestStatus.getId() != null) {
            throw new BadRequestAlertException("A new requestStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestStatus result = requestStatusService.save(requestStatus);
        return ResponseEntity.created(new URI("/api/request-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /request-statuses : Updates an existing requestStatus.
     *
     * @param requestStatus the requestStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated requestStatus,
     * or with status 400 (Bad Request) if the requestStatus is not valid,
     * or with status 500 (Internal Server Error) if the requestStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/request-statuses")
    @Timed
    public ResponseEntity<RequestStatus> updateRequestStatus(@Valid @RequestBody RequestStatus requestStatus) throws URISyntaxException {
        log.debug("REST request to update RequestStatus : {}", requestStatus);
        if (requestStatus.getId() == null) {
            return createRequestStatus(requestStatus);
        }
        RequestStatus result = requestStatusService.save(requestStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requestStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /request-statuses : get all the requestStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of requestStatuses in body
     */
    @GetMapping("/request-statuses")
    @Timed
    public List<RequestStatus> getAllRequestStatuses() {
        log.debug("REST request to get all RequestStatuses");
        return requestStatusService.findAll();
        }

    /**
     * GET  /request-statuses/:id : get the "id" requestStatus.
     *
     * @param id the id of the requestStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the requestStatus, or with status 404 (Not Found)
     */
    @GetMapping("/request-statuses/{id}")
    @Timed
    public ResponseEntity<RequestStatus> getRequestStatus(@PathVariable Long id) {
        log.debug("REST request to get RequestStatus : {}", id);
        RequestStatus requestStatus = requestStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(requestStatus));
    }

    /**
     * DELETE  /request-statuses/:id : delete the "id" requestStatus.
     *
     * @param id the id of the requestStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/request-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteRequestStatus(@PathVariable Long id) {
        log.debug("REST request to delete RequestStatus : {}", id);
        requestStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
