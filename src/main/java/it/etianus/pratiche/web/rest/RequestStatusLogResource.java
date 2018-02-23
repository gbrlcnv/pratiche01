package it.etianus.pratiche.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.etianus.pratiche.domain.RequestStatusLog;
import it.etianus.pratiche.service.RequestStatusLogService;
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
 * REST controller for managing RequestStatusLog.
 */
@RestController
@RequestMapping("/api")
public class RequestStatusLogResource {

    private final Logger log = LoggerFactory.getLogger(RequestStatusLogResource.class);

    private static final String ENTITY_NAME = "requestStatusLog";

    private final RequestStatusLogService requestStatusLogService;

    public RequestStatusLogResource(RequestStatusLogService requestStatusLogService) {
        this.requestStatusLogService = requestStatusLogService;
    }

    /**
     * POST  /request-status-logs : Create a new requestStatusLog.
     *
     * @param requestStatusLog the requestStatusLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new requestStatusLog, or with status 400 (Bad Request) if the requestStatusLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/request-status-logs")
    @Timed
    public ResponseEntity<RequestStatusLog> createRequestStatusLog(@Valid @RequestBody RequestStatusLog requestStatusLog) throws URISyntaxException {
        log.debug("REST request to save RequestStatusLog : {}", requestStatusLog);
        if (requestStatusLog.getId() != null) {
            throw new BadRequestAlertException("A new requestStatusLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestStatusLog result = requestStatusLogService.save(requestStatusLog);
        return ResponseEntity.created(new URI("/api/request-status-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /request-status-logs : Updates an existing requestStatusLog.
     *
     * @param requestStatusLog the requestStatusLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated requestStatusLog,
     * or with status 400 (Bad Request) if the requestStatusLog is not valid,
     * or with status 500 (Internal Server Error) if the requestStatusLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/request-status-logs")
    @Timed
    public ResponseEntity<RequestStatusLog> updateRequestStatusLog(@Valid @RequestBody RequestStatusLog requestStatusLog) throws URISyntaxException {
        log.debug("REST request to update RequestStatusLog : {}", requestStatusLog);
        if (requestStatusLog.getId() == null) {
            return createRequestStatusLog(requestStatusLog);
        }
        RequestStatusLog result = requestStatusLogService.save(requestStatusLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requestStatusLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /request-status-logs : get all the requestStatusLogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of requestStatusLogs in body
     */
    @GetMapping("/request-status-logs")
    @Timed
    public List<RequestStatusLog> getAllRequestStatusLogs() {
        log.debug("REST request to get all RequestStatusLogs");
        return requestStatusLogService.findAll();
        }

    /**
     * GET  /request-status-logs/:id : get the "id" requestStatusLog.
     *
     * @param id the id of the requestStatusLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the requestStatusLog, or with status 404 (Not Found)
     */
    @GetMapping("/request-status-logs/{id}")
    @Timed
    public ResponseEntity<RequestStatusLog> getRequestStatusLog(@PathVariable Long id) {
        log.debug("REST request to get RequestStatusLog : {}", id);
        RequestStatusLog requestStatusLog = requestStatusLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(requestStatusLog));
    }

    /**
     * DELETE  /request-status-logs/:id : delete the "id" requestStatusLog.
     *
     * @param id the id of the requestStatusLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/request-status-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteRequestStatusLog(@PathVariable Long id) {
        log.debug("REST request to delete RequestStatusLog : {}", id);
        requestStatusLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
