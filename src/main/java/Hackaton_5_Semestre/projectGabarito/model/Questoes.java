package Hackaton_5_Semestre.projectGabarito.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Questoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String enunciado;

    private Double peso;

    @Enumerated(EnumType.STRING)
    private TipoQuestao tipo;

    @ElementCollection
    @CollectionTable(name = "alternativas", joinColumns = @JoinColumn(name = "questao_id"))
    @Column(name = "alternativa")
    private List<String> alternativas = new ArrayList<>();

    @Column(name = "gabarito")
    private String gabarito;

    @ManyToOne
    @JoinColumn(name = "id_prova")
    private Prova prova;
}
