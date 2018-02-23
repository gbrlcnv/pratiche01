package it.etianus.pratiche.service;

import it.etianus.pratiche.domain.RequestType;
import it.etianus.pratiche.repository.RequestTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing RequestType.
 */
@Service
@Transactional
public class RequestTypeService {

    private final Logger log = LoggerFactory.getLogger(RequestTypeService.class);

    private final RequestTypeRepository requestTypeRepository;

    public RequestTypeService(RequestTypeRepository requestTypeRepository) {
        this.requestTypeRepository = requestTypeRepository;
    }

    /**
     * Save a requestType.
     *
     * @param requestType the entity to save
     * @return the persisted entity
     */
    public RequestType save(RequestType requestType) {
        log.debug("Request to save RequestType : {}", requestType);
        return requestTypeRepository.save(requestType);
    }

    /**
     * Get all the requestTypes.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RequestType> findAll() {
        log.debug("Request to get all RequestTypes");
        return requestTypeRepository.findAll();
    }

    /**
     * Get one requestType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RequestType findOne(Long id) {
        log.debug("Request to get RequestType : {}", id);
        return requestTypeRepository.findOne(id);
    }

    /**
     * Delete the requestType by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RequestType : {}", id);
        requestTypeRepository.delete(id);
    }
}
