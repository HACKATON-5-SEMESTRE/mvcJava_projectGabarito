package Hackaton_5_Semestre.projectGabarito.controller;

import Hackaton_5_Semestre.projectGabarito.model.Prova;
import Hackaton_5_Semestre.projectGabarito.model.Questoes;
import Hackaton_5_Semestre.projectGabarito.service.DisciplinaService;
import Hackaton_5_Semestre.projectGabarito.service.ProvaService;
import Hackaton_5_Semestre.projectGabarito.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/professor/prova")
public class ProvaController {

    @Autowired
    private ProvaService service;

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private DisciplinaService disciplinaService;

    @GetMapping("/formulario")
    public String iniciarFormulario(Prova prova, Model model) {
        model.addAttribute("prova", prova);
        model.addAttribute("turmas", turmaService.listarTodos());
        model.addAttribute("disciplinas", disciplinaService.listarTodos());
        model.addAttribute("questao", new Questoes()); // Adiciona aqui
        return "prova/questao/formulario";
    }

    @GetMapping("/nova")
    public String novaProva(Model model) {
        model.addAttribute("prova", new Prova());
        model.addAttribute("turmas", turmaService.listarTodos());
        model.addAttribute("disciplinas", disciplinaService.listarTodos());
        return "prova/formulario_prova";
    }

    @PostMapping("/salvar")
    public String salvarProva(@ModelAttribute Prova prova, Model model) {
        if (prova.getTurma() == null ||
                prova.getTurma().getId() == null ||
                prova.getDisciplina() == null ||
                prova.getDisciplina().getId() == null)
        {
            model.addAttribute("erro", "Turma e Disciplina são obrigatórias.");
            model.addAttribute("turmas", turmaService.listarTodos());
            model.addAttribute("disciplinas", disciplinaService.listarTodos());
            return "prova/formulario_prova";
        }
        prova.setQuestoes(new ArrayList<>());
        service.salvar(prova);
        return "redirect:/professor/questao/multiplas/" + prova.getId();
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("provas", service.listarTodos());
        return "prova/lista";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        try {
            Prova prova = service.buscarPorId(id);
            model.addAttribute("prova", prova);
            model.addAttribute("turmas", turmaService.listarTodos());
            model.addAttribute("disciplinas", disciplinaService.listarTodos());
            model.addAttribute("questao", new Questoes()); // Garante que o objeto esteja no model
            return "prova/questao/formulario";
        } catch (RuntimeException e) {
            model.addAttribute("erro", "Prova não encontrada: " + e.getMessage());
            return "redirect:/professor/prova/listar";
        }
    }


    @GetMapping("/remover/{id}")
    public String remover(@PathVariable Long id) {
        try {
            service.deletarPorId(id);
        } catch (RuntimeException e) {
            return "redirect:/professor/prova/listar?erro=" + e.getMessage();
        }
        return "redirect:/professor/prova/listar";
    }
}
