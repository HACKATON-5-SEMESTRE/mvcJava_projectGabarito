package Hackaton_5_Semestre.projectGabarito.controller;

import Hackaton_5_Semestre.projectGabarito.model.Prova;
import Hackaton_5_Semestre.projectGabarito.model.Questoes;
import Hackaton_5_Semestre.projectGabarito.service.ProvaService;
import Hackaton_5_Semestre.projectGabarito.service.QuestaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/professor/questao")
public class QuestaoController {

    @Autowired
    private QuestaoService questaoService;

    @Autowired
    private ProvaService provaService;

    @GetMapping("/nova/{provaId}")
    public String novaQuestao(@PathVariable Long provaId, Model model) {

        var prova = provaService.buscarPorId(provaId);

        Questoes questao = new Questoes();
        questao.setProva(prova);

        model.addAttribute("questao", questao);
        model.addAttribute("prova", prova);

        double valorTotal = prova.getValorTotal() != null ? prova.getValorTotal() : 0;
        double pesoSomado = prova.getQuestoes().stream().mapToDouble(Questoes::getPeso).sum();
        double valorRestante = valorTotal - pesoSomado;

        model.addAttribute("valorRestante", valorRestante);

        return "prova/questao/formulario";
    }

    @PostMapping("/salvar")
    public String salvarQuestao(@ModelAttribute Questoes questao, Model model) {
        if (questao.getProva() == null || questao.getProva().getId() == null) {
            model.addAttribute("erro", "Prova não informada");
            return "prova/questao/formulario";
        }

        var prova = provaService.buscarPorId(questao.getProva().getId());

        double valorTotal = prova.getValorTotal() != null ? prova.getValorTotal() : 0;
        double pesoSomado = prova.getQuestoes().stream().mapToDouble(Questoes::getPeso).sum();

        double valorRestante = valorTotal - pesoSomado;

        if (questao.getPeso() > valorRestante) {
            model.addAttribute("erro", "Peso da questão excede o valor restante da prova: " + valorRestante);
            model.addAttribute("questao", questao);
            model.addAttribute("prova", prova);
            model.addAttribute("valorRestante", valorRestante);
            return "prova/questao/formulario";
        }

        questao.setProva(prova);
        questaoService.salvar(questao);

        prova.setValorTotal(valorRestante - questao.getPeso());
        provaService.salvar(prova);

        return "redirect:/professor/prova/editar/" + prova.getId();
    }

    @PostMapping("/professor/questao/salvar-varias")
    public String salvarProvaComQuestoes(@ModelAttribute Prova prova, Model model) {
        try {
            // Verifica se prova existe (se for editar) ou cria nova
            if (prova.getId() != null) {
                // Se quiser, busca a prova do banco para mesclar dados
                Prova provaExistente = provaService.buscarPorId(prova.getId());
                provaExistente.setTitulo(prova.getTitulo());
                provaExistente.setDataAplicacao(prova.getDataAplicacao());
                provaExistente.setValorTotal(prova.getValorTotal());
                provaExistente.setTurma(prova.getTurma());
                provaExistente.setDisciplina(prova.getDisciplina());
                provaExistente.setGabaritoOficial(prova.getGabaritoOficial());
                // Remove questões antigas
                provaExistente.getQuestoes().clear();
                // Adiciona as questões novas da submissão
                if (prova.getQuestoes() != null) {
                    for (Questoes q : prova.getQuestoes()) {
                        q.setProva(provaExistente);
                        provaExistente.getQuestoes().add(q);
                    }
                }
                provaService.salvar(provaExistente);
            } else {
                // Prova nova
                if (prova.getQuestoes() != null) {
                    for (Questoes q : prova.getQuestoes()) {
                        q.setProva(prova);
                    }
                }
                provaService.salvar(prova);
            }
            return "redirect:/professor/prova/listar";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao salvar a prova: " + e.getMessage());
            model.addAttribute("prova", prova);
            // Você pode recarregar turmas, disciplinas, etc
            return "prova/questao/formulario";
        }
    }
}
