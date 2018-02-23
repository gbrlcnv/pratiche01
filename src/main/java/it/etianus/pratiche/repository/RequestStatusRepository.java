package it.etianus.pratiche.repository;

import it.etianus.pratiche.domain.RequestStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RequestStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestStatusRepository extends JpaRepository<RequestStatus, Long> {

}
