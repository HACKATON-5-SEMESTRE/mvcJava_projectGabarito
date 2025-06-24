package Hackaton_5_Semestre.projectGabarito.api;

import Hackaton_5_Semestre.projectGabarito.model.Disciplina;
import Hackaton_5_Semestre.projectGabarito.service.DisciplinaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/disciplina")
public class DisciplinaApi {

    private final DisciplinaService service;

    @GetMapping
    public ResponseEntity<List<Disciplina>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Disciplina disciplina) {
        service.salvar(disciplina);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> alterar(@RequestBody Disciplina disciplina) {
        if (disciplina.getId() == null)
            return ResponseEntity.badRequest().build();
        service.salvar(disciplina);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        service.deletarPorId(id);
        return ResponseEntity.ok().build();
    }
}
