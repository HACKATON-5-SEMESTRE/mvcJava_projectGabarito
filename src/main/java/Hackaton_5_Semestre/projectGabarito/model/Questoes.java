package Hackaton_5_Semestre.projectGabarito.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String alternativas;

    private String gabarito;

    @ManyToOne
    @JoinColumn(name = "id_prova")
    private Prova prova;
}