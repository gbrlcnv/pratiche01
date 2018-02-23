package it.etianus.pratiche.service;

import it.etianus.pratiche.domain.RequestDoc;
import it.etianus.pratiche.repository.RequestDocRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing RequestDoc.
 */
@Service
@Transactional
public class RequestDocService {

    private final Logger log = LoggerFactory.getLogger(RequestDocService.class);

    private final RequestDocRepository requestDocRepository;

    public RequestDocService(RequestDocRepository requestDocRepository) {
        this.requestDocRepository = requestDocRepository;
    }

    /**
     * Save a requestDoc.
     *
     * @param requestDoc the entity to save
     * @return the persisted entity
     */
    public RequestDoc save(RequestDoc requestDoc) {
        log.debug("Request to save RequestDoc : {}", requestDoc);
        return requestDocRepository.save(requestDoc);
    }

    /**
     * Get all the requestDocs.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RequestDoc> findAll() {
        log.debug("Request to get all RequestDocs");
        return requestDocRepository.findAll();
    }

    /**
     * Get one requestDoc by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RequestDoc findOne(Long id) {
        log.debug("Request to get RequestDoc : {}", id);
        return requestDocRepository.findOne(id);
    }

    /**
     * Delete the requestDoc by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RequestDoc : {}", id);
        requestDocRepository.delete(id);
    }
}
