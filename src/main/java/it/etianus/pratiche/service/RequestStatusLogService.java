package it.etianus.pratiche.service;

import it.etianus.pratiche.domain.RequestStatusLog;
import it.etianus.pratiche.repository.RequestStatusLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing RequestStatusLog.
 */
@Service
@Transactional
public class RequestStatusLogService {

    private final Logger log = LoggerFactory.getLogger(RequestStatusLogService.class);

    private final RequestStatusLogRepository requestStatusLogRepository;

    public RequestStatusLogService(RequestStatusLogRepository requestStatusLogRepository) {
        this.requestStatusLogRepository = requestStatusLogRepository;
    }

    /**
     * Save a requestStatusLog.
     *
     * @param requestStatusLog the entity to save
     * @return the persisted entity
     */
    public RequestStatusLog save(RequestStatusLog requestStatusLog) {
        log.debug("Request to save RequestStatusLog : {}", requestStatusLog);
        return requestStatusLogRepository.save(requestStatusLog);
    }

    /**
     * Get all the requestStatusLogs.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RequestStatusLog> findAll() {
        log.debug("Request to get all RequestStatusLogs");
        return requestStatusLogRepository.findAll();
    }

    /**
     * Get one requestStatusLog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RequestStatusLog findOne(Long id) {
        log.debug("Request to get RequestStatusLog : {}", id);
        return requestStatusLogRepository.findOne(id);
    }

    /**
     * Delete the requestStatusLog by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RequestStatusLog : {}", id);
        requestStatusLogRepository.delete(id);
    }
}
