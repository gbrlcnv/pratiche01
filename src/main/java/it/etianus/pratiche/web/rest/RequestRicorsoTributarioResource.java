package it.etianus.pratiche.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.etianus.pratiche.domain.RequestRicorsoTributario;
import it.etianus.pratiche.service.RequestRicorsoTributarioService;
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
 * REST controller for managing RequestRicorsoTributario.
 */
@RestController
@RequestMapping("/api")
public class RequestRicorsoTributarioResource {

    private final Logger log = LoggerFactory.getLogger(RequestRicorsoTributarioResource.class);

    private static final String ENTITY_NAME = "requestRicorsoTributario";

    private final RequestRicorsoTributarioService requestRicorsoTributarioService;

    public RequestRicorsoTributarioResource(RequestRicorsoTributarioService requestRicorsoTributarioService) {
        this.requestRicorsoTributarioService = requestRicorsoTributarioService;
    }

    /**
     * POST  /request-ricorso-tributarios : Create a new requestRicorsoTributario.
     *
     * @param requestRicorsoTributario the requestRicorsoTributario to create
     * @return the ResponseEntity with status 201 (Created) and with body the new requestRicorsoTributario, or with status 400 (Bad Request) if the requestRicorsoTributario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/request-ricorso-tributarios")
    @Timed
    public ResponseEntity<RequestRicorsoTributario> createRequestRicorsoTributario(@Valid @RequestBody RequestRicorsoTributario requestRicorsoTributario) throws URISyntaxException {
        log.debug("REST request to save RequestRicorsoTributario : {}", requestRicorsoTributario);
        if (requestRicorsoTributario.getId() != null) {
            throw new BadRequestAlertException("A new requestRicorsoTributario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestRicorsoTributario result = requestRicorsoTributarioService.save(requestRicorsoTributario);
        return ResponseEntity.created(new URI("/api/request-ricorso-tributarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /request-ricorso-tributarios : Updates an existing requestRicorsoTributario.
     *
     * @param requestRicorsoTributario the requestRicorsoTributario to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated requestRicorsoTributario,
     * or with status 400 (Bad Request) if the requestRicorsoTributario is not valid,
     * or with status 500 (Internal Server Error) if the requestRicorsoTributario couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/request-ricorso-tributarios")
    @Timed
    public ResponseEntity<RequestRicorsoTributario> updateRequestRicorsoTributario(@Valid @RequestBody RequestRicorsoTributario requestRicorsoTributario) throws URISyntaxException {
        log.debug("REST request to update RequestRicorsoTributario : {}", requestRicorsoTributario);
        if (requestRicorsoTributario.getId() == null) {
            return createRequestRicorsoTributario(requestRicorsoTributario);
        }
        RequestRicorsoTributario result = requestRicorsoTributarioService.save(requestRicorsoTributario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requestRicorsoTributario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /request-ricorso-tributarios : get all the requestRicorsoTributarios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of requestRicorsoTributarios in body
     */
    @GetMapping("/request-ricorso-tributarios")
    @Timed
    public List<RequestRicorsoTributario> getAllRequestRicorsoTributarios() {
        log.debug("REST request to get all RequestRicorsoTributarios");
        return requestRicorsoTributarioService.findAll();
        }

    /**
     * GET  /request-ricorso-tributarios/:id : get the "id" requestRicorsoTributario.
     *
     * @param id the id of the requestRicorsoTributario to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the requestRicorsoTributario, or with status 404 (Not Found)
     */
    @GetMapping("/request-ricorso-tributarios/{id}")
    @Timed
    public ResponseEntity<RequestRicorsoTributario> getRequestRicorsoTributario(@PathVariable Long id) {
        log.debug("REST request to get RequestRicorsoTributario : {}", id);
        RequestRicorsoTributario requestRicorsoTributario = requestRicorsoTributarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(requestRicorsoTributario));
    }

    /**
     * DELETE  /request-ricorso-tributarios/:id : delete the "id" requestRicorsoTributario.
     *
     * @param id the id of the requestRicorsoTributario to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/request-ricorso-tributarios/{id}")
    @Timed
    public ResponseEntity<Void> deleteRequestRicorsoTributario(@PathVariable Long id) {
        log.debug("REST request to delete RequestRicorsoTributario : {}", id);
        requestRicorsoTributarioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
