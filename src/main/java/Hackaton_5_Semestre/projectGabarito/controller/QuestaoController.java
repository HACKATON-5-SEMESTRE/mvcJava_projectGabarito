package Hackaton_5_Semestre.projectGabarito.controller;

import Hackaton_5_Semestre.projectGabarito.model.Prova;
import Hackaton_5_Semestre.projectGabarito.model.Questoes;
import Hackaton_5_Semestre.projectGabarito.model.QuestoesForm;
import Hackaton_5_Semestre.projectGabarito.model.TipoQuestao;
import Hackaton_5_Semestre.projectGabarito.service.ProvaService;
import Hackaton_5_Semestre.projectGabarito.service.QuestaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/professor/questao")
public class QuestaoController {

    @Autowired
    private QuestaoService questaoService;

    @Autowired
    private ProvaService provaService;

    @GetMapping("/multiplas/{provaId}")
    public String formAdicionarMultiplas(@PathVariable Long provaId, Model model) {
        Prova prova = provaService.buscarPorId(provaId);
        model.addAttribute("prova", prova);
        model.addAttribute("questoesForm", new QuestoesForm());
        return "prova/questao/formulario";  // seu template Thymeleaf
    }

    @PostMapping("/salvarMultiplas")
    public String salvarMultiplasQuestoes(@RequestParam("provaId") Long provaId,
                                          @ModelAttribute("questoesForm") QuestoesForm questoesWrapper) {
        Prova prova = provaService.buscarPorId(provaId);

        for (Questoes q : questoesWrapper.getQuestoes()) {
            q.setProva(prova);

            if (q.getTipo() == TipoQuestao.OBJETIVA && q.getAlternativas() != null) {
                List<String> alternativasFiltradas = new ArrayList<>();
                for (String alt : q.getAlternativas()) {
                    if (alt != null && !alt.trim().isEmpty()) {
                        alternativasFiltradas.add(alt.trim());
                    }
                }
                q.setAlternativas(alternativasFiltradas);
            }

            if (q.getTipo() == TipoQuestao.DISSERTATIVA) {
                q.setAlternativas(new ArrayList<>());
                q.setGabarito(null);
            }
        }

        questaoService.salvarTodas(questoesWrapper.getQuestoes());

        return "redirect:/professor/prova/editar/" + provaId;
    }
}
