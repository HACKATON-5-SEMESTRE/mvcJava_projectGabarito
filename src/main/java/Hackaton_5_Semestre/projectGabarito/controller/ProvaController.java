package Hackaton_5_Semestre.projectGabarito.controller;

import Hackaton_5_Semestre.projectGabarito.model.Prova;
import Hackaton_5_Semestre.projectGabarito.service.ProvaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("prova")
public class ProvaController {

    @Autowired
    private ProvaService service;

    @GetMapping()
    public String iniciar(Prova prova, Model model) {
        return "prova/formulario";
    }

   /* @PostMapping()
    public String inserir(Prova prova, Model model, RespostaAluno respostaAluno) {
        if (prova.getRespostas() == null) {
            prova.setRespostas(Arrays.asList(respostaAluno));
        } else {
            prova.getRespostas().add(respostaAluno);
        }
        return iniciar(prova, model);
    }*/

    @PostMapping("salvar")
    public String salvar(Prova prova, Model model) {
        try {
            service.salvar(prova);
            return "redirect:/prova/listar";
        } catch (Exception e) {
            model.addAttribute("message", "Erro ao salvar prova.");
            return iniciar(prova, model);
        }
    }

    @GetMapping("listar")
    public String listar(Model model) {
        model.addAttribute("provas", service.listarTodos());
        return "prova/lista";
    }

    @GetMapping("editar/{id}")
    public String alterar(@PathVariable Long id, Model model) {
        model.addAttribute("prova", service.buscarPorId(id));
        return "prova/formulario";
    }

    @GetMapping("remover/{id}")
    public String remover(@PathVariable Long id, Model model) {
        service.deletarPorId(id);
        return "redirect:/prova/listar";
    }
}
