package Hackaton_5_Semestre.projectGabarito.controller;

import Hackaton_5_Semestre.projectGabarito.model.Turma;
import Hackaton_5_Semestre.projectGabarito.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/turmas")
public class TurmaController {

    @Autowired
    private TurmaService service;

    @GetMapping("/novo")
    public String iniciar(Model model) {
        model.addAttribute("turma", new Turma());
        return "turma/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Turma turma, Model model) {

        // Validações
        if (turma.getCurso() == null || turma.getCurso().trim().isEmpty()) {
            model.addAttribute("erro", "O campo Curso é obrigatório.");
            model.addAttribute("turma", turma);
            return "turma/formulario";
        }

        if (turma.getSala() == null || turma.getSala().trim().isEmpty()) {
            model.addAttribute("erro", "O campo Sala é obrigatório.");
            model.addAttribute("turma", turma);
            return "turma/formulario";
        }

        if (turma.getSemestre() == null) {
            model.addAttribute("erro", "O campo Semestre é obrigatório.");
            model.addAttribute("turma", turma);
            return "turma/formulario";
        }

        try {
            service.salvar(turma);
            return "redirect:/admin/turmas/listar";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao salvar a turma: " + e.getMessage());
            model.addAttribute("turma", turma);
            return "turma/formulario";
        }
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("turmas", service.listarTodos());
        return "turma/lista";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        try {
            Turma turma = service.buscarPorId(id);
            model.addAttribute("turma", turma);
            return "turma/formulario";
        } catch (RuntimeException e) {
            model.addAttribute("erro", "Turma não encontrada: " + e.getMessage());
            return "redirect:/admin/turmas/listar";
        }
    }

    @GetMapping("/remover/{id}")
    public String remover(@PathVariable Long id, Model model) {
        try {
            service.deletarPorId(id);
        } catch (RuntimeException e) {
            model.addAttribute("erro", "Erro ao remover: " + e.getMessage());
        }
        return "redirect:/admin/turmas/listar";
    }
}
