// QuestaoRepository.java
package Hackaton_5_Semestre.projectGabarito.repository;

import Hackaton_5_Semestre.projectGabarito.model.Questoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestaoRepository extends JpaRepository<Questoes, Long> {
    List<Questoes> findByProvaId(Long provaId);
}
