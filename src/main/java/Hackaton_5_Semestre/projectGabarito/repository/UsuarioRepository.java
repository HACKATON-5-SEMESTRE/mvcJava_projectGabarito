package Hackaton_5_Semestre.projectGabarito.repository;

import Hackaton_5_Semestre.projectGabarito.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);
}
