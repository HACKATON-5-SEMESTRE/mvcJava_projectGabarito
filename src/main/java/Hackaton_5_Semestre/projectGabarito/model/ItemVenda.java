package Hackaton_5_Semestre.projectGabarito.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Venda venda;

    private Integer quantidade;
    private Float valor;
    private String descricao;
}
