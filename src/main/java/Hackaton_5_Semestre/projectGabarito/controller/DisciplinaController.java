package Hackaton_5_Semestre.projectGabarito.controller;

import Hackaton_5_Semestre.projectGabarito.model.Disciplina;
import Hackaton_5_Semestre.projectGabarito.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/admin/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService service;

    @GetMapping()
    public String iniciar(Disciplina disciplina, Model model) {
        return "disciplina/formulario";
    }

    @PostMapping()
    public String inserir(Disciplina disciplina, Model model) {
        return iniciar(disciplina, model);
    }

    @PostMapping("salvar")
    public String salvar(Disciplina disciplina, Model model) {
        try {
            service.salvar(disciplina);
            return "redirect:/disciplina/listar";
        } catch (Exception e){
            model.addAttribute("message","Não consegue");
            return iniciar(disciplina,model);
        }
    }

    @GetMapping("listar")
    public String listar(Model model) {
        model.addAttribute("disciplinas", service.listarTodos());
        return "disciplina/lista";
    }

    @GetMapping("editar/{id}")
    public String alterar(@PathVariable Long id, Model model) {
        model.addAttribute("disciplina", service.buscarPorId(id));
        return "disciplina/formulario";
    }

    @GetMapping("remover/{id}")
    public String remover(@PathVariable Long id, Model model) {
        service.deletarPorId(id);
        return "redirect:/disciplina/listar";
    }
}
