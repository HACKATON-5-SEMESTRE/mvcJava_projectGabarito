package Hackaton_5_Semestre.projectGabarito.api;

import Hackaton_5_Semestre.projectGabarito.model.Turma;
import Hackaton_5_Semestre.projectGabarito.service.TurmaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/turma")
public class TurmaApi {

    private final TurmaService service;

    @GetMapping
    public ResponseEntity<List<Turma>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turma> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Turma turma) {
        service.salvar(turma);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> alterar(@RequestBody Turma turma) {
        if (turma.getId() == null)
            return ResponseEntity.badRequest().build();
        service.salvar(turma);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        service.deletarPorId(id);
        return ResponseEntity.ok().build();
    }
}
