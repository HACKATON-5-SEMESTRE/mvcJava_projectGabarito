package Hackaton_5_Semestre.projectGabarito.api;

import Hackaton_5_Semestre.projectGabarito.model.RespostasAluno;
import Hackaton_5_Semestre.projectGabarito.service.RespostasAlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/respostas-alunos")
public class RespostasAlunoApi {

    @Autowired
    private RespostasAlunoService respostasAlunoService;

    @GetMapping
    public List<RespostasAluno> listarTodos() {
        return respostasAlunoService.listarTodos();
    }

    @GetMapping("/{id}")
    public RespostasAluno buscarPorId(@PathVariable Long id) {
        return respostasAlunoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Resposta não encontrada: " + id));
    }

    @GetMapping("/aluno/{alunoId}")
    public List<RespostasAluno> buscarPorAluno(@PathVariable Long alunoId) {
        return respostasAlunoService.buscarPorAluno(alunoId);
    }

    @GetMapping("/prova/{provaId}")
    public List<RespostasAluno> buscarPorProva(@PathVariable Long provaId) {
        return respostasAlunoService.buscarPorProva(provaId);
    }

    @PostMapping
    public RespostasAluno criar(@RequestBody RespostasAluno respostasAluno) {
        return respostasAlunoService.salvar(respostasAluno);
    }

    @PutMapping("/{id}")
    public RespostasAluno atualizar(@PathVariable Long id, @RequestBody RespostasAluno respostasAluno) {
        respostasAluno.setId(id);
        return respostasAlunoService.salvar(respostasAluno);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        respostasAlunoService.deletarPorId(id);
    }
}
