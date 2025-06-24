package Hackaton_5_Semestre.projectGabarito.controller;

import Hackaton_5_Semestre.projectGabarito.model.RespostasAluno;
import Hackaton_5_Semestre.projectGabarito.service.AlunoService;
import Hackaton_5_Semestre.projectGabarito.service.ProvaService;
import Hackaton_5_Semestre.projectGabarito.service.RespostasAlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("aluno/notas")
public class RespostasAlunoController {

    @Autowired
    private RespostasAlunoService respostasAlunoService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ProvaService provaService;

    @GetMapping()
    public String iniciar(RespostasAluno respostasAluno, Model model) {
        model.addAttribute("respostasAluno", respostasAluno);
        model.addAttribute("alunos", alunoService.listarTodos());
        model.addAttribute("provas", provaService.listarTodos());

        return "notas/lista_notas";
    }

    @PostMapping("salvar")
    public String salvar(@ModelAttribute RespostasAluno respostasAluno, Model model) {
        try {
            if (respostasAluno.getDataEnvio() == null) {
                respostasAluno.setDataEnvio(LocalDateTime.now());
            }

            respostasAlunoService.salvar(respostasAluno);
            return "redirect:/aluno/notas/lista_notas";
        } catch (Exception e) {
            model.addAttribute("message", "Erro ao salvar resposta: " + e.getMessage());
            model.addAttribute("alunos", alunoService.listarTodos());
            model.addAttribute("provas", provaService.listarTodos());

            return "notas/lista_notas";
        }
    }

    @GetMapping("lista_notas")
    public String listar(Model model) {
        model.addAttribute("respostas", respostasAlunoService.listarTodos());
        return "notas/lista_notas";
    }

    @GetMapping("editar/{id}")
    public String alterar(@PathVariable Long id, Model model) {
        RespostasAluno respostasAluno = respostasAlunoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Resposta inválida: " + id));

        model.addAttribute("respostasAluno", respostasAluno);
        model.addAttribute("alunos", alunoService.listarTodos());
        model.addAttribute("provas", provaService.listarTodos());

        return "notas/lista_notas";
    }

    @GetMapping("remover/{id}")
    public String remover(@PathVariable Long id) {
        respostasAlunoService.deletarPorId(id);
        return "redirect:/aluno/notas/lista_notas";
    }

    @GetMapping("/aluno/{alunoId}/notas")
    public String listarNotasAluno(@PathVariable Long alunoId, Model model) {
        model.addAttribute("respostas", respostasAlunoService.buscarPorAluno(alunoId));
        return "respostas/notasAluno";
    }
}
