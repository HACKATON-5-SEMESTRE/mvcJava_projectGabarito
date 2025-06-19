package Hackaton_5_Semestre.projectGabarito.controller;

import Hackaton_5_Semestre.projectGabarito.model.Aluno;
import Hackaton_5_Semestre.projectGabarito.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping()
    public String iniciar(Aluno aluno, Model model) {
        return "usuario/formulario";
    }

    @PostMapping()
    public String inserir(Aluno aluno, Model model) {
        try {
            alunoService.salvar(aluno);
            return "redirect:/aluno/listar";
        } catch (Exception e) {
            model.addAttribute("message", "Erro ao salvar aluno: " + e.getMessage());
            return "aluno/formulario";
        }
    }

    @PostMapping("salvar")
    public String salvar(Aluno aluno, Model model) {
        try {
            alunoService.salvar(aluno);
            return "redirect:/aluno/listar";
        } catch (Exception e){
            model.addAttribute("message","Não consegue Moisés");
            return iniciar(aluno, model);
        }
    }

    @GetMapping("listar")
    public String listar(Model model) {
        model.addAttribute("alunos", alunoService.listarTodos());
        return "aluno/lista";
    }

    @GetMapping("editar/{id}")
    public String alterar(@PathVariable Long id, Model model) {
        model.addAttribute("aluno", alunoService.buscarPorId(id));
        return "aluno/formulario";
    }

    @GetMapping("remover/{id}")
    public String remover(@PathVariable Long id, Model model) {
        alunoService.deletarPorId(id);
        return "redirect:/aluno/listar";
    }
}
