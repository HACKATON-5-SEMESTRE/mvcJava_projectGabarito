package Hackaton_5_Semestre.projectGabarito.controller.admin;

import Hackaton_5_Semestre.projectGabarito.model.Aluno;
import Hackaton_5_Semestre.projectGabarito.model.Turma;
import Hackaton_5_Semestre.projectGabarito.model.Usuario;
import Hackaton_5_Semestre.projectGabarito.service.AlunoService;
import Hackaton_5_Semestre.projectGabarito.service.TurmaService;
import Hackaton_5_Semestre.projectGabarito.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/aluno")
public class AlunoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private TurmaService turmaService;

    @GetMapping("/novo")
    public String novoAluno(Model model) {
        model.addAttribute("aluno", new Aluno());
        model.addAttribute("turmas", turmaService.listarTodos());
        return "aluno/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Aluno> alunoOpt = alunoService.buscarPorId(id);
        if (alunoOpt.isPresent()) {
            model.addAttribute("aluno", alunoOpt.get());
            model.addAttribute("turmas", turmaService.listarTodos());
            return "aluno/formulario";
        } else {
            redirectAttributes.addFlashAttribute("erro", "Aluno não encontrado.");
            return "redirect:/admin/aluno/listar";
        }
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Aluno aluno, Model model, RedirectAttributes redirectAttributes) {
        try {
            // Salvar o usuário associado
            Usuario usuario = aluno.getUsuario();
            if (usuario != null) {
                usuarioService.salvar(usuario);
            }

            // Validar e associar turma
            if (aluno.getTurma() != null && aluno.getTurma().getId() != null) {
                Optional<Turma> optionalTurma = turmaService.buscarPorId(aluno.getTurma().getId());
                if (optionalTurma.isPresent()) {
                    aluno.setTurma(optionalTurma.get());
                } else {
                    model.addAttribute("erro", "Turma não encontrada.");
                    model.addAttribute("turmas", turmaService.listarTodos());
                    return "aluno/formulario";
                }
            }

            alunoService.salvar(aluno);
            redirectAttributes.addFlashAttribute("sucesso", "Aluno salvo com sucesso.");
            return "redirect:/admin/aluno/listar";

        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao salvar aluno: " + e.getMessage());
            model.addAttribute("turmas", turmaService.listarTodos());
            return "aluno/formulario";
        }
    }

    @GetMapping("/listar")
    public String listar(Model model, @ModelAttribute("sucesso") String sucesso,
                         @ModelAttribute("erro") String erro) {
        model.addAttribute("alunos", alunoService.listarTodos());
        model.addAttribute("sucesso", sucesso);
        model.addAttribute("erro", erro);
        return "aluno/lista";
    }

    @GetMapping("/remover/{id}")
    public String remover(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            alunoService.deletarPorId(id);
            redirectAttributes.addFlashAttribute("sucesso", "Aluno removido com sucesso!");
        } catch (EmptyResultDataAccessException e) {
            redirectAttributes.addFlashAttribute("erro", "Aluno não encontrado: " + id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao remover aluno: " + e.getMessage());
        }
        return "redirect:/admin/aluno/listar";
    }
}
