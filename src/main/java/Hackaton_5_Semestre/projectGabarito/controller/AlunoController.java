package Hackaton_5_Semestre.projectGabarito.controller;

import Hackaton_5_Semestre.projectGabarito.model.Aluno;
import Hackaton_5_Semestre.projectGabarito.model.Turma;
import Hackaton_5_Semestre.projectGabarito.service.AlunoService;
import Hackaton_5_Semestre.projectGabarito.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private TurmaService turmaService;

    @GetMapping()
    public String iniciar(Aluno aluno, Model model) {
        model.addAttribute("aluno", aluno);
        model.addAttribute("turmas", turmaService.listarTodos());
        return "aluno/formulario";
    }

    @PostMapping("salvar")
    public String salvar(@ModelAttribute Aluno aluno, Model model) {
        try {
            if (aluno.getTurma() != null && aluno.getTurma().getId() != null) {
                Optional<Turma> optionalTurma = turmaService.buscarPorId(aluno.getTurma().getId());
                if (optionalTurma.isPresent()) {
                    aluno.setTurma(optionalTurma.get());
                } else {
                    model.addAttribute("message", "Turma não encontrada");
                    model.addAttribute("turmas", turmaService.listarTodos());
                    return "aluno/formulario";
                }
            }
            alunoService.salvar(aluno);
            return "redirect:/aluno/listar";
        } catch (Exception e) {
            model.addAttribute("message",
                    "Erro ao salvar aluno: " + e.getMessage());

            model.addAttribute("turmas", turmaService.listarTodos());
            return "aluno/formulario";
        }
    }



    @GetMapping("listar")
    public String listar(Model model) {
        model.addAttribute("alunos", alunoService.listarTodos());
        return "aluno/lista";
    }

    @GetMapping("editar/{id}")
    public String alterar(@PathVariable Long id, Model model,
                          RedirectAttributes redirectAttributes) {
        var optionalAluno = alunoService.buscarPorId(id);

        if (optionalAluno.isPresent()) {
            model.addAttribute("aluno", optionalAluno.get());
            model.addAttribute("turmas", turmaService.listarTodos());
            return "aluno/formulario";
        } else {
            redirectAttributes.addFlashAttribute("erro",
                    "Aluno não encontrado");
            return "redirect:/aluno/listar";
        }
    }

    @GetMapping("remover/{id}")
    public String remover(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            alunoService.deletarPorId(id);
            redirectAttributes.addFlashAttribute("sucesso",
                    "Aluno removido com sucesso!");
        } catch (EmptyResultDataAccessException e) {
            redirectAttributes.addFlashAttribute("erro",
                    "Aluno não encontrado: " + id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro",
                    "Erro ao remover aluno: " + e.getMessage());
        }

        return "redirect:/aluno/listar";
    }
}
