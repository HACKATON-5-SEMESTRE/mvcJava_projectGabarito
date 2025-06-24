// Questoes.java
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

    @ElementCollection
    @CollectionTable(name = "alternativas", joinColumns = @JoinColumn(name = "questao_id"))
    @Column(name = "alternativa")
    private List<String> alternativas = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prova")
    private Prova prova;

    @Column(length = 1)
    private String gabarito; // A, B, C, D ou E
}
