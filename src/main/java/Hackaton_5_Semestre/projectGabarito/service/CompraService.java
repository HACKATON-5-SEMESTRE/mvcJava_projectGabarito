package Hackaton_5_Semestre.projectGabarito.service;

import br.unialfa.compra_venda.model.Compra;
import br.unialfa.compra_venda.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompraService {

    @Autowired
    private CompraRepository repository;

    @Transactional
    public void salvar(Compra compra) {
        compra.getItens().forEach(itemCompra -> itemCompra.setCompra(compra));
        repository.save(compra);
    }

    public List<Compra> listarTodos() {
        var result = repository.findAll();
        return result;
    }

    public Compra buscarPorId(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public void deletarPorId(Long id) {
        repository.deleteById(id);
    }

}
