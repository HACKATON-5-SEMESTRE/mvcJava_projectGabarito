// ProvaController.java
package Hackaton_5_Semestre.projectGabarito.controller;

import Hackaton_5_Semestre.projectGabarito.model.Prova;
import Hackaton_5_Semestre.projectGabarito.service.DisciplinaService;
import Hackaton_5_Semestre.projectGabarito.service.ProvaService;
import Hackaton_5_Semestre.projectGabarito.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/professor/prova")
public class ProvaController {

    @Autowired
    private ProvaService provaService;

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private DisciplinaService disciplinaService;

    @GetMapping("/nova")
    public String novaProva(Model model) {
        model.addAttribute("prova", new Prova());
        model.addAttribute("turmas", turmaService.listarTodos());
        model.addAttribute("disciplinas", disciplinaService.listarTodos());
        return "prova/formulario_prova";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Prova prova = provaService.buscarPorId(id);
        model.addAttribute("prova", prova);
        model.addAttribute("turmas", turmaService.listarTodos());
        model.addAttribute("disciplinas", disciplinaService.listarTodos());
        return "prova/formulario_prova";
    }


    @PostMapping("/salvar")
    public String salvarProva(@ModelAttribute Prova prova, Model model) {
        try {
            if (prova.getTurma() == null || prova.getDisciplina() == null) {
                model.addAttribute("erro", "Turma e Disciplina são obrigatórias.");
                model.addAttribute("turmas", turmaService.listarTodos());
                model.addAttribute("disciplinas", disciplinaService.listarTodos());
                return "prova/formulario_prova";
            }
            provaService.salvar(prova);
            return "redirect:/professor/prova/listar";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao salvar: " + e.getMessage());
            model.addAttribute("turmas", turmaService.listarTodos());
            model.addAttribute("disciplinas", disciplinaService.listarTodos());
            return "prova/formulario_prova";
        }
    }

    @GetMapping("/listar")
    public String listarProvas(Model model) {
        model.addAttribute("provas", provaService.listarTodos());
        return "prova/lista";
    }

    @GetMapping("/remover/{id}")
    public String removerProva(@PathVariable Long id) {
        provaService.deletarPorId(id);
        return "redirect:/professor/prova/listar";
    }
}
