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
        try {
            service.salvar(turma);
            return "redirect:/admin/turmas/listar";
        } catch (Exception e) {
            model.addAttribute("message", "Erro ao salvar turma: " + e.getMessage());
            return "turma/formulario";
        }
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("turmas", service.listarTodos());
        return "turma/lista";
    }

    @GetMapping("/editar/{id}")
    public String alterar(@PathVariable Long id, Model model) {
        model.addAttribute("turma", service.buscarPorId(id));
        return "turma/formulario";
    }

    @GetMapping("/remover/{id}")
    public String remover(@PathVariable Long id) {
        service.deletarPorId(id);
        return "redirect:/admin/turmas/listar";
    }
}
