package it.etianus.pratiche.repository;

import it.etianus.pratiche.domain.RequestType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RequestType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestTypeRepository extends JpaRepository<RequestType, Long> {

}
