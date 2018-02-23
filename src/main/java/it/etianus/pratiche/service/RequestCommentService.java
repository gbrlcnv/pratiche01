package it.etianus.pratiche.service;

import it.etianus.pratiche.domain.RequestComment;
import it.etianus.pratiche.repository.RequestCommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing RequestComment.
 */
@Service
@Transactional
public class RequestCommentService {

    private final Logger log = LoggerFactory.getLogger(RequestCommentService.class);

    private final RequestCommentRepository requestCommentRepository;

    public RequestCommentService(RequestCommentRepository requestCommentRepository) {
        this.requestCommentRepository = requestCommentRepository;
    }

    /**
     * Save a requestComment.
     *
     * @param requestComment the entity to save
     * @return the persisted entity
     */
    public RequestComment save(RequestComment requestComment) {
        log.debug("Request to save RequestComment : {}", requestComment);
        return requestCommentRepository.save(requestComment);
    }

    /**
     * Get all the requestComments.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RequestComment> findAll() {
        log.debug("Request to get all RequestComments");
        return requestCommentRepository.findAll();
    }

    /**
     * Get one requestComment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RequestComment findOne(Long id) {
        log.debug("Request to get RequestComment : {}", id);
        return requestCommentRepository.findOne(id);
    }

    /**
     * Delete the requestComment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RequestComment : {}", id);
        requestCommentRepository.delete(id);
    }
}
