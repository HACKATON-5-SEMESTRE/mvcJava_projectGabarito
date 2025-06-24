package Hackaton_5_Semestre.projectGabarito.service;

import Hackaton_5_Semestre.projectGabarito.model.Disciplina;
import Hackaton_5_Semestre.projectGabarito.repository.DisciplinaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository repository;

    @Transactional
    public void salvar(Disciplina disciplina) {
        repository.save(disciplina);
    }

    public List<Disciplina> listarTodos() {
        return repository.findAll();
    }

    public Optional<Disciplina> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void deletarPorId(Long id) {
        repository.deleteById(id);
    }

    public Optional<Disciplina> buscarPorNome(String nome) {
        return repository.findByNome(nome);
    }
}
