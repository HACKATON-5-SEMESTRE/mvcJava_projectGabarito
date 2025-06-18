package Hackaton_5_Semestre.projectGabarito.controller;

import br.unialfa.compra_venda.model.Compra;
import br.unialfa.compra_venda.model.ItemCompra;
import br.unialfa.compra_venda.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("compra")
public class CompraController {

    @Autowired
    private CompraService service;

    @GetMapping()
    public String iniciar(Compra compra, Model model) {
        return "compra/formulario";
    }

    @PostMapping()
    public String inserir(Compra compra, ItemCompra itemCompra, Model model) {
        itemCompra.setId(null);

        if (compra.getItens() == null) compra.setItens(Arrays.asList(itemCompra));
        else compra.getItens().add(itemCompra);

        return iniciar(compra, model);
    }

    @PostMapping("{index}")
    public String removerItem(@PathVariable int index, Compra compra, Model model) {
        compra.getItens().remove(index);
        return iniciar(compra, model);
    }

    @PostMapping("salvar")
    public String salvar(Compra compra, Model model) {
        try {
            service.salvar(compra);
            return "redirect:/compra/listar";
        } catch (Exception e) {
            model.addAttribute("errotitulo", "Algo de errado não deu certo: ");
            model.addAttribute("erro", "Entre em contato!");
            return iniciar(compra,model);
        }
    }

    @GetMapping("listar")
    public String listar(Model model) {
        model.addAttribute("compras", service.listarTodos());
        return "compra/lista";
    }

    @GetMapping("editar/{id}")
    public String alterar(@PathVariable Long id, Model model) {
        model.addAttribute("compra", service.buscarPorId(id));
        return "compra/formulario";
    }

    @GetMapping("remover/{id}")
    public String remover(@PathVariable Long id, Model model) {
        service.deletarPorId(id);
        return "redirect:/compra/listar";
    }
}
