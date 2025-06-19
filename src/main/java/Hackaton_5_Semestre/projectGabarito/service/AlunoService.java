package Hackaton_5_Semestre.projectGabarito.service;

import Hackaton_5_Semestre.projectGabarito.model.Aluno;
import Hackaton_5_Semestre.projectGabarito.repository.AlunoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Transactional
    public void salvar(Aluno aluno) {
        alunoRepository.save(aluno);
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id).get();
    }

    public void deletarPorId(Long id) {
        alunoRepository.deleteById(id);
    }
}
