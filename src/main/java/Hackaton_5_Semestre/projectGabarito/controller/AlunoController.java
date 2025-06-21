package Hackaton_5_Semestre.projectGabarito.controller.admin;

import Hackaton_5_Semestre.projectGabarito.model.Aluno;
import Hackaton_5_Semestre.projectGabarito.model.Turma;
import Hackaton_5_Semestre.projectGabarito.service.AlunoService;
import Hackaton_5_Semestre.projectGabarito.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private TurmaService turmaService;

    @GetMapping("/novo")
    public String novoAluno(Model model) {
        model.addAttribute("aluno", new Aluno());
        model.addAttribute("turmas", turmaService.listarTodos());
        return "aluno/formulario";
    }


    // Formulário de edição
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Optional<Aluno> aluno = alunoService.buscarPorId(id);
        model.addAttribute("aluno", aluno);
        model.addAttribute("turmas", turmaService.listarTodos());
        return "aluno/formulario";
    }

    // Salvar novo ou editar existente
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Aluno aluno, Model model) {
        try {
            if (aluno.getTurma() != null && aluno.getTurma().getId() != null) {
                Turma turma = turmaService.buscarPorId(aluno.getTurma().getId());
                aluno.setTurma(turma);
            }

            alunoService.salvar(aluno);
            return "redirect:/admin/aluno/listar";

        } catch (Exception e) {
            model.addAttribute("message", "Erro ao salvar aluno: " + e.getMessage());
            model.addAttribute("turmas", turmaService.listarTodos());
            return "aluno/formulario";
        }
    }

    // Listagem de alunos
    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("alunos", alunoService.listarTodos());
        return "aluno/listar";
    }

    // Remoção de aluno
    @GetMapping("/remover/{id}")
    public String remover(@PathVariable Long id) {
        alunoService.deletarPorId(id);
        return "redirect:/admin/aluno/listar";
    }
}
