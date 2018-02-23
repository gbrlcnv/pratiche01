package it.etianus.pratiche.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.etianus.pratiche.domain.RequestComment;
import it.etianus.pratiche.service.RequestCommentService;
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
 * REST controller for managing RequestComment.
 */
@RestController
@RequestMapping("/api")
public class RequestCommentResource {

    private final Logger log = LoggerFactory.getLogger(RequestCommentResource.class);

    private static final String ENTITY_NAME = "requestComment";

    private final RequestCommentService requestCommentService;

    public RequestCommentResource(RequestCommentService requestCommentService) {
        this.requestCommentService = requestCommentService;
    }

    /**
     * POST  /request-comments : Create a new requestComment.
     *
     * @param requestComment the requestComment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new requestComment, or with status 400 (Bad Request) if the requestComment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/request-comments")
    @Timed
    public ResponseEntity<RequestComment> createRequestComment(@Valid @RequestBody RequestComment requestComment) throws URISyntaxException {
        log.debug("REST request to save RequestComment : {}", requestComment);
        if (requestComment.getId() != null) {
            throw new BadRequestAlertException("A new requestComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestComment result = requestCommentService.save(requestComment);
        return ResponseEntity.created(new URI("/api/request-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /request-comments : Updates an existing requestComment.
     *
     * @param requestComment the requestComment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated requestComment,
     * or with status 400 (Bad Request) if the requestComment is not valid,
     * or with status 500 (Internal Server Error) if the requestComment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/request-comments")
    @Timed
    public ResponseEntity<RequestComment> updateRequestComment(@Valid @RequestBody RequestComment requestComment) throws URISyntaxException {
        log.debug("REST request to update RequestComment : {}", requestComment);
        if (requestComment.getId() == null) {
            return createRequestComment(requestComment);
        }
        RequestComment result = requestCommentService.save(requestComment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requestComment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /request-comments : get all the requestComments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of requestComments in body
     */
    @GetMapping("/request-comments")
    @Timed
    public List<RequestComment> getAllRequestComments() {
        log.debug("REST request to get all RequestComments");
        return requestCommentService.findAll();
        }

    /**
     * GET  /request-comments/:id : get the "id" requestComment.
     *
     * @param id the id of the requestComment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the requestComment, or with status 404 (Not Found)
     */
    @GetMapping("/request-comments/{id}")
    @Timed
    public ResponseEntity<RequestComment> getRequestComment(@PathVariable Long id) {
        log.debug("REST request to get RequestComment : {}", id);
        RequestComment requestComment = requestCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(requestComment));
    }

    /**
     * DELETE  /request-comments/:id : delete the "id" requestComment.
     *
     * @param id the id of the requestComment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/request-comments/{id}")
    @Timed
    public ResponseEntity<Void> deleteRequestComment(@PathVariable Long id) {
        log.debug("REST request to delete RequestComment : {}", id);
        requestCommentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
