package Hackaton_5_Semestre.projectGabarito.controller;

import Hackaton_5_Semestre.projectGabarito.model.Turma;
import Hackaton_5_Semestre.projectGabarito.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("turma")
public class TurmaController {

    @Autowired
    private TurmaService service;

    @GetMapping()
    public String iniciar(Turma turma, Model model) {
        return "turma/formulario";
    }

    @PostMapping()
    public String inserir(Turma turma, Model model) {
        return iniciar(turma, model);
    }

    @PostMapping("salvar")
    public String salvar(Turma turma, Model model) {

        if (turma.getNome() == null || turma.getNome().trim().isEmpty()) {
            model.addAttribute("erro", "O campo Nome é obrigatório.");
            model.addAttribute("turma", turma);
            return iniciar(turma, model);
        }

        if (!turma.getNome().matches("[a-zA-ZÀ-ÿ\\s]{1,100}")) {
            model.addAttribute("erro", "O campo Nome deve conter apenas letras e no máximo 100 caracteres.");
            model.addAttribute("turma", turma);
            return iniciar(turma, model);
        }

        if (turma.getAno() == null) {
            model.addAttribute("erro", "O campo Ano é obrigatório.");
            model.addAttribute("turma", turma);
            return iniciar(turma, model);
        }

        if (turma.getSemestre() == null) {
            model.addAttribute("erro", "O campo Semestre é obrigatório.");
            model.addAttribute("turma", turma);
            return iniciar(turma, model);
        }

        try {
            service.salvar(turma);
            return "redirect:/turma/listar";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao salvar a turma: " + e.getMessage());
            model.addAttribute("turma", turma);
            return iniciar(turma, model);
        }
    }

    @GetMapping("listar")
    public String listar(Model model) {
        model.addAttribute("turmas", service.listarTodos());
        return "turma/lista";
    }

    @GetMapping("editar/{id}")
    public String alterar(@PathVariable Long id, Model model) {
        try {
            Turma turma = service.buscarPorId(id);
            model.addAttribute("turma", turma);
            return "turma/formulario";
        } catch (RuntimeException e) {
            model.addAttribute("erro", "Turma não encontrada: " + e.getMessage());
            return "turma/lista";
        }
    }

    @GetMapping("remover/{id}")
    public String remover(@PathVariable Long id) {
        try {
            service.deletarPorId(id);
        } catch (RuntimeException e) {
            return "redirect:/turma/listar?erro=" + e.getMessage();
        }
        return "redirect:/turma/listar";
    }
}
