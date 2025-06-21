package Hackaton_5_Semestre.projectGabarito.service;

import Hackaton_5_Semestre.projectGabarito.model.RespostasAluno;
import Hackaton_5_Semestre.projectGabarito.repository.RespostasAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RespostasAlunoService {

    @Autowired
    private RespostasAlunoRepository repository;

    public List<RespostasAluno> listarTodos() {
        return repository.findAll();
    }

    public Optional<RespostasAluno> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public RespostasAluno salvar(RespostasAluno respostasAluno) {
        return repository.save(respostasAluno);
    }

    public void deletarPorId(Long id) {
        repository.deleteById(id);
    }

    public List<RespostasAluno> buscarPorAluno(Long alunoId) {
        return repository.findByAlunoId(alunoId);
    }

    /*
    public List<RespostasAluno> buscarPorProva(Long provaId) {
        return repository.findByProvaId(provaId);
    }
    */
}
