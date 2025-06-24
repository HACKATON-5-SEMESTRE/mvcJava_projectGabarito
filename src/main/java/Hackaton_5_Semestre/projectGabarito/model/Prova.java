// Prova.java
package Hackaton_5_Semestre.projectGabarito.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prova {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private LocalDate dataAplicacao;

    private Double valorTotal;

    @Lob
    private String gabaritoOficial;

    @ManyToOne
    @JoinColumn(name = "id_turma")
    private Turma turma;

    @ManyToOne
    @JoinColumn(name = "id_disciplina")
    private Disciplina disciplina;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "prova", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Questoes> questoes = new ArrayList<>();

    // Helper para adicionar questão
    public void addQuestao(Questoes questao) {
        questoes.add(questao);
        questao.setProva(this);
    }

    // Helper para remover questão
    public void removeQuestao(Questoes questao) {
        questoes.remove(questao);
        questao.setProva(null);
    }
}
