package Hackaton_5_Semestre.projectGabarito.service;

import Hackaton_5_Semestre.projectGabarito.model.Questoes;
import Hackaton_5_Semestre.projectGabarito.repository.ProvaRepository;
import Hackaton_5_Semestre.projectGabarito.repository.QuestaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestaoService {

    @Autowired
    private QuestaoRepository repository;

    @Autowired
    private ProvaRepository provaRepository;

    public List<Questoes> listarPorProva(Long provaId) {
        return repository.findByProvaId(provaId);
    }

    public Optional<Questoes> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void salvar(Questoes questao) {

        Long provaId = questao.getProva() != null ? questao.getProva().getId() : null;
        if (provaId == null) {
            throw new IllegalArgumentException("Prova não informada.");
        }

        var prova = provaRepository.findById(provaId)
                .orElseThrow(() -> new IllegalArgumentException("Prova não encontrada."));

        if (questao.getPeso() > prova.getValorTotal()) {
            throw new IllegalArgumentException("Peso da questão excede o valor restante da prova.");
        }

        prova.setValorTotal(prova.getValorTotal() - questao.getPeso());

        questao.setProva(prova);

        repository.save(questao);
        provaRepository.save(prova);

        System.out.println("provaId = " + provaId);
        System.out.println("prova encontrado = " + prova);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
