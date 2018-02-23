package it.etianus.pratiche.service;

import it.etianus.pratiche.domain.DocStore;
import it.etianus.pratiche.repository.DocStoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing DocStore.
 */
@Service
@Transactional
public class DocStoreService {

    private final Logger log = LoggerFactory.getLogger(DocStoreService.class);

    private final DocStoreRepository docStoreRepository;

    public DocStoreService(DocStoreRepository docStoreRepository) {
        this.docStoreRepository = docStoreRepository;
    }

    /**
     * Save a docStore.
     *
     * @param docStore the entity to save
     * @return the persisted entity
     */
    public DocStore save(DocStore docStore) {
        log.debug("Request to save DocStore : {}", docStore);
        return docStoreRepository.save(docStore);
    }

    /**
     * Get all the docStores.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DocStore> findAll() {
        log.debug("Request to get all DocStores");
        return docStoreRepository.findAll();
    }

    /**
     * Get one docStore by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DocStore findOne(Long id) {
        log.debug("Request to get DocStore : {}", id);
        return docStoreRepository.findOne(id);
    }

    /**
     * Delete the docStore by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DocStore : {}", id);
        docStoreRepository.delete(id);
    }
}
