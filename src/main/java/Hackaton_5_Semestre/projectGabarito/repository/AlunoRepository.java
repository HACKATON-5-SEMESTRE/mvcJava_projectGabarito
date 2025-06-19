package Hackaton_5_Semestre.projectGabarito.repository;

import Hackaton_5_Semestre.projectGabarito.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
