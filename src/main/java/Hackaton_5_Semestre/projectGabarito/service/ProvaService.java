// ProvaService.java
package Hackaton_5_Semestre.projectGabarito.service;

import Hackaton_5_Semestre.projectGabarito.model.Prova;
import Hackaton_5_Semestre.projectGabarito.model.Usuario;
import Hackaton_5_Semestre.projectGabarito.repository.ProvaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvaService {

    @Autowired
    private ProvaRepository repository;

    @Transactional
    public Prova salvar(Prova prova) {
        // Atribuir usuário logado
        var usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        prova.setUsuario(usuario);

        // Configurar a relação bidirecional: cada questão aponta para a prova
        if (prova.getQuestoes() != null) {
            prova.getQuestoes().forEach(q -> q.setProva(prova));
        }

        return repository.save(prova);
    }

    public List<Prova> listarTodos() {
        return repository.findAll();
    }

    public Prova buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Prova não encontrada"));
    }

    @Transactional
    public void deletarPorId(Long id) {
        repository.deleteById(id);
    }
}
