package Hackaton_5_Semestre.projectGabarito.service;

import Hackaton_5_Semestre.projectGabarito.model.Aluno;
import Hackaton_5_Semestre.projectGabarito.model.Usuario;
import Hackaton_5_Semestre.projectGabarito.repository.AlunoRepository;
import Hackaton_5_Semestre.projectGabarito.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void salvar(Aluno aluno) {
        // Verifica RA duplicado
        Optional<Aluno> alunoExistente = alunoRepository.findByRA(aluno.getRA());
        if (alunoExistente.isPresent() && !alunoExistente.get().getId().equals(aluno.getId())) {
            throw new RuntimeException("RA já cadastrado para outro aluno.");
        }

        // Verifica login duplicado
        Usuario usuario = aluno.getUsuario();
        if (usuario != null) {
            Optional<Usuario> usuarioExistente = usuarioRepository.findByLogin(usuario.getLogin());
            if (usuarioExistente.isPresent() && !usuarioExistente.get().getId().equals(usuario.getId())) {
                throw new RuntimeException("Login já cadastrado para outro usuário.");
            }
            usuarioRepository.save(usuario); // garante salvar/atualizar o usuário
        }

        alunoRepository.save(aluno);
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarPorId(Long id) {
        return alunoRepository.findById(id);
    }

    @Transactional
    public void deletarPorId(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new IllegalArgumentException("Aluno não encontrado: " + id);
        }
        alunoRepository.deleteById(id);
    }
}
