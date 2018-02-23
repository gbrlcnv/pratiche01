package it.etianus.pratiche.service;

import it.etianus.pratiche.domain.RequestRicorsoTributario;
import it.etianus.pratiche.repository.RequestRicorsoTributarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing RequestRicorsoTributario.
 */
@Service
@Transactional
public class RequestRicorsoTributarioService {

    private final Logger log = LoggerFactory.getLogger(RequestRicorsoTributarioService.class);

    private final RequestRicorsoTributarioRepository requestRicorsoTributarioRepository;

    public RequestRicorsoTributarioService(RequestRicorsoTributarioRepository requestRicorsoTributarioRepository) {
        this.requestRicorsoTributarioRepository = requestRicorsoTributarioRepository;
    }

    /**
     * Save a requestRicorsoTributario.
     *
     * @param requestRicorsoTributario the entity to save
     * @return the persisted entity
     */
    public RequestRicorsoTributario save(RequestRicorsoTributario requestRicorsoTributario) {
        log.debug("Request to save RequestRicorsoTributario : {}", requestRicorsoTributario);
        return requestRicorsoTributarioRepository.save(requestRicorsoTributario);
    }

    /**
     * Get all the requestRicorsoTributarios.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RequestRicorsoTributario> findAll() {
        log.debug("Request to get all RequestRicorsoTributarios");
        return requestRicorsoTributarioRepository.findAll();
    }

    /**
     * Get one requestRicorsoTributario by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RequestRicorsoTributario findOne(Long id) {
        log.debug("Request to get RequestRicorsoTributario : {}", id);
        return requestRicorsoTributarioRepository.findOne(id);
    }

    /**
     * Delete the requestRicorsoTributario by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RequestRicorsoTributario : {}", id);
        requestRicorsoTributarioRepository.delete(id);
    }
}
