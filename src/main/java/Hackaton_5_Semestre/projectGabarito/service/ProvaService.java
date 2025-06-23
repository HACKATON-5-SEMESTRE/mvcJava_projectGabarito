package Hackaton_5_Semestre.projectGabarito.service;

import Hackaton_5_Semestre.projectGabarito.model.Disciplina;
import Hackaton_5_Semestre.projectGabarito.model.Prova;
import Hackaton_5_Semestre.projectGabarito.model.Turma;
import Hackaton_5_Semestre.projectGabarito.model.Usuario;
import Hackaton_5_Semestre.projectGabarito.repository.DisciplinaRepository;
import Hackaton_5_Semestre.projectGabarito.repository.ProvaRepository;
import Hackaton_5_Semestre.projectGabarito.repository.TurmaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProvaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProvaRepository repository;

    public List<Turma> buscarTodasTurmas() {
        return turmaRepository.findAll();
    }

    public List<Disciplina> buscarTodasDisciplinas() {
        return disciplinaRepository.findAll();
    }

    @Transactional
    public void salvar(Prova prova) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        prova.setUsuario(usuario);

        if (prova.getQuestoes() == null) {
            prova.setQuestoes(new ArrayList<>());
        }

        prova.getQuestoes().forEach(resposta -> resposta.setProva(prova));
        repository.save(prova);
    }

    public List<Prova> listarTodos() {
        return repository.findAll();
    }

    public Prova buscarPorId(Long id) {
        Prova prova = repository.findById(id).orElseThrow(() -> new RuntimeException("Prova não encontrada"));
        prova.getQuestoes().size();
        return prova;
    }

    public void deletarPorId(Long id) {
        repository.deleteById(id);
    }

}
