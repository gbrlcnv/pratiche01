package it.etianus.pratiche.repository;

import it.etianus.pratiche.domain.DocStore;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DocStore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocStoreRepository extends JpaRepository<DocStore, Long> {

}
