package Hackaton_5_Semestre.projectGabarito.controller;

import Hackaton_5_Semestre.projectGabarito.model.Prova;
import Hackaton_5_Semestre.projectGabarito.model.RespostasAluno;
import Hackaton_5_Semestre.projectGabarito.model.Turma;
import Hackaton_5_Semestre.projectGabarito.service.ProvaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("prova")
public class ProvaController {

    @Autowired
    private ProvaService service;

    @GetMapping()
    public String iniciar(Prova prova, Model model) {
        return "prova/formulario";
    }

   @PostMapping()
    public String inserir(Prova prova, Model model, RespostasAluno respostaAluno) {
        if (prova.getRespostas() == null) {
            prova.setRespostas(Arrays.asList(respostaAluno));
        } else {
            prova.getRespostas().add(respostaAluno);
        }
        return iniciar(prova, model);
    }

    @PostMapping("salvar")
    public String salvar(Prova prova, Model model) {

        if (prova.getTitulo() == null || prova.getTitulo().trim().isEmpty()) {
            model.addAttribute("erro", "O campo Título é obrigatório.");
            model.addAttribute("prova", prova);
            return "prova/formulario";
        }

        if (prova.getDataAplicacao() == null) {
            model.addAttribute("erro", "O campo Data de Aplicação da prova é obrigatório.");
            model.addAttribute("prova", prova);
            return "prova/formulario";
        }

        if (prova.getGabaritoOficial() == null || prova.getGabaritoOficial().trim().isEmpty()) {
            model.addAttribute("erro", "O campo Gabarito Oficial é obrigatório.");
            model.addAttribute("prova", prova);
            return "prova/formulario";
        }

        if (prova.getTurma() == null || prova.getTurma().getId() == null) {
            model.addAttribute("erro", "A Turma deve ser selecionada.");
            model.addAttribute("prova", prova);
            return "prova/formulario";
        }

        if (prova.getDisciplina() == null || prova.getDisciplina().getId() == null) {
            model.addAttribute("erro", "A Disciplina deve ser selecionada.");
            model.addAttribute("prova", prova);
            return "prova/formulario";
        }

        try {
            service.salvar(prova);
            return "redirect:/prova/listar";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao salvar prova: " + e.getMessage());
            model.addAttribute("prova", prova);
            return "prova/formulario";
        }
    }


    @GetMapping("listar")
    public String listar(Model model) {
        model.addAttribute("provas", service.listarTodos());
        return "prova/lista";
    }

    @GetMapping("editar/{id}")
    public String alterar(@PathVariable Long id, Model model) {
        try {
            Prova prova = service.buscarPorId(id);
            model.addAttribute("prova", prova);
            return "prova/formulario";
        } catch (RuntimeException e) {
            model.addAttribute("erro", "prova não encontrada: " + e.getMessage());
            return "prova/lista";
        }
    }

    @GetMapping("remover/{id}")
    public String remover(@PathVariable Long id) {
        try {
            service.deletarPorId(id);
        } catch (RuntimeException e) {
            return "redirect:/prova/listar?erro=" + e.getMessage();
        }
        return "redirect:/prova/listar";
    }
}
