// ProvaRepository.java
package Hackaton_5_Semestre.projectGabarito.repository;

import Hackaton_5_Semestre.projectGabarito.model.Prova;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvaRepository extends JpaRepository<Prova, Long> {
}
