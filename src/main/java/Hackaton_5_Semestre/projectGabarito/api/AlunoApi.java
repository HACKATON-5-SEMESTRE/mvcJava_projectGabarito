package Hackaton_5_Semestre.projectGabarito.api;

import Hackaton_5_Semestre.projectGabarito.model.Aluno;
import Hackaton_5_Semestre.projectGabarito.service.AlunoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/aluno")
public class AlunoApi {

    private final AlunoService service;

    @GetMapping
    public ResponseEntity<List<Aluno>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Aluno aluno) {
        service.salvar(aluno);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> alterar(@RequestBody Aluno aluno) {
        if (aluno.getId() == null)
            return ResponseEntity.badRequest().build();
        service.salvar(aluno);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        service.deletarPorId(id);
        return ResponseEntity.ok().build();
    }
}
