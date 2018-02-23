package it.etianus.pratiche.service;

import it.etianus.pratiche.domain.RequestStatus;
import it.etianus.pratiche.repository.RequestStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing RequestStatus.
 */
@Service
@Transactional
public class RequestStatusService {

    private final Logger log = LoggerFactory.getLogger(RequestStatusService.class);

    private final RequestStatusRepository requestStatusRepository;

    public RequestStatusService(RequestStatusRepository requestStatusRepository) {
        this.requestStatusRepository = requestStatusRepository;
    }

    /**
     * Save a requestStatus.
     *
     * @param requestStatus the entity to save
     * @return the persisted entity
     */
    public RequestStatus save(RequestStatus requestStatus) {
        log.debug("Request to save RequestStatus : {}", requestStatus);
        return requestStatusRepository.save(requestStatus);
    }

    /**
     * Get all the requestStatuses.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RequestStatus> findAll() {
        log.debug("Request to get all RequestStatuses");
        return requestStatusRepository.findAll();
    }

    /**
     * Get one requestStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RequestStatus findOne(Long id) {
        log.debug("Request to get RequestStatus : {}", id);
        return requestStatusRepository.findOne(id);
    }

    /**
     * Delete the requestStatus by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RequestStatus : {}", id);
        requestStatusRepository.delete(id);
    }
}
