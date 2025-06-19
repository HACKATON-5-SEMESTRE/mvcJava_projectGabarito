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
        try {
            service.salvar(turma);
            return "redirect:/turma/listar";
        } catch (Exception e){
            model.addAttribute("message","Não consegue");
            return iniciar(turma,model);
        }
    }

    @GetMapping("listar")
    public String listar(Model model) {
        model.addAttribute("turmas", service.listarTodos());
        return "turma/lista";
    }

    @GetMapping("editar/{id}")
    public String alterar(@PathVariable Long id, Model model) {
        model.addAttribute("turma", service.buscarPorId(id));
        return "turma/formulario";
    }

    @GetMapping("remover/{id}")
    public String remover(@PathVariable Long id, Model model) {
        service.deletarPorId(id);
        return "redirect:/turma/listar";
    }
}
