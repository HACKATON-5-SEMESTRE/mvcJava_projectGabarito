package Hackaton_5_Semestre.projectGabarito.repository;

import Hackaton_5_Semestre.projectGabarito.model.RespostasAluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespostasAlunoRepository extends JpaRepository<RespostasAluno, Long> {
    List<RespostasAluno> findByAluno_Id(Long alunoId);
    List<RespostasAluno> findByProva_Id(Long provaId);
}
