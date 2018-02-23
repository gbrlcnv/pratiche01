package it.etianus.pratiche.repository;

import it.etianus.pratiche.domain.RequestRicorsoTributario;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RequestRicorsoTributario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestRicorsoTributarioRepository extends JpaRepository<RequestRicorsoTributario, Long> {

}
