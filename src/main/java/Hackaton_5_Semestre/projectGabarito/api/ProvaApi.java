package Hackaton_5_Semestre.projectGabarito.api;

import Hackaton_5_Semestre.projectGabarito.model.Prova;
import Hackaton_5_Semestre.projectGabarito.service.ProvaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/prova")
public class ProvaApi {

    private final ProvaService service;

    @GetMapping
    public ResponseEntity<List<Prova>> listarTodos() {
        List<Prova> provas = service.listarTodos();
        provas.forEach(prova -> prova.getQuestoes().forEach(questao -> questao.getAlternativas()));
        return ResponseEntity.ok(provas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prova> buscarPorId(@PathVariable Long id) {
        Prova prova = service.buscarPorId(id);
        if (prova != null) {
            prova.getQuestoes().forEach(questao -> questao.getAlternativas());
            return ResponseEntity.ok(prova);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/turma/{turmaId}")
    public ResponseEntity<List<Prova>> listarPorTurma(@PathVariable Long turmaId) {
        List<Prova> provas = service.listarTodos()
                .stream()
                .filter(p -> p.getTurma() != null && p.getTurma().getId().equals(turmaId))
                .collect(Collectors.toList());

        provas.forEach(prova -> prova.getQuestoes().forEach(questao -> questao.getAlternativas()));

        return ResponseEntity.ok(provas);
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Prova prova) {
        service.salvar(prova);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> alterar(@RequestBody Prova prova) {
        if (prova.getId() == null)
            return ResponseEntity.badRequest().build();
        service.salvar(prova);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        service.deletarPorId(id);
        return ResponseEntity.ok().build();
    }
}
