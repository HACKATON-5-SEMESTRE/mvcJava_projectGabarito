    package Hackaton_5_Semestre.projectGabarito.repository;

    import Hackaton_5_Semestre.projectGabarito.model.Disciplina;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.Optional;

    public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
        Optional<Disciplina> findByNome(String nome);

    }
