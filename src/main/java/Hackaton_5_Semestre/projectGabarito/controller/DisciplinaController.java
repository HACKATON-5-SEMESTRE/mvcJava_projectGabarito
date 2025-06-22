package Hackaton_5_Semestre.projectGabarito.controller;

import Hackaton_5_Semestre.projectGabarito.model.Disciplina;
import Hackaton_5_Semestre.projectGabarito.service.DisciplinaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService service;

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("disciplina", new Disciplina());
        return "disciplina/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Disciplina disciplina, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("disciplina", disciplina);
            return "disciplina/formulario";
        }

        // Validação de duplicidade por nome
        Optional<Disciplina> existente = service.buscarPorNome(disciplina.getNome());
        if (existente.isPresent() && !existente.get().getId().equals(disciplina.getId())) {
            model.addAttribute("erro", "Já existe uma disciplina com esse nome.");
            model.addAttribute("disciplina", disciplina);
            return "disciplina/formulario";
        }

        try {
            service.salvar(disciplina);
            return "redirect:/admin/disciplinas/listar";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao salvar disciplina: " + e.getMessage());
            model.addAttribute("disciplina", disciplina);
            return "disciplina/formulario";
        }
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("disciplinas", service.listarTodos());
        return "disciplina/lista";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Optional<Disciplina> disciplinaOpt = service.buscarPorId(id);
        if (disciplinaOpt.isPresent()) {
            model.addAttribute("disciplina", disciplinaOpt.get());
            return "disciplina/formulario";
        } else {
            model.addAttribute("erro", "Disciplina não encontrada.");
            return "redirect:/admin/disciplinas/listar";
        }
    }

    @GetMapping("/remover/{id}")
    public String remover(@PathVariable Long id, Model model) {
        try {
            service.deletarPorId(id);
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao remover: " + e.getMessage());
        }
        return "redirect:/admin/disciplinas/listar";
    }
}
