package Hackaton_5_Semestre.projectGabarito.controller;

import Hackaton_5_Semestre.projectGabarito.model.Prova;
import Hackaton_5_Semestre.projectGabarito.model.Questoes;
import Hackaton_5_Semestre.projectGabarito.model.QuestoesForm;
import Hackaton_5_Semestre.projectGabarito.repository.QuestaoRepository;
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
        Questoes questao = new Questoes();
        Prova prova = provaService.buscarPorId(provaId);
        questao.setProva(prova);
        model.addAttribute("questao", questao);
        model.addAttribute("provaId", provaId);
        return "questao/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Questoes questao) {
        questaoService.salvar(questao);
        return "redirect:/professor/prova/editar/" + questao.getProva().getId();
    }

    @GetMapping("/multiplas/{provaId}")
    public String formAdicionarMultiplas(@PathVariable Long provaId, Model model) {
        Prova prova = provaService.buscarPorId(provaId);
        model.addAttribute("prova", prova);
        return "prova/questao/formulario";
    }

    @PostMapping("/questao/salvarMultiplas")
    public String salvarMultiplasQuestoes(@RequestParam("provaId") Long provaId,
                                          @ModelAttribute QuestoesForm questoesWrapper) {
        Prova prova = provaService.buscarPorId(provaId);
        for (Questoes q : questoesWrapper.getQuestoes()) {
            q.setProva(prova);
        }
        questaoService.salvarTodas(questoesWrapper.getQuestoes());
        return "redirect:/professor/prova/listar";
    }

    @GetMapping("/remover/{id}")
    public String remover(@PathVariable Long id) {
        Questoes questao = questaoService.buscarPorId(id).orElseThrow(() -> new RuntimeException("Questão não encontrada"));
        Long provaId = questao.getProva().getId();
        questaoService.deletar(id);
        return "redirect:/professor/prova/editar/" + provaId;
    }
}
