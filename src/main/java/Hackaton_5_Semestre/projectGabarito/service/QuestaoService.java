package Hackaton_5_Semestre.projectGabarito.service;

import Hackaton_5_Semestre.projectGabarito.model.Questoes;
import Hackaton_5_Semestre.projectGabarito.repository.QuestaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestaoService {

    @Autowired
    private QuestaoRepository repository;

    public List<Questoes> listarPorProva(Long provaId) {
        return repository.findByProvaId(provaId);
    }

    public Optional<Questoes> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void salvar(Questoes questao) {
        repository.save(questao);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public void salvarTodas(List<Questoes> questoes) {
        repository.saveAll(questoes);
    }
}
